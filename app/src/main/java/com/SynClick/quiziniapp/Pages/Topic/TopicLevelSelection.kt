package com.SynClick.quiziniapp.Pages.Topic

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.SynClick.quiziniapp.Pages.MainPages.MainPage
import com.SynClick.quiziniapp.R
import com.SynClick.quiziniapp.ui.theme.QuiziniAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Objects

class TopicLevelSelection : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuiziniAppTheme {
                TopicsLevelFragment()
            }
        }
    }
}


@Composable
fun TopicsLevelFragment() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val (topics, setTopics) = remember { mutableStateOf<List<Topic>?>(null) }
    val (topicRank, setTopicRank) = remember { mutableStateOf<Map<String, Double>?>(null) }
    val (selectedDifficulties, setSelectedDifficulties) = remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    val (isLoading, setIsLoading) = remember { mutableStateOf(true) }
    val (isSending, setIsSending) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val callSelected = Services.getTopicService().getAllActivatedUserTopics("Bearer " + Data.token)
                val responseSelected = withContext(Dispatchers.IO) { callSelected.execute() }
                if (responseSelected.isSuccessful) {
                    println("Response: ${responseSelected.body()}")
                    println("Response Size: ${responseSelected.body()?.userTopics?.size}")
                    Data.userTopics = responseSelected.body()?.userTopics
                    Data.userTopics.map { println(it.userRank) }
                    setTopics(Data.userTopics)
                    setTopicRank(Data.userTopics?.map { it as Topic }?.map { it.id to it.userRank }?.toMap() ?: emptyMap())
                    setSelectedDifficulties(Data.userTopics?.map { it.id to
                            if(it.userRank<=0.1) "Entry-level"
                            else if(it.userRank<= 0.2) "Mid-level"
                            else if(it.userRank<=0.3) "Experienced"
                            else if(it.userRank<=0.4)"I've suffered enough"
                            else "Entry-level"
                    }?.toMap() ?: emptyMap())

                } else {
                    println("Error: ${responseSelected.code()}")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            Text(
                text = "Please Select Your Topic Level",
                textAlign = TextAlign.Center,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                items(topics?.size ?: 0) { index ->
                    Column {
                        Card(
                            shape = RoundedCornerShape(22.dp),
                            modifier = Modifier
                                .padding(50.dp)
                                .align(Alignment.CenterHorizontally),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        ) {
                            TopicLevel(topicInfo = topics!![index])
                        }
                        println("Topic: ${Data.userTopics[index].userRank}")

                        DifficultySelector(
                            topic = topics!![index].name,
                            onDifficultySelected = { difficulty ->
                                val newSelectedDifficulties = selectedDifficulties.toMutableMap()
                                newSelectedDifficulties[topics[index].id] = difficulty
                                setSelectedDifficulties(newSelectedDifficulties)
                            },
                            level = selectedDifficulties[topics[index].id]?.let {
                                when (it) {
                                    "Entry-level" -> 0
                                    "Mid-level" -> 1
                                    "Experienced" -> 2
                                    "I've suffered enough" -> 3
                                    else -> 2
                                }
                            } ?: 3,
                            changeable=Data.userTopics[index].userRank==0.0

                        )
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                setIsSending(true)
                                println("Selected difficulties: $selectedDifficulties")
                                val a: List<Map<String, Object>> = selectedDifficulties.map { (topicId, difficulty) ->
                                    mapOf(
                                        "topicId" to Integer.parseInt(topicId) as Object,
                                        "rank" to when (difficulty) {
                                            "Entry-level" -> 1 as Object
                                            "Mid-level" -> 2 as Object
                                            "Experienced" -> 3 as Object
                                            "I've suffered enough" -> 4 as Object
                                            else -> 1 as Object
                                        }
                                    )
                                }

                                val call = Services.getClientService().updateUserRanks("Bearer " + Data.token, a)
                                val response = withContext(Dispatchers.IO) { call.execute() }
                                if (response.isSuccessful) {
                                    println("Response: ${response.body()?.message}")

                                    val intent = Intent(context, /*OnBoardingScreen*/MainPage::class.java)
                                    context.startActivity(intent)
                                    (context as? ComponentActivity)?.finish() // Optional: finish the current activity so the user can't go back to it



                                } else {
                                    println("Error: ${response.code()}")
                                    println("Error Body: ${response.errorBody()?.string()}")
                                    println("Error Message: ${response.message()}")
                                    // Handle error
                                }
                                setIsSending(false)
                            } catch (e: Exception) {
                                println("Exception: $e")
                            } finally {
                                setIsSending(false)
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 15.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    if (isSending) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                        }
                    } else {
                        Text(
                            "Continue",
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(5.dp),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DifficultySelector(topic: String, level: Int,changeable:Boolean, onDifficultySelected: (String) -> Unit) {
    val difficultyLevels = listOf("Entry-level", "Mid-level", "Experienced", "I've suffered enough")
    var sliderPosition by remember { mutableStateOf(level) }
    val selectedDifficulty = difficultyLevels[sliderPosition]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (changeable) "Select Difficulty for $topic" else "$topic is already selected",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (changeable) {
            Slider(
                value = sliderPosition.toFloat(),
                onValueChange = {
                    sliderPosition = it.toInt()

                    onDifficultySelected(selectedDifficulty)
                },
                valueRange = 0f..(difficultyLevels.size - 1).toFloat(),
                steps = difficultyLevels.size - 2,
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Selected Difficulty: $selectedDifficulty",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground)

        }
    }
}

@Composable
fun TopicLevel(topicInfo: Topic) {
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


