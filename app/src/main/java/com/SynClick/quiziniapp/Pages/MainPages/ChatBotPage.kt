package com.SynClick.quiziniapp.Pages.MainPages

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.Services
import com.SynClick.quiziniapp.Data.Data
import com.SynClick.quiziniapp.Data.Models.Conversation
import com.SynClick.quiziniapp.Data.Models.Message
import com.SynClick.quiziniapp.Data.Models.Post
import com.SynClick.quiziniapp.Data.Models.Questionnaire
import com.SynClick.quiziniapp.Data.Models.RequestsModel.AddQuestionRequest
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetConversationRequest
import com.SynClick.quiziniapp.Data.Models.RequestsModel.getNewPostsRequest
import com.SynClick.quiziniapp.Pages.Authentification.checkEmail
import com.SynClick.quiziniapp.R
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.SynClick.quiziniapp.Data.Models.RequestsModel.CreateQuestionRequest
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetConversationByIdRequest
import com.SynClick.quiziniapp.Data.Models.RequestsModel.ReactionMessageRequest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@SuppressLint("SuspiciousIndentation")
@Composable fun ChatBotPage(FragmentWidth:Float, setTopMenuVisibility: (Boolean) -> Unit,getMenu:(Boolean)->Unit)
{
    val (menuState,setMenuState)=remember{mutableStateOf(false)}
    val (newState,setNewState)=remember{mutableStateOf(false)}
    val (loadMoreConversation,setLoadMoreConversation)=remember{mutableStateOf(true)}
    val (ConversationList,setConversationList)=remember{mutableStateOf(listOf<Conversation>())}
    val (CurrentConversation,setCurrentConversation)=remember{mutableStateOf(Conversation())}
    val (CurrentMessages,setCurrentMessages)=remember{mutableStateOf(listOf<Message>())}
    val (InputText,setInputText)=remember{mutableStateOf("")}
    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()
    val gson = Gson()
    val (loadMoreMessages,setLoadMoreMessages)=remember{mutableStateOf(false)}

    LaunchedEffect(CurrentConversation) {
        if(CurrentConversation.messages!=null) {
            setNewState(false)
            setCurrentMessages(sortMessagesByDate(CurrentConversation.messages))
        }
        else{
            setNewState(false)
            CurrentConversation.messages= listOf()
            setCurrentMessages(sortMessagesByDate(CurrentConversation.messages))
        }

    }
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
            .distinctUntilChanged()
            .collectLatest { isAtBottom ->
                if (isAtBottom) {
                    println("bottom")
                    setLoadMoreMessages(true)
                }
            }
    }

    LaunchedEffect(loadMoreMessages) {
        println("loadMoreMessages")

        if(loadMoreMessages)
        coroutineScope.launch {
            try {
                println(CurrentConversation.messages.map { it.id }.toList())
                val call = Services.getChatBotService()
                    .getConversationById(
                        "Bearer " + Data.token,
                        GetConversationByIdRequest(3,
                            CurrentConversation.messages.map { it.id }.toList(),
                            CurrentConversation.id
                        )
                    )
                val response = withContext(Dispatchers.IO) { call.execute() }
                if (response.isSuccessful) { // 200..300
                    val body = response.body()
                    if (body != null) {
                        println("body: " + body);
                        println("body: " + gson.toJson(body));
                        println("code: " + response.code());
                        if(body.conversation!=null)
                        setCurrentMessages(CurrentMessages+body.conversation.messages)
                        CurrentConversation.messages=CurrentMessages

                        setLoadMoreConversation(false);
                    }
                    else{
                        println("error1: "+response.code())
                    }

                }
                else{
                    println("error: "+response.code())
                }
            } catch (e: Exception) {
                e.printStackTrace()

            }
            finally {

                setLoadMoreMessages(false)
            }
        }



    }

    LaunchedEffect(loadMoreConversation) {
        if (loadMoreConversation)
            coroutineScope.launch {
                try {
                    val call = Services.getChatBotService()
                        .getConversations(
                            "Bearer " + Data.token,
                            GetConversationRequest(10,
                                ConversationList.map { it.id }.toList()
                            )
                        )
                    val response = withContext(Dispatchers.IO) { call.execute() }
                    if (response.isSuccessful) { // 200..300
                        val body = response.body()
                        if (body != null) {
                            println("body: " + body);
                            println("body: " + gson.toJson(body));
                            println("code: " + response.code());
                            setConversationList(ConversationList+body.conversations);
                            setCurrentConversation(body.conversations[0])


                            setLoadMoreConversation(false);
                        }

                    }
                    else{
                        println("error: "+response.code())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
    }


    val (conversationBarState,setConversationBarState)=remember{mutableStateOf(false)}
    setTopMenuVisibility(false)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Spacer(modifier = Modifier.height(60.dp))
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp - 200.dp),
                reverseLayout = true, // Ensures the content starts from the bottom
            ) {
                if(ConversationList.isNotEmpty())
                {

                    CurrentMessages.forEach {
                        item {
                            if(it!=null && CurrentMessages!=null && sortMessagesByDate(CurrentMessages).indexOf(it)==0)
                                MessageView(it,if(CurrentConversation.id==null) Long.MIN_VALUE else CurrentConversation.id,FragmentWidth,new=true,setNewState={setNewState(it)})
                            else
                                MessageView(it,if(CurrentConversation.id==null) Long.MIN_VALUE else CurrentConversation.id,FragmentWidth,new=false,setNewState={setNewState(it)})

                        }
                    }
                }
                else{
                    item {
                        Text(text = "No Conversations")
                    }
                }

            }
            Input(FragmentWidth,InputText,{setInputText(it)},
                {

                    coroutineScope.launch {
                        try {
                            if(CurrentMessages.size!=0){
                                val call = Services.getChatBotService()
                                    .addQuestion(
                                        "Bearer " + Data.token,
                                        AddQuestionRequest(CurrentConversation.id,InputText)
                                    )
                                val response = withContext(Dispatchers.IO) { call.execute() }
                                if (response.isSuccessful) { // 200..300
                                    val body = response.body()
                                    if (body != null) {
                                        println("body: " + body);
                                        println("body: " + gson.toJson(body));
                                        println("code: " + response.code());
                                        setNewState(true)
                                        setCurrentMessages(sortMessagesByDate(CurrentMessages+body.conversation.messages))

                                        setInputText("")
                                    }
                                } else {

                                }
                            }
                            else{
                                val call = Services.getChatBotService()
                                    .createConversation(
                                        "Bearer " + Data.token,
                                        CreateQuestionRequest(InputText)
                                    )
                                val response = withContext(Dispatchers.IO) { call.execute() }
                                if (response.isSuccessful) { // 200..300
                                    val body = response.body()
                                    if (body != null) {
                                        println("body: " + body);
                                        println("body: " + gson.toJson(body));
                                        println("code: " + response.code());
                                        setNewState(true)
                                        setCurrentMessages(CurrentMessages+body.conversation.messages[0])
                                        setConversationList(listOf(body.conversation)+ConversationList)
                                        setInputText("")
                                    }
                                } else {
                                    println("error: "+response.code())

                                }
                            }


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }


                }
                )



        }
        ConversationMenu(
            null,
            FragmentWidth,
            getMenu={setMenuState(!menuState);getMenu(!menuState)},
            changeConversationBarState ={setConversationBarState(!conversationBarState)},
            menuState,
            conversationBarState,
            ConversationList,
            CurrentConversation=CurrentConversation,
            setConversation={setCurrentConversation(it)},
            {setCurrentConversation(Conversation());println("new conversation")}
        )


    }



}
@Composable
fun ConversationMenu(
    conversation: Conversation?,
    FragmentWidth: Float,
    getMenu: () -> Unit,
    changeConversationBarState: () -> Unit,
    menuState: Boolean,
    conversationBarState: Boolean,
    ConversationList: List<Conversation>,
    CurrentConversation:Conversation,
    setConversation:(Conversation)->Unit,
    newConversation:()->Unit
) {
    val tintColor = if (menuState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    val slideOffset by animateFloatAsState(
        targetValue = if (conversationBarState) 0f else -1f, // Slide in when true
        animationSpec = tween(durationMillis = 300) // Animation duration
    )
    val todayConversations = ConversationList.filter {
        isCreatedToday(convertStringToDate(it.createdAt))
    }
    val yesterdayConversations = ConversationList.filter {
        isYesterday(convertStringToDate(it.createdAt))
    }
    val thisWeekConversations = ConversationList.filter {
        isThisWeek(convertStringToDate(it.createdAt))&&(!(isYesterday(convertStringToDate(it.createdAt))|| isCreatedToday(convertStringToDate(it.createdAt))))
    }
    val lastMonthConversations = ConversationList.filter {
        isLastMonth(convertStringToDate(it.createdAt))&&(!isThisWeek(convertStringToDate(it.createdAt)))
    }
    val olderConversations = ConversationList.filter {
        !isCreatedToday(convertStringToDate(it.createdAt)) && !isYesterday(convertStringToDate(it.createdAt)) && !isThisWeek(convertStringToDate(it.createdAt)) && !isLastMonth(convertStringToDate(it.createdAt))
    }
    Column(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .background(if (conversationBarState) MaterialTheme.colorScheme.background else Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                    .background(if (conversationBarState) MaterialTheme.colorScheme.background else Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hamburger),
                        contentDescription = null,
                        modifier = Modifier
                            .size((50 * FragmentWidth).dp)
                            .clickable(onClick = {
                                getMenu()
                            }),
                        alignment = Alignment.CenterStart,
                        colorFilter = ColorFilter.tint(tintColor)
                    )
                    Text(
                        text = "Welcome To QuizBt",
                        fontSize = ((20 * FragmentWidth)).sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.conversation),
                        contentDescription = null,
                        modifier = Modifier
                            .size((30 * FragmentWidth).dp)
                            .clickable(onClick = {
                                changeConversationBarState()
                            }),
                        alignment = Alignment.CenterStart,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
        }

        // Sliding Menu Content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .offset(x = slideOffset.dp * 500) // Slide horizontally based on offset
        ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.background))
                    {
                        Column(
                            modifier = Modifier
                                .padding(top=20.dp, start = 10.dp)
                        ) {
                            Card (
                                modifier = Modifier
                                    .clickable {
                                        newConversation()
                                        changeConversationBarState()
                                    }
                                    .padding(vertical = 20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.onBackground,
                                ),

                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .padding(5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {


                                    Image(
                                        painter = painterResource(id = R.drawable.add),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size((40 * FragmentWidth).dp)
                                            .clickable(onClick = {
                                            }),
                                        alignment = Alignment.CenterStart,
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
                                    )
                                    Text(
                                        text = "New Conversation",
                                        modifier = Modifier
                                            .padding(10.dp),
                                        color = MaterialTheme.colorScheme.background,
                                        fontSize = ((20 * FragmentWidth)).sp,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }


                            if(todayConversations.isNotEmpty()) {
                                Text(
                                    text = "Today",
                                    fontSize = ((20 * FragmentWidth)).sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    textAlign = TextAlign.Center,

                                )

                                Spacer(modifier =Modifier.height(12.dp))
                                todayConversations.forEach {

                                    Card (
                                        modifier = Modifier
                                            .clickable {
                                                setConversation(it)
                                                changeConversationBarState()
                                            },
                                        colors = CardDefaults.cardColors(

                                            containerColor=(if (it == CurrentConversation) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background),
                                        ),

                                        ){
                                        Text(
                                            text = it.title,
                                            modifier = Modifier
                                                .padding(10.dp),
                                            color = if(it==CurrentConversation)MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                                            fontSize = ((17 * FragmentWidth)).sp,
                                            textAlign = TextAlign.Center,
                                        )
                                    }

                                    Spacer(modifier =Modifier.height(12.dp))
                                }
                                Spacer(modifier =Modifier.height(25.dp))
                            }
                            if(yesterdayConversations.isNotEmpty()) {
                                Text(
                                    text = "Yesterday",
                                    fontSize = ((20 * FragmentWidth)).sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier =Modifier.height(12.dp))
                                yesterdayConversations.forEach {
                                    Card (
                                        modifier = Modifier
                                            .clickable {
                                                setConversation(it)
                                                changeConversationBarState()
                                            },
                                        colors = CardDefaults.cardColors(

                                            containerColor=(if (it == CurrentConversation) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background),
                                            ),

                                    ){
                                        Text(
                                            text = it.title,
                                            modifier = Modifier
                                                .padding(10.dp),
                                            color = if(it==CurrentConversation)MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                                            fontSize = ((17 * FragmentWidth)).sp,
                                            textAlign = TextAlign.Center,
                                        )
                                    }


                                    Spacer(modifier =Modifier.height(12.dp))
                                }
                                Spacer(modifier =Modifier.height(25.dp))
                            }
                            if(thisWeekConversations.isNotEmpty()) {
                                Text(
                                    text = "This Week",
                                    fontSize = ((20 * FragmentWidth)).sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier =Modifier.height(12.dp))
                                thisWeekConversations.forEach {

                                    Card (
                                        modifier = Modifier
                                            .clickable {
                                                setConversation(it)
                                                changeConversationBarState()
                                            },
                                        colors = CardDefaults.cardColors(

                                            containerColor=(if (it == CurrentConversation) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background),
                                        ),

                                        ){
                                        Text(
                                            text = it.title,
                                            modifier = Modifier
                                                .padding(10.dp),
                                            color = if(it==CurrentConversation)MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                                            fontSize = ((17 * FragmentWidth)).sp,
                                            textAlign = TextAlign.Center,
                                        )
                                    }

                                    Spacer(modifier =Modifier.height(12.dp))
                                }
                                Spacer(modifier =Modifier.height(25.dp))
                            }
                            if(lastMonthConversations.isNotEmpty()) {

                                Text(
                                    text = "Last Month",
                                    fontSize = ((20 * FragmentWidth)).sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier =Modifier.height(12.dp))
                                lastMonthConversations.forEach {

                                    Card (
                                        modifier = Modifier
                                            .clickable {
                                                setConversation(it)
                                                changeConversationBarState()
                                            },
                                        colors = CardDefaults.cardColors(

                                            containerColor=(if (it == CurrentConversation) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background),
                                        ),

                                        ){
                                        Text(
                                            text = it.title,
                                            modifier = Modifier
                                                .padding(10.dp),
                                            color = if(it==CurrentConversation)MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                                            fontSize = ((17 * FragmentWidth)).sp,
                                            textAlign = TextAlign.Center,
                                        )
                                    }

                                    Spacer(modifier =Modifier.height(12.dp))
                                }
                                Spacer(modifier =Modifier.height(25.dp))
                            }
                            if(olderConversations.isNotEmpty()) {
                                Text(
                                    text = "Older",
                                    fontSize = ((20 * FragmentWidth)).sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier =Modifier.height(12.dp))
                                olderConversations.forEach {

                                    Card (
                                        modifier = Modifier
                                            .clickable {
                                                setConversation(it)
                                                changeConversationBarState()
                                            },
                                        colors = CardDefaults.cardColors(

                                            containerColor=(if (it == CurrentConversation) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background),
                                        ),

                                        ){
                                        Text(
                                            text = it.title,
                                            modifier = Modifier
                                                .padding(10.dp),
                                            color = if(it==CurrentConversation)MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                                            fontSize = ((17 * FragmentWidth)).sp,
                                            textAlign = TextAlign.Center,
                                        )
                                    }

                                    Spacer(modifier =Modifier.height(12.dp))
                                }
                                Spacer(modifier =Modifier.height(25.dp))
                            }


                        }

                    }
                }
            }
    }
}


