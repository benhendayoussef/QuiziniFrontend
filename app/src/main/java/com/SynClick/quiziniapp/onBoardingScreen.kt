package com.SynClick.quiziniapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.SynClick.quiziniapp.Data.Models.OnBoardingData
import com.SynClick.quiziniapp.ui.theme.BottomCardShape
import com.SynClick.quiziniapp.ui.theme.QuiziniAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.SynClick.quiziniapp.Pages.Authentification.Authentif
import kotlinx.coroutines.launch

class OnBoardingScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuiziniAppTheme {
                OnBoardingScreenui()
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview(showBackground = true)
fun OnBoardingScreenui() {
    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = false
    systemUiController.isNavigationBarVisible=false

    Surface(modifier= Modifier.fillMaxSize()) {
        var items=ArrayList<OnBoardingData>()
        items.add(OnBoardingData(R.drawable.onboarding1,MaterialTheme.colorScheme.primary,"Ai generated Quizzes","be the first to use Ai to Create Quizzes"))
        items.add(OnBoardingData(R.drawable.onboarding2,MaterialTheme.colorScheme.secondary,"Answer the Quizzes","Answer the Quizzes and get your score"))
        items.add(OnBoardingData(R.drawable.onboarding3,MaterialTheme.colorScheme.tertiary,"get your Results","Get your results and share with your friends"))
        val pagerState = rememberPagerState(
            pageCount = items.size,
            initialOffscreenLimit = 2,
            infiniteLoop = false,
            initialPage = 0
        )
        OnboardingPager(item = items, pagerState = pagerState, modifier = Modifier.fillMaxSize())



    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(item: List<OnBoardingData>, pagerState: PagerState, modifier: Modifier) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(modifier =modifier){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .background(item[pagerState.currentPage].color)){
            HorizontalPager(state = pagerState) { page ->
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ){
                    Image(
                        painter = painterResource(id = item[page].image),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    )}
                }


        }
        Box(modifier =Modifier.align(Alignment.BottomCenter)){
            Card (
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                elevation = CardDefaults.cardElevation(0.dp),
                shape =BottomCardShape.large
            ){
                Box{
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()){
                        PagerIndicator(items = item,currentPage = pagerState.currentPage)
                        Text(text = item[pagerState.currentPage].mainText,
                            modifier= Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, end = 30.dp),
                            color=item[pagerState.currentPage].color,
                            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                            textAlign=TextAlign.Right,
                            fontSize=20.sp,
                            fontWeight=FontWeight.ExtraBold
                        )
                        Text(text = item[pagerState.currentPage].subText,
                            modifier= Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 40.dp, end = 30.dp),
                            color=Color.Gray,
                            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                            textAlign=TextAlign.Center,
                            fontSize=17.sp,
                            fontWeight=FontWeight.Normal
                        )

                    }
                    Box(modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(30.dp)
                    ){
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            if(pagerState.currentPage!=item.size-1){
                                TextButton(onClick = {
                                    val intent = Intent(context, Authentif::class.java)
                                    context.startActivity(intent)
                                    (context as? ComponentActivity)?.finish();
                                    }) {
                                    Text(
                                        text = "Skip Now",
                                        color = Color(0xFF292D32),
                                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Right
                                    )

                                }
                                OutlinedButton(
                                    onClick = {coroutineScope.launch {
                                        scrollBy(pagerState,1 )
                                    }},
                                    border = BorderStroke(
                                        14.dp,
                                        item[pagerState.currentPage].color
                                    ),
                                    shape = RoundedCornerShape(50),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = item[pagerState.currentPage].color
                                    ),
                                    modifier = Modifier.size(65.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.right_arrow),
                                        contentDescription = "",
                                        modifier = Modifier.size(65.dp)
                                    )

                                }
                            }
                            else{
                                Button(
                                    onClick = {
                                        val intent = Intent(context, Authentif::class.java)
                                        context.startActivity(intent)
                                        (context as? ComponentActivity)?.finish();
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = item[pagerState.currentPage].color
                                    ),
                                    contentPadding = PaddingValues(12.dp),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 0.dp
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Get Started", color = Color.White, fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
@Composable
fun PagerIndicator(items:List<OnBoardingData>,currentPage:Int){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top=20.dp)
    ){
        repeat(items.size){
            Indicator(
                isSelected = it==currentPage,
                color=items[currentPage].color
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
suspend fun scrollBy(pagerState: PagerState,i: Int) {
    pagerState.animateScrollToPage(pagerState.currentPage+1)
}

@Composable
fun Indicator(isSelected:Boolean,color: Color){
    val width= animateDpAsState(targetValue = if(isSelected) 40.dp else 10.dp)
    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) color
                else Color.Gray.copy(alpha = 0.5f)
            )
    )
}