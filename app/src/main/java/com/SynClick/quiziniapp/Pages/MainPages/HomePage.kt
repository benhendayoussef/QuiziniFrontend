package com.SynClick.quiziniapp.Pages.MainPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.Services
import com.SynClick.quiziniapp.Data.Data
import com.SynClick.quiziniapp.Data.Data.topics
import com.SynClick.quiziniapp.Data.Data.user
import com.SynClick.quiziniapp.Data.Data.userTopics
import com.SynClick.quiziniapp.Data.Models.Questionnaire
import com.SynClick.quiziniapp.Data.Models.RequestsModel.CreateQuizRequest
import com.SynClick.quiziniapp.Data.Models.Topic
import com.SynClick.quiziniapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomePage(FragmentWidth:Float,onSelectedItemChange: (String) -> Unit) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val (questionnaires, setQuestionnaires) = remember { mutableStateOf<List<Questionnaire>?>(null) }
    val (creatingQuiz, setcreatingQuiz) = remember { mutableStateOf(false) }

    val (isQuestionnaireLoading, setQuestionnaireLoading) = remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Spacer(modifier =Modifier.height((20*FragmentWidth).dp))
        Text(
            "Your topics",
            modifier = Modifier.padding((10*FragmentWidth).dp),
            fontSize = (25*FragmentWidth).sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        LazyHorizontalGrid(rows = GridCells.Fixed(1),
            modifier = Modifier
                .fillMaxWidth()
                .height((100 * FragmentWidth).dp),

        ) {
            items(userTopics?.size ?: 0) { index ->

                    val painter = rememberImagePainter(data = userTopics[index].imageUrl)
                    Image(

                        painter = painter,
                        contentDescription = userTopics[index].name,
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .aspectRatio(10 / 6f),
                        contentScale = ContentScale.Fit
                    )

            }
        }
        Spacer(modifier =Modifier.height((20*FragmentWidth).dp))


        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height((250 * FragmentWidth).dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            shape= RoundedCornerShape(30.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            hexToColor("#fed722"),
                            hexToColor("#ff950f")
                        )
                    )
                )){
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 5.dp)
                        .fillMaxWidth(0.5f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        Text(text = "New Quizz",
                            fontSize = (28*(FragmentWidth)).sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding((5*FragmentWidth).dp)
                        )
                        Text(text = "Start a new AI generated Quizz",
                            fontSize = (12*FragmentWidth).sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding((5*FragmentWidth).dp),
                            textAlign = TextAlign.Center
                        )
                        Button(onClick =

                        {


                            coroutineScope.launch {
                                setcreatingQuiz(true)
                                try {
                                    val call = Services.getQuestionnaireService()
                                        .getQuestionnaire("Bearer " + Data.token,
                                            CreateQuizRequest(
                                            20, userTopics.map { it.id.toInt() }.toList()))
                                    val response =
                                        withContext(Dispatchers.IO) { call.execute() }
                                    if (response.isSuccessful) {
                                        val quizReponse = response.body()
                                        if (quizReponse != null) {
                                            println(quizReponse.questions.size)
                                            Data.ActualQQuestionnaire = quizReponse
                                            onSelectedItemChange("quiz")
                                        }
                                    }
                                    else{
                                        println("Error: ${response.code()}")
                                        // Handle error
                                    }
                                }catch (e: Exception) {
                                    println("Exception: $e")
                                    // Handle exception
                                }
                                finally {
                                    setcreatingQuiz(false)
                                }
                            }


                        },
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .height((50*FragmentWidth).dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                            )
                        ){
                            Text(text =if(creatingQuiz) "Creating ..." else "Start Quizz",
                                fontSize = (14*FragmentWidth).sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding((5*FragmentWidth).dp)
                            )

                        }
                    }

                    Image(painter = painterResource(id = R.drawable.onboarding2),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        alignment = Alignment.CenterStart
                    )
                }
            }

        }



        Spacer(modifier =Modifier.height((20*FragmentWidth).dp))
        Text(
            "Quizzes",
            modifier = Modifier.padding((10*FragmentWidth).dp),
            fontSize = (25*FragmentWidth).sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                try {
                    val call = Services.getQuestionnaireService().getAllQuestionnaires("Bearer "+ Data.token)
                    val response = withContext(Dispatchers.IO) { call.execute() }
                    if (response.isSuccessful) {
                        val questionnairesResponse = response.body()
                        if(questionnairesResponse != null){
                            println(questionnairesResponse.questionnaire)
                            Data.Questionnaires = questionnairesResponse.questionnaire
                            setQuestionnaires(questionnairesResponse.questionnaire)
                            println(questionnairesResponse.message)

                        }


                    } else {
                        println("Error: ${response.code()}")
                        // Handle error
                    }
                } catch (e: Exception) {
                    println("Exception: $e")
                    // Handle exception
                } finally {
                    setQuestionnaireLoading(false)
                }
            }
        }

        if (isQuestionnaireLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                println(Data.Questionnaires?.size)
                items(questionnaires?.size ?: 0) { index ->
                    val questionnaire = questionnaires!![index]
                    if (questionnaire != null) {
                        val cardColor=
                            if(index%4 ==0)
                                hexToColor("#a962fe")
                            else if(index%4==1)
                                hexToColor("#ffb218")
                            else if(index%4==2)
                                hexToColor("#1db6fe")
                            else
                                hexToColor("#06d796")

                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = cardColor,
                            )
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(text ="Date: "+(questionnaire.answeredAt?.toString()?.removeRange(10,questionnaire.answeredAt.toString().length)),
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontSize = (15*FragmentWidth).sp,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(text ="Correctly Answered: "+(questionnaire?.numberCorrectedAnswers.toString())+"/"+(questionnaire.numberOfQuestions.toString()),
                                    textAlign = TextAlign.Center,
                                    fontSize = (15*FragmentWidth).sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Text(text = "Completed: ",
                                        textAlign = TextAlign.Center,
                                        fontSize = (15*FragmentWidth).sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(10.dp),

                                    )
                                    val imageRes = when (questionnaire.completed) {
                                        true -> R.drawable.tick
                                        else -> R.drawable.cross
                                    }
                                    val tintColor = if (questionnaire.completed) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary

                                    Image(
                                        painter = painterResource(id = imageRes),
                                        contentDescription = null,
                                        modifier = Modifier.size((50*FragmentWidth).dp),
                                        alignment = Alignment.CenterStart,
                                        colorFilter = ColorFilter.tint(tintColor)


                                    )
                                }
                            }
                        }


                    }
                }
            }
        }

    }

}
fun hexToColor(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}