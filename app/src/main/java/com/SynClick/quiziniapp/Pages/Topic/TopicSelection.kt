package com.SynClick.quiziniapp.Pages.Topic

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.ClientService
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.Services
import com.SynClick.quiziniapp.Data.Data
import com.SynClick.quiziniapp.Data.Models.Topic
import com.SynClick.quiziniapp.Data.Models.userEntityDto
import com.SynClick.quiziniapp.R
import com.SynClick.quiziniapp.ui.theme.QuiziniAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopicSelection : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuiziniAppTheme {
                TopicsFragment()
            }
        }
    }
}
@Composable
fun TopicsFragment() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val (topics, setTopics) = remember { mutableStateOf<List<Topic>?>(null) }
    val (selectedTopic, setSelectedTopic) = remember { mutableStateOf<Map<String, Boolean>?>(null) }
    val (isLoading, setIsLoading) = remember { mutableStateOf(true) }
    val (isSending, setIsSending) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val call = Services.getTopicService().getAllTopics("Bearer "+ Data.token)
                val response = withContext(Dispatchers.IO) { call.execute() }
                if (response.isSuccessful) {
                    println("Response: ${response.body()}")
                    println("Response Size: ${response.body()?.topics?.size}")
                    Data.topics=response.body()?.topics
                    setTopics(response.body()?.topics?.map { it as Topic } ?: emptyList())

                    val callSelected = Services.getTopicService().getAllActivatedUserTopics("Bearer "+ Data.token)
                    val responseSelected = withContext(Dispatchers.IO) { callSelected.execute() }
                    if (responseSelected.isSuccessful) {
                        println("Response: ${responseSelected.body()}")
                        println("Response Size: ${responseSelected.body()?.userTopics?.size}")
                        Data.userTopics=responseSelected.body()?.userTopics
                        setSelectedTopic(responseSelected.body()?.userTopics?.map { it as Topic }?.map { it.id to true }?.toMap() ?: emptyMap())
                    } else {
                        println("Error: ${responseSelected.code()}")
                        // Handle error
                    }
                } else {
                    println("Error: ${response.code()}")
                    // Handle error
                }
            } catch (e: Exception) {
                println("Exception: $e")
                // Handle exception
            } finally {
                setIsLoading(false)
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        ){
            Text(
                text = "Please Choose Your Topic",
                textAlign = TextAlign.Center,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()

            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                items(topics?.size ?: 0) { index ->
                    Card(
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable(onClick = {
                                val newSelectedTopic =
                                    selectedTopic?.toMutableMap() ?: mutableMapOf()
                                if (newSelectedTopic[topics!![index].id] == true) {
                                    newSelectedTopic[topics!![index].id] = false
                                } else {
                                    newSelectedTopic[topics!![index].id] = true
                                }
                                setSelectedTopic(newSelectedTopic)
                            }),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if(selectedTopic?.get(topics!![index].id) == true) Color(android.graphics.Color.parseColor("#"+topics!![index].colorRef)) else Color.Transparent
                        )


                    ) {
                        Card(
                            shape = RoundedCornerShape(22.dp),
                            modifier = Modifier
                                .padding(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor =MaterialTheme.colorScheme.background
                            )) {

                                Topic(topicInfo = topics!![index])
                            }

                    }
                }
            }
            Card (modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ){

                Button(onClick = {
                    if(selectedTopic?.filter { it.value }?.size!! >= 3)
                    coroutineScope.launch {
                        try {
                            setIsSending(true)
                            val call=Services.getClientService().saveSelectedTopics("Bearer " + Data.token, userEntityDto( selectedTopic?.filter { it.value }?.map { it.key.toInt() }?.toSet()!!))
                            val response = withContext(Dispatchers.IO) { call.execute() }
                            if (response.isSuccessful) {
                                Data.userTopics=Data.topics?.filter({ selectedTopic?.get(it.id) == true })?.map { it as Topic }
                                setIsSending(false)
                                val intent = Intent(context, /*OnBoardingScreen*/TopicLevelSelection::class.java)
                                context.startActivity(intent)

                            } else {
                                setIsSending(false)
                            }

                        } catch (e: Exception) {
                            println("Exception: $e")
                        } finally {
                            setIsSending(false)
                        }
                    }

                }
                    ,modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 15.dp)
                        .fillMaxWidth()

                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(selectedTopic?.filter { it.value }?.size!! >= 3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    )

                ){
                    if (isSending) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                        }
                    } else
                    Text(text=(if(selectedTopic?.filter { it.value }?.size!! >= 3) "Continue" else "desactivated" )+" ("+ selectedTopic?.filter { it.value }?.size+"/3 Selected)",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp)

                }
            }

        }

    }
}




@Composable
fun Topic(topicInfo: Topic) {
    val painter = rememberImagePainter(data = topicInfo.imageUrl)
    println("Loading image from URL: ${topicInfo.imageUrl}")

    Image(

        painter = painter,
        contentDescription = topicInfo.name,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .aspectRatio(10 / 6f),
        contentScale = ContentScale.Fit
    )
}



fun drawableToImageBitmap(context: Context, drawableId: Int): ImageBitmap {
    val drawable: Drawable = ContextCompat.getDrawable(context, drawableId) ?: throw IllegalArgumentException("Drawable not found")

    if (drawable is BitmapDrawable) {
        return drawable.bitmap.asImageBitmap()
    }

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = android.graphics.Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap.asImageBitmap()
}