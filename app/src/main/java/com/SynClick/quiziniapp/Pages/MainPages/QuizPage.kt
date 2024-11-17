import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.MediaCodec.QueueRequest
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.HorizontalAlign
import coil.compose.AsyncImage
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.Services
import com.SynClick.quiziniapp.Data.Data
import com.SynClick.quiziniapp.Data.Data.userTopics
import com.SynClick.quiziniapp.Data.Models.Question
import com.SynClick.quiziniapp.Data.Models.QuestionReponse
import com.SynClick.quiziniapp.Data.Models.Questionnaire
import com.SynClick.quiziniapp.Data.Models.RequestsModel.CorrectQuestionnaireRequest
import com.SynClick.quiziniapp.Data.Models.RequestsModel.CreateQuizRequest
import com.SynClick.quiziniapp.R
import com.google.gson.Gson
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.droidsonroids.gif.GifDrawable
import kotlin.random.Random

@Composable
fun QuizPage(FragmentWidth: Float, setTopMenuVisibility: (Boolean) -> Unit,goToHome:()->Unit) {
    val context = LocalContext.current
    if(Data.ActualQQuestionnaire!=null) {
        val questions = Data.ActualQQuestionnaire.questions
        val (currentState, setCurrentState) = remember { mutableStateOf(1) }
        val (answers, setAnswers) = remember { mutableStateOf(emptyList<QuestionReponse>()) }
        val (currentQuestion, setCurrentQuestion) = remember { mutableStateOf(0) }

        LaunchedEffect(answers) {
            println("answers:" + answers.size)
            answers.map { answer ->
                println("question:" + answer.questionId)
                println("answers:" + answer.answersId.size)
                println("time:" + answer.respondingTime)
            }
        }

        if (currentState == 1) {
            startQuiz(FragmentWidth, { setCurrentState(2) })
        } else if (currentState == 2) {
            questionPage(
                FragmentWidth,
                currentQuestion,
                questions[currentQuestion],
                { setCurrentQuestion(currentQuestion + 1) },
                { setCurrentState(3) },
                setTopMenuVisibility,
                { answer ->
                    setAnswers(answers + answer)
                })
        } else if (currentState == 3) {
            setTopMenuVisibility(false)
            endQuiz(FragmentWidth, { setCurrentState(1) }, answers, { goToHome() })
        }
    }
    else{

    }
}

@Composable
fun startQuiz(FragmentWidth: Float,nextState:()->Unit) {
    var progress by remember { mutableStateOf(0.0f) }
    var isPressed by remember { mutableStateOf(false) }

    // Manage progress logic
    LaunchedEffect(isPressed) {
        if (isPressed) {
            val startTime = System.currentTimeMillis()
            while (isPressed && progress < 1.0f) {
                val elapsedTime = System.currentTimeMillis() - startTime
                progress = (elapsedTime / 2000f).coerceAtMost(1.0f) // Fill over 2 seconds
                delay(10) // Smooth updates (60 FPS)
                if(progress == 1.0f){
                    nextState()
                }
            }
        } else {
            progress = 0.0f // Reset progress when button is released
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Are You Ready?",
            fontWeight = FontWeight.Bold,
            fontSize = (30*FragmentWidth).sp)
        Text(text = "Hold the Button to Fill the Coffee Cup",
            fontWeight = FontWeight.Bold,
            fontSize = (14*FragmentWidth).sp)

        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier
            .fillMaxWidth(),) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.question),
                    contentDescription = "Question Info",
                    modifier = Modifier.width((60 * FragmentWidth).dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)

                )

                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "20",
                        fontWeight = FontWeight.Black,
                        fontSize = (25 * FragmentWidth).sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Multiple Choices Questions",
                        fontWeight = FontWeight.Medium,
                        fontSize = (15 * FragmentWidth).sp
                    )
                }

            }
            Spacer(modifier =Modifier.height((40*FragmentWidth).dp))

            Row {
                Image(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "Question Info",
                    modifier = Modifier.width((60 * FragmentWidth).dp),

                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )

                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "2 minutes",
                        fontWeight = FontWeight.Black,
                        fontSize = (25 * FragmentWidth).sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Recommanded by Question",
                        fontWeight = FontWeight.Medium,
                        fontSize = (15 * FragmentWidth).sp
                    )
                }

            }

            Spacer(modifier =Modifier.height((40*FragmentWidth).dp))
            DashedLine(color = Color.Black, thickness = 2.dp, dashLength = 8.dp, gapLength = 4.dp)

            Spacer(modifier =Modifier.height((40*FragmentWidth).dp))
            Text(text = "Before You Start,",
                fontWeight = FontWeight.Bold,
                fontSize = (25*FragmentWidth).sp)
            val items = listOf("You must complete this test in one session make sure your internet is stable",
                "1 mark awarded for a correct answer. No marking Will bo there for wrong answer.",
                "the result of your test will be displayed at the end of the test.",
                "You can not go back to the previous question once you have moved to the next question.")
            BulletPointList(items = items,FragmentWidth = FragmentWidth)
        }




        Spacer(modifier = Modifier.height(2.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = Color.Transparent

        )

        Spacer(modifier = Modifier.height(2.dp))

        Card(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            try {
                                awaitRelease()
                            } finally {
                                isPressed = false
                            }
                        }
                    )
                }
                .padding(16.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            )
        ){
            Text(text ="Hold to start",
                modifier = Modifier.padding((16*FragmentWidth).dp),

                fontSize = (25*FragmentWidth).sp,)
        }
    }
}