fun isCreatedToday(date: LocalDate): Boolean {
    val today = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        return true;
    }
    // Compare the date part of createdAt with today
    return date == today
}

fun convertStringToDate(date: String): LocalDate {

    val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    } else {
        return LocalDate.now();
    }
    return LocalDate.parse(date, formatter)
}

fun isYesterday(date: LocalDate): Boolean {
    val yesterday = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().minusDays(1)
    } else {
        return false;
    }
    return date == yesterday
}

fun isThisWeek(date: LocalDate): Boolean {
    val today = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        return false;
    }
    val startOfWeek = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    } else {
        return false;
    }
    return date in startOfWeek..today
}

fun isLastMonth(date: LocalDate): Boolean {
    val today = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        return false;
    }
    val firstDayOfLastMonth = today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth())
    val lastDayOfLastMonth = today.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth())
    return date in firstDayOfLastMonth..lastDayOfLastMonth
}

fun convertStringToDateTime(dateString: String): LocalDateTime {
    val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    return LocalDateTime.parse(dateString, formatter)
}

// Sorting the messages by createdAt
fun sortMessagesByDate(messages: List<Message>): List<Message> {
    return messages.sortedByDescending { convertStringToDateTime(it.createdAt) }
}



@Composable
fun MessageView(post:Message,CurrentConversationId:Long,FragmentWidth:Float,new:Boolean=false,setNewState:(Boolean)->Unit) {
    val parts = splitCode(post.answer)
    var currentText by remember { mutableStateOf("") }
    var currentIndex by remember { mutableStateOf(0) }
    val (isLiked,setIsLike) = remember { mutableStateOf(0) }
    setIsLike(post.isLiked)

    val coroutineScope = rememberCoroutineScope()
    val gson = Gson()

    LaunchedEffect(parts) {
        if(new)
        parts.forEach { part ->
            for (i in 1..part.length) {
                currentText = part.take(i)
                delay(50) // Adjust typing speed here
            }
            delay(300) // Pause between parts
            currentIndex++
        }
        else{
            currentIndex=parts.size
        }
        setNewState(false)
    }
    Column(){
        Row (
            modifier = Modifier
                .fillMaxWidth()

        ){
            Spacer(modifier =Modifier
                .fillMaxWidth(.2f))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding((20 * FragmentWidth).dp)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape((15 * FragmentWidth).dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape((30*FragmentWidth).dp)

            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Column {
                        Text(text = post.question)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = convertStringToDate(post.createdAt).dayOfMonth.toString() + "/" + convertStringToDate(post.createdAt).monthValue.toString() + "/" + convertStringToDate(post.createdAt).year.toString()+" "+convertStringToDateTime(post.createdAt).hour.toString()+":"+convertStringToDateTime(post.createdAt).minute.toString(),
                            modifier = Modifier
                                .align(Alignment.End),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        )
                    }
                }

            }


        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape((15 * FragmentWidth).dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape((30 * FragmentWidth).dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Column {
                        parts.take(currentIndex).forEach { part ->
                            if (part.startsWith("<code>")) {
                                CodeDisplayScreen(part)
                            } else {
                                Text(text = part)
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }

                        // Display the current typing text
                        if (currentIndex < parts.size) {
                            if (parts[currentIndex].startsWith("<code>")) {
                                CodeDisplayScreen(currentText)
                            } else {
                                Text(text = currentText)
                            }
                        }

                        Row(){
                            Image(
                                modifier = Modifier
                                    .padding((5 * FragmentWidth).dp)
                                    .width((30 * FragmentWidth).dp)
                                    .aspectRatio(1f)
                                    .clickable(onClick = {

                                        coroutineScope.launch {
                                            try {
                                                val call = Services
                                                    .getChatBotService()
                                                    .likeAnswer(
                                                        "Bearer " + Data.token,
                                                        ReactionMessageRequest(
                                                            CurrentConversationId,
                                                            post.id
                                                        )
                                                    )
                                                val response =
                                                    withContext(Dispatchers.IO) { call.execute() }
                                                if (response.isSuccessful) { // 200..300
                                                    val body = response.body()
                                                    if (body != null) {
                                                        println("body: " + body);
                                                        println("body: " + Gson().toJson(body));
                                                        println("code: " + response.code());
                                                        if (isLiked == 1) {
                                                            setIsLike(0)
                                                            post.isLiked = 0
                                                        } else {
                                                            setIsLike(1)
                                                            post.isLiked = 1
                                                        }
                                                    } else {

                                                    }
                                                } else {

                                                }
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        }


                                    }),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                                painter = painterResource(id = if(isLiked==1)R.drawable.like_filled else R.drawable.like),
                                contentDescription = "UpVote",

                                )

                            Spacer(modifier = Modifier.width((20*FragmentWidth).dp))
                            Text(text = "|",
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                fontSize = (30*FragmentWidth).sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width((10*FragmentWidth).dp))
                            Image(
                                modifier = Modifier
                                    .padding((5 * FragmentWidth).dp)
                                    .width((30 * FragmentWidth).dp)
                                    .aspectRatio(1f)
                                    .clickable(onClick = {

                                        coroutineScope.launch {
                                            try {
                                                val call = Services
                                                    .getChatBotService()
                                                    .dislikeAnswer(
                                                        "Bearer " + Data.token,
                                                        ReactionMessageRequest(
                                                            CurrentConversationId,
                                                            post.id
                                                        )
                                                    )
                                                val response =
                                                    withContext(Dispatchers.IO) { call.execute() }
                                                if (response.isSuccessful) { // 200..300
                                                    val body = response.body()
                                                    if (body != null) {
                                                        println("body: " + body);
                                                        println("body: " + Gson().toJson(body));
                                                        println("code: " + response.code());
                                                        if (isLiked == -1) {

                                                            setIsLike(0)
                                                            post.isLiked = 0
                                                        } else {

                                                            setIsLike(-1)
                                                            post.isLiked = -1
                                                        }
                                                    } else {

                                                    }
                                                } else {

                                                }
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        }


                                    }),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                                painter = painterResource(id = if(isLiked==-1)R.drawable.dislike_filled else R.drawable.dislike),
                                contentDescription = "UpVote",

                                )
                        }
                    }
                }
            }
        }



    }



}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Input(FragmentWidth:Float,InputText:String, setInputText:(String)->Unit,sendQuestion:()->Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(15.dp),
                value = InputText,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                onValueChange = { setInputText(it)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,       // Color when focused
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,     // Color when unfocused
                    disabledBorderColor =MaterialTheme.colorScheme.onSurface,         // Color when there's an error
                    containerColor = MaterialTheme.colorScheme.background,        // Color of the TextField
                    ),
                label = { Text("Write Your Question Here!", color = MaterialTheme.colorScheme.onBackground) }, // Label similar to TextInputLayout
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.88f)
                    .padding(10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.send),
                contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = {
                        sendQuestion()
                        println("send clicked")
                    })
                    .padding(end = 5.dp),
                alignment = Alignment.CenterStart,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}


