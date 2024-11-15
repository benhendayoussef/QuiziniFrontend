package com.SynClick.quiziniapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.SynClick.quiziniapp.Assets.LoadingAnimation
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.ClientService
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.Services
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.TopicService
import com.SynClick.quiziniapp.Pages.Authentification.Authentif
import com.SynClick.quiziniapp.Pages.Topic.TopicSelection
import com.SynClick.quiziniapp.ui.theme.QuiziniAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuiziniAppTheme {
                SplashScreen()
            }
        }
        val Url = "http://192.168.1.14:8080/"
        //val UrlFac = "http://192.168.4.246:8080/"

        val retrofit = Retrofit.Builder()
            .baseUrl(Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val clientService: ClientService = retrofit.create(ClientService::class.java)
        Services.setClientService(clientService)
        val topicService: TopicService = retrofit.create(TopicService::class.java)
        Services.setTopicService(topicService)
    }
}

@Composable
@Preview(showBackground = true)
fun SplashScreen() {
    val durationMillis = 2000 // Animation duration
    var startAlpha by remember { mutableStateOf(0f) } // Initial alpha state
    val endAlpha = 1f // Final alpha value
    val context = LocalContext.current

    // Use LaunchedEffect to set the initial alpha and trigger the animation
    LaunchedEffect(Unit) {
        startAlpha = endAlpha // Transition to the final alpha value
    }

    // Animate the alpha value
    val alpha by animateFloatAsState(
        targetValue = startAlpha,
        animationSpec = tween(durationMillis = durationMillis),
        finishedListener = {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(context, /*OnBoardingScreen*/TopicSelection::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish() // Optional: finish the current activity so the user can't go back to it
            }, 1000)
        }
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primary
            )
    ) {
        val (logo) = createRefs()

        Box(
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        ) {
            // Apply the animated opacity
            Logo(modifier = Modifier
                .graphicsLayer(alpha = alpha)
                .scale((alpha / 2 + 0.5).toFloat()))
        }
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.quizini_logo_white),
            contentDescription = "logo image",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.size(50.dp))
        Text(
            text = "Quizini",
            color = MaterialTheme.colorScheme.background,
            fontSize = 50.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Start your Own Quiz",
            color = MaterialTheme.colorScheme.background,
            fontSize = 25.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.size(50.dp))
        LoadingAnimation()
    }
}