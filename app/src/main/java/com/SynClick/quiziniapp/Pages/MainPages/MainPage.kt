package com.SynClick.quiziniapp.Pages.MainPages

import QuizPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.SynClick.quiziniapp.Data.Data
import com.SynClick.quiziniapp.R
import com.SynClick.quiziniapp.ui.theme.QuiziniAppTheme

class MainPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuiziniAppTheme {
                MainPageContent()
            }
        }
    }
}


@Composable
fun MainPageContent() {
    var selectedItem by remember { mutableStateOf("Home") }
    var isMenuSelected by remember { mutableStateOf(false) }
    val FragmentWidth by animateFloatAsState(
        targetValue = if (!isMenuSelected) 0f else 0.55f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
    )
    val menuRotationY by animateFloatAsState(
        targetValue = if (!isMenuSelected) 90f else 0f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary)){
        Menu(selectedItem, { selectedItem = it; isMenuSelected = false }, menuRotationY)

        MainFragment(selectedItem, isMenuSelected,{ selectedItem = it},{ isMenuSelected = it },FragmentWidth)

    }
}


@Composable
fun MainFragment(selectedItem: String, isMenuSelected: Boolean,setSelecteditem: (String) -> Unit, onMenuToggle: (Boolean) -> Unit,FragmentWidth:Float) {
    val (topMenuVisibility, setTopMenuVisibility) = remember { mutableStateOf(true) }

    setTopMenuVisibility(true)
    Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.fillMaxWidth(FragmentWidth))
        Card(modifier = Modifier
            .fillMaxHeight(1f - FragmentWidth * 0.5f)
            .fillMaxWidth()
            .fillMaxHeight(),
            shape = getLeftRoundedShape((FragmentWidth*100).toInt()),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            Scaffold(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
                topBar = {
                    if(topMenuVisibility)
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,) {
                            val tintColor = if (isMenuSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground

                            Image(
                                painter = painterResource(id = R.drawable.hamburger),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50 * (1 - FragmentWidth).dp)
                                    .clickable(onClick = {
                                        onMenuToggle(!isMenuSelected)
                                        println(selectedItem)
                                    }),
                                alignment = Alignment.CenterStart,
                                colorFilter = ColorFilter.tint(tintColor)


                            )
                            Text(
                                text = "Welcome ${Data.user?.name}",
                                fontSize = (25*(1-FragmentWidth)).sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )


                        } else Spacer(modifier =Modifier.height(0.dp))
                },
                content = { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        when (selectedItem) {
                            "Home" -> {

                                HomePage(1 - FragmentWidth,setSelecteditem)
                            }
                            "quiz" -> {
                                QuizPage(FragmentWidth =1-FragmentWidth, setTopMenuVisibility = { it:Boolean-> setTopMenuVisibility(it) })
                            }
                            "Chat Bot" -> {
                                Text("Chat Bot", modifier = Modifier.padding(paddingValues))
                            }
                            "news" -> {
                                Text("News", modifier = Modifier.padding(paddingValues))
                            }
                            "Close" -> {
                                Button(onClick = { onMenuToggle(false) }, modifier = Modifier.padding(paddingValues)) {
                                    Text("Close")
                                }
                            }
                        }
                    }
                }
            )

        }
    }
}

@Composable
fun Menu(selectedItem: String, onItemSelected: (String) -> Unit, menuRotationY: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
            .padding(start = 10.dp)
            .graphicsLayer {
                rotationY = menuRotationY
                cameraDistance = 8 * density
                transformOrigin = TransformOrigin(0f, 0.5f)

            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Data.user?.name?.let {
            Text(text = "Welcome $it",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 25.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Column {
            MenuItem("Home", selectedItem, onItemSelected)
            MenuItem("quiz", selectedItem, onItemSelected)
            MenuItem("Chat Bot", selectedItem, onItemSelected)
            MenuItem("news", selectedItem, onItemSelected)
        }
        Spacer(modifier = Modifier.height(50.dp))
        MenuItem("Close", selectedItem, onItemSelected)
    }
}
@Composable
fun MenuItem(item: String, selectedItem: String, onItemSelected: (String) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor =   (if (item == selectedItem) MaterialTheme.colorScheme.background else Color.Transparent),

            )
    ) {

        Row(modifier = Modifier
            .padding(16.dp)
            .clickable { onItemSelected(item) },
            verticalAlignment = Alignment.CenterVertically

        ) {
            val imageRes = when (item) {
                "news" -> R.drawable.news
                "Home" -> R.drawable.home
                "quiz" -> R.drawable.quiz
                "Chat Bot" -> R.drawable.chatbot
                "Close" -> R.drawable.close
                else -> R.drawable.js
            }
            val tintColor = if (item == selectedItem) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                alignment = Alignment.CenterStart,
                colorFilter = ColorFilter.tint(tintColor)


            )

            Spacer(modifier =Modifier.size(30.dp))
            Text(
                text = item,
                color = if (item == selectedItem) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary,
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

        }
    }

}


fun getLeftRoundedShape(cornerRadius: Int): Shape {
    return RoundedCornerShape(
        topStart = cornerRadius.dp,
        bottomStart = cornerRadius.dp
    )
}