fun splitCode(text:String): MutableList<String> {
    val regex = "(<code>(.*)</code>)".toRegex()

    val parts = mutableListOf<String>()

    var lastIndex = 0

    // Extract matches and split text
    regex.findAll(text).forEach { match ->
        val beforeCode = text.substring(lastIndex, match.range.first)
        val codeContent = match.value // Keep the <code> tag and its content

        if (beforeCode.isNotBlank()) {
            parts.add(beforeCode.trim()) // Add the text before the <code> tag
        }
        parts.add(codeContent.trim()) // Add the code content

        lastIndex = match.range.last + 1
    }

    // Add remaining text after the last match
    if (lastIndex < text.length) {
        parts.add(text.substring(lastIndex).trim())
    }
    return parts;
}

@Composable
fun CodeDisplayScreen(input: String) {
    Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {
            val code = input.removePrefix("<code>").removeSuffix("</code>")
            HighlightedText(text =code)

        }
    }
}

@Composable
fun HighlightedText(text: String) {

    val parts = Regex("[|.(){} ,]+|[^|.(){} ,]+")
        .findAll(text)
        .map { it.value }
        .toList()
    println(parts)
    val highlightedText = buildAnnotatedString {
        val words = parts
        for (word in words) {
            if (word == "map" || word == "Flux") {
                withStyle(style = SpanStyle(color = hexToColor("#2e95d3"))) {
                    append("$word ")
                }
            }
            else if (word == "module" || word == "requires") {
                withStyle(style = SpanStyle(color = hexToColor("#2e95d3"))) {
                    append("$word ")
                }
            }
            else if (Regex("[^a-zA-Z0-9]+").matches(word)) {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("$word ")
                }
            }
            else if (Regex(" *[0-9]+").matches(word)) {
                withStyle(style = SpanStyle(color = hexToColor("#df3079"))) {
                    append("$word ")
                }
            }
            else if (word == "map" || word == "Flux") {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("$word ")
                }
            }
            else {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("$word ")
                }
            }
        }
    }

    BasicText(text = highlightedText)
}