@Composable
fun endQuiz(FragmentWidth: Float,nextState:()->Unit,reponse:List<QuestionReponse>,goToHome:()->Unit) {

    val quotes = listOf(
        "Time is the school in which we learn, time is the fire in which we burn.",
        "Lost time is never found again.",
        "The bad news is time flies. The good news is you’re the pilot.",
        "Don’t spend time beating on a wall, hoping it will transform into a door.",
        "Live as if you were to die tomorrow. Learn as if you were to live forever.",
        "Tell me and I forget, teach me and I may remember, involve me and I learn.",
        "The beautiful thing about learning is that nobody can take it away from you.",
        "Wisdom is not a product of schooling but of the lifelong attempt to acquire it."
    )

    val authors = listOf(
        "Delmore Schwartz",
        "Benjamin Franklin",
        "Michael Altshuler",
        "Coco Chanel",
        "Mahatma Gandhi",
        "Benjamin Franklin",
        "B.B. King",
        "Albert Einstein"
    )
    val rand = remember {mutableStateOf(Random.nextInt(quotes.size))}



    val (Result, setResult) = remember { mutableStateOf(Questionnaire()) }
    val coroutineScope = rememberCoroutineScope()
    val (error, setError) = remember { mutableStateOf(false) }
    val (errorMessage, setErrorMessage) = remember { mutableStateOf("") }

    val (GettingResult, setGettingResult) = remember { mutableStateOf(false) }
    val serverResponseDeferred = CompletableDeferred<Unit>()

    val gson = Gson()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                println(Data.ActualQQuestionnaire.quiz_id.toInt())
                setGettingResult(false)
                val call = Services.getQuestionnaireService()
                    .correctQuestionnaire(
                        "Bearer " + Data.token,
                        CorrectQuestionnaireRequest(
                            Data.ActualQQuestionnaire.quiz_id.toInt(),
                            reponse
                        )
                    )
                val response = withContext(Dispatchers.IO) { call.execute() }
                if (response.isSuccessful) {
                    val result = response.body()
                    println(result)
                    if (result != null) {
                        println(result.message)
                    }
                    if (result != null) {
                        result.questionnaire?.let { setResult(it) }
                    }

                } else {
                    println("error" + response.errorBody()?.string())
                    println("error" + response.message())
                    println("error" + response.code())
                    setError(true)
                    setErrorMessage(response.message())

                }
            } catch (e: Exception) {
                println(e)
            }
            finally {
                serverResponseDeferred.complete(Unit)

            }
        }
        coroutineScope.launch {
            delay(5000)
            serverResponseDeferred.await()
            setGettingResult(true)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Result of the Quiz",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = (30*FragmentWidth).sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((500 * FragmentWidth).dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                    ) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                            .padding(16.dp)


                        ){
                            if(GettingResult){
                                val pourcentage= remember { mutableStateOf(Result.numberCorrectedAnswers*100/Result.numberOfQuestions)}
                               Column(
                                   modifier = Modifier.fillMaxSize(),
                                   verticalArrangement = Arrangement.Center,
                                   horizontalAlignment = Alignment.CenterHorizontally
                               ) {
                                   Text(text =  when {
                                       pourcentage.value < 50 -> "Try harder next time."
                                       pourcentage.value < 70 -> "You're doing okay, but there's room for improvement."
                                       pourcentage.value >= 70 -> "Congratulations! You've done a great job!"
                                       else -> "Invalid score."
                                   },
                                       textAlign = TextAlign.Center,
                                       fontWeight = FontWeight.Bold,
                                       lineHeight = 30.sp,
                                       color = MaterialTheme.colorScheme.primary,
                                       fontSize = (25*FragmentWidth).sp)
                                   Spacer(modifier = Modifier.height(16.dp))

                                   Text(text ="${pourcentage.value}%",
                                       fontWeight = FontWeight.Black,
                                       color = if(pourcentage.value<50)MaterialTheme.colorScheme.secondary else if(pourcentage.value<80)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                                       fontSize = (50*FragmentWidth).sp)
                               } 
                            }
                            else{
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = quotes[rand.value],
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 30.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = (25*FragmentWidth).sp)
                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(text = authors[rand.value],
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontSize = (20*FragmentWidth).sp)
                                }
                            }

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Card(modifier = Modifier
                                .width(40.dp)
                                .aspectRatio(1f),
                                shape = RoundedCornerShape(
                                    topStart =0.dp,
                                    bottomStart = 0.dp,
                                    topEnd = 20.dp,
                                    bottomEnd = 20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {

                            }
                            DashedLine(color = Color.Black, thickness = 2.dp, dashLength = 8.dp, gapLength = 4.dp,
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(2.dp))
                            Card(modifier = Modifier
                                .width(40.dp)
                                .aspectRatio(1f),
                                shape = RoundedCornerShape(
                                    topStart =20.dp,
                                    bottomStart = 20.dp,
                                    topEnd = 0.dp,
                                    bottomEnd = 0.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ){}

                        }

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                        ){

                            if (GettingResult) {
                                val pourcentage =remember { mutableStateOf(Result.numberCorrectedAnswers / Result.numberOfQuestions * 100) }
                                Image(
                                    painter = painterResource(id = if(pourcentage.value>=80)R.drawable.gold else if(pourcentage.value>=50)R.drawable.argent else R.drawable.bronze),
                                    contentDescription = "Question Info",
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                            else{
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.background),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                    }



                    }


                }
            },
        bottomBar = {

            Row {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            if(GettingResult){
                                goToHome()
                                Data.Questionnaires.add(Result)
                            }
                        }),

                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),

                ){

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        Text(text =if(GettingResult) "Back To Home" else "Waiting for the result ...",
                            modifier = Modifier,
                            textAlign = TextAlign.Right,
                            fontSize = (20*FragmentWidth).sp,)
                        if(GettingResult){
                            Image(
                                painter = painterResource(id = R.drawable.next_arrow),
                                contentDescription = "Question Info",
                                modifier = Modifier.width((35 * FragmentWidth).dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)

                            )
                        }
                    }

                }
            }
        }

    )

}
@SuppressLint("MutableCollectionMutableState")
@Composable
fun questionPage(FragmentWidth: Float,currentQuestion:Int,question:Question,nextQuestion:()->Unit,nextState:()->Unit,setTopMenuVisibility: (Boolean) -> Unit,addAnswer:(QuestionReponse)->Unit) {
    var selectedAnswer by remember { mutableStateOf(HashMap<String, Boolean>()) }
    var progress by remember { mutableStateOf(0.0f) }
    var duration by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(true) }
    LaunchedEffect(currentQuestion) {
        duration=0
        progress=0.0f
        isRunning=true
    }



    LaunchedEffect(isRunning) {
        if (isRunning) {
            val startTime = System.currentTimeMillis()
            while (isRunning) {
                val elapsedTime = System.currentTimeMillis() - startTime
                duration=(elapsedTime/1000).toInt()
                if (progress < 1.0f) {
                    progress = (elapsedTime / (1000 * 120).toFloat()).coerceAtMost(1.0f)
                }
                delay(10) // Smooth updates (100 FPS)

            }
        }
    }
    setTopMenuVisibility(false)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height((80 * FragmentWidth).dp)
                .padding(16.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)            )
        ){
            Box(modifier = Modifier.fillMaxSize()){
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    color = if(progress<=0.5f)MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
                    trackColor = Color.Transparent
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(

                        text = if(duration<=120)if(duration>=60)"${duration/60} minutes ${duration%60} seconds" else "${duration} seconds" else "OverTime",
                        fontWeight = FontWeight.Bold,
                        fontSize = (18 * FragmentWidth).sp,
                        color = if(progress<=0.5f)MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                            painter = painterResource(id = R.drawable.timer),
                    contentDescription = "Question Info",
                    modifier = Modifier.width((30 * FragmentWidth).dp),
                        colorFilter = ColorFilter.tint(if(progress<=0.5f)MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }


        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White

            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(text = "Question ${currentQuestion+1}/${Data.ActualQQuestionnaire.questions.size}",
                    fontWeight = FontWeight.Black,
                    fontSize = (20*FragmentWidth).sp,
                    color = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = question.question,
                    fontWeight = FontWeight.Bold,
                    fontSize = (24*FragmentWidth).sp)
                Spacer(modifier = Modifier.height(16.dp))
                question.propositions.map { reponse ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((100 * FragmentWidth).dp)
                            .border(
                                3.dp,
                                if (selectedAnswer[reponse.key] == true) MaterialTheme.colorScheme.tertiary else hexToColor(
                                    "#e9e9e9"
                                ),
                                RoundedCornerShape(30.dp)
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        selectedAnswer[reponse.key] =
                                            !(selectedAnswer[reponse.key] ?: false)
                                    }
                                )
                            },
                        shape = RoundedCornerShape(30.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if(selectedAnswer[reponse.key] == true)MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f) else Color.White
                        )
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${reponse.key.toInt()}.",
                                fontWeight = FontWeight.Medium,
                                fontSize = (17*FragmentWidth).sp,
                                color = if(selectedAnswer[reponse.key] == true)MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = reponse.value,
                                fontWeight = FontWeight.Medium,
                                fontSize = (17*FragmentWidth).sp,
                                color = if(selectedAnswer[reponse.key] == true)MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                }

            }

        }
        Row {
            Spacer(modifier =Modifier.weight(1f))
            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .clickable(onClick = {
                        isRunning = false
                        if (selectedAnswer.size > 0) {
                            addAnswer(
                                QuestionReponse(
                                    question.id,
                                    duration,
                                    selectedAnswer.filter { it.value == true }.keys
                                        .map { it.toInt() }
                                        .toList()
                                )
                            )

                            if (currentQuestion < Data.ActualQQuestionnaire.questions.size - 1) {
                                nextQuestion()
                            } else {
                                nextState()
                            }

                            selectedAnswer=HashMap()//remove this for testing
                        }


                    }),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ){

                Row(
                    modifier = Modifier
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){

                    Text(text =if(currentQuestion<Data.ActualQQuestionnaire.questions.size-1)"Next Question" else "get Result",
                        modifier = Modifier,
                        textAlign = TextAlign.Right,
                        fontSize = (20*FragmentWidth).sp,)
                    Image(
                        painter = painterResource(id = R.drawable.next_arrow),
                        contentDescription = "Question Info",
                        modifier = Modifier.width((35 * FragmentWidth).dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)

                    )
                }

            }
        }


    }


}


@Composable
fun DashedLine(
    color: Color = Color.Gray,
    thickness: Dp = 1.dp,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 10.dp,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxWidth()) {
        val canvasWidth = size.width
        val dashPx = dashLength.toPx()
        val gapPx = gapLength.toPx()
        var startX = 0f

        while (startX < canvasWidth) {
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(startX, 0f),
                end = androidx.compose.ui.geometry.Offset(startX + dashPx, 0f),
                strokeWidth = thickness.toPx(),
                cap = Stroke.DefaultCap
            )
            startX += dashPx + gapPx
        }
    }
}

@Composable
fun BulletPointList(items: List<String>,FragmentWidth: Float) {
    Column(modifier = Modifier.padding(16.dp)) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size((8 * FragmentWidth).dp)
                        .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width((8*FragmentWidth).dp))
                Text(text = item, fontSize = (16*FragmentWidth).sp)
            }
            Spacer(modifier = Modifier.height((8*FragmentWidth).dp))
        }
    }
}

fun hexToColor(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}