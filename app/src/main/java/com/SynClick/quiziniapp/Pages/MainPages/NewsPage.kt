package com.SynClick.quiziniapp.Pages.MainPages

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.Pager
import coil.compose.AsyncImage
import com.SynClick.quiziniapp.Data.DAOs.serverSevices.Services
import com.SynClick.quiziniapp.Data.Data
import com.SynClick.quiziniapp.Data.Models.Post
import com.SynClick.quiziniapp.Data.Models.RequestsModel.CorrectQuestionnaireRequest
import com.SynClick.quiziniapp.Data.Models.RequestsModel.getNewPostsRequest
import com.SynClick.quiziniapp.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable fun NewsPage(FragmentWidth:Float) {
    val (news,setNews) = remember { mutableStateOf(listOf<Post>()) }
    val (loading,setLoading) = remember { mutableStateOf(true) }
    val (NoMorePosts,setNoMorePosts) = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val gson = Gson()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val call = Services.getPostService()
                    .getNewPosts(
                        "Bearer " + Data.token,
                        getNewPostsRequest(
                            news.map { it.id }.toMutableList(),
                            5

                        )
                    )
                val response = withContext(Dispatchers.IO) { call.execute() }
                if (response.isSuccessful) {
                    val body= response.body()
                    println("news body: "+gson.toJson(body))
                    if (body != null) {
                        if (body!=null) {
                            setNews(body.posts)
                            //news[0].mediaUrls.add("http://res.cloudinary.com/dfvbhzskm/video/upload/v1731800583/quizini/postImages/OTHER/18/quizini/postImages/OTHER/18/videoplayback_2024-11-17-00:42:50:309.mp4")
                            setLoading(false)
                            if(news.size>0){
                                setNoMorePosts(false)
                            }
                            else{
                                setNoMorePosts(true)
                            }
                        }
                        else {
                            setNoMorePosts(true)
                        }
                    }

                }
                else {
                    // Error
                    println("error: "+response.errorBody()?.string())
                }
            } catch (e: Exception) {
                // Error
                println(e)
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        println("news: "+news.size)
        items(news.size) { index ->
            PostView(news[index],FragmentWidth)
        }
    }
}


@Composable
fun PostView(post:Post,FragmentWidth:Float) {
    val (numberofUpVotes,setNumberOfUpVotes) = remember { mutableStateOf(post.upVoteNumber) }
    val (numberofDownVotes,setNumberOfDownVotes) = remember { mutableStateOf(post.downVoteNumber) }
    val (isUpVoted,setIsUpVoted) = remember { mutableStateOf(post.isUpVoted) }
    val (isDownVoted,setIsDownVoted) = remember { mutableStateOf(post.isDownVoted) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding((20 * FragmentWidth).dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape((30 * FragmentWidth).dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        ),
        shape = RoundedCornerShape((30*FragmentWidth).dp)

    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding((15 * FragmentWidth).dp)
        ){
            Text(text = post.title,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = (20*FragmentWidth).sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
                )
            Row() {
                Text(text = "By "+post.author,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = (13*FragmentWidth).sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                NewsType(post.type,FragmentWidth)
            }
            Text(text = post.content,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = (15*FragmentWidth).sp,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height((10*FragmentWidth).dp))
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
               post.tags.map {
                     Card(
                          modifier = Modifier
                              .padding((2 * FragmentWidth).dp)
                              .border(
                                  width = 2.dp,
                                  color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                  shape = RoundedCornerShape((9 * FragmentWidth).dp)
                              ),
                          colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0f),
                          ),
                          shape = RoundedCornerShape((10*FragmentWidth).dp)
                     ) {
                         Column(
                             modifier = Modifier.padding(horizontal = (5*FragmentWidth).dp)
                         ){
                             Text(text = "#"+it.replace(" ","_"),
                                 fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                 fontSize = (12*FragmentWidth).sp,
                                 color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                 maxLines = 1,
                                 overflow = TextOverflow.Ellipsis
                             )

                         }
                     }
               }

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val zonedDateTime = ZonedDateTime.parse(post.createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                val now = ZonedDateTime.now()
                val duration = Duration.between(zonedDateTime,now)
                if(duration.toHours()>6){
                    Text(text = zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                        fontSize = (17*FragmentWidth).sp,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                else{
                    if(duration.toHours()<1){
                        Text(text = duration.toHours().toString()+" minutes ago",
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                            fontSize = (17*FragmentWidth).sp,
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    else{
                        Text(text = duration.toHours().toString()+" hours ago",
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                            fontSize = (17*FragmentWidth).sp,
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

            }
            else {
                Text(text = post.createdAt.removeRange(10,post.createdAt.length-1),
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                    fontSize = (17*FragmentWidth).sp,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if(post.mediaUrls.size>0)
            ImageVideoLinkSlider(post.mediaUrls, context = LocalContext.current)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                    ),
                    modifier = Modifier.padding(top = (10 * FragmentWidth).dp)


                ) {
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding((5 * FragmentWidth).dp)
                    ){
                        Row(

                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.clickable(onClick = {
                                post.isUpVoted = !post.isUpVoted
                                setIsUpVoted(!isUpVoted)
                                if(post.isUpVoted){
                                    post.upVoteNumber+=1
                                    setNumberOfUpVotes(numberofUpVotes+1)
                                    if(post.isDownVoted){
                                        post.downVoteNumber-=1
                                        setNumberOfDownVotes(numberofDownVotes-1)
                                        post.isDownVoted = false
                                        setIsDownVoted(false)

                                    }
                                }
                                else{
                                    post.upVoteNumber-=1
                                    setNumberOfUpVotes(numberofUpVotes-1)
                                }
                            })
                        ){
                            Image(
                                modifier = Modifier
                                    .padding((5 * FragmentWidth).dp)
                                    .width((30 * FragmentWidth).dp)
                                    .aspectRatio(1f),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                                painter = painterResource(id = if(isUpVoted)R.drawable.upvote_filled else R.drawable.upvote),
                                contentDescription = "UpVote",

                                )
                            Text(text = numberofUpVotes.toString(),
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                fontSize = (20*FragmentWidth).sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.width((20*FragmentWidth).dp))
                        Text(text = "|",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontSize = (30*FragmentWidth).sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width((10*FragmentWidth).dp))
                        Row(

                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.clickable(onClick = {
                                post.isDownVoted = !post.isDownVoted
                                setIsDownVoted(!isDownVoted)
                                if(post.isDownVoted){
                                    post.downVoteNumber+=1
                                    setNumberOfDownVotes(numberofDownVotes+1)
                                    if(post.isUpVoted){
                                        post.upVoteNumber-=1
                                        setNumberOfUpVotes(numberofUpVotes-1)
                                        post.isUpVoted = false
                                        setIsUpVoted(false)
                                    }
                                }
                                else{
                                    post.downVoteNumber-=1
                                    setNumberOfDownVotes(numberofDownVotes-1)
                                }
                            })
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding((5 * FragmentWidth).dp)
                                    .width((30 * FragmentWidth).dp)
                                    .aspectRatio(1f),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                                painter = painterResource(id = if (isDownVoted) R.drawable.downvote_filled else R.drawable.downvote),
                                contentDescription = "UpVote",

                                )
                            if (numberofDownVotes < 1000)
                                Text(
                                    text = numberofDownVotes.toString(),
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    fontSize = (20 * FragmentWidth).sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            else {
                                Text(
                                    text = (numberofDownVotes / 1000).toString() + "k",
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    fontSize = (20 * FragmentWidth).sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                    }
                }

                Text(text ="Views: "+post.seenNumber.toString(),
                    modifier = Modifier.padding((5 * FragmentWidth).dp),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = (15*FragmentWidth).sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )




            }



        }

    }


}

@Composable
fun NewsType(Type:String,FragmentWidth:Float) {
    /*
        NEWS,
    TUTORIAL,
    ACHIEVEMENT,
    EVENT,
    OTHER,
    TECHNICAL,
     */
    val color = when(Type){
        "NEWS" -> MaterialTheme.colorScheme.primary
        "TUTORIAL" -> MaterialTheme.colorScheme.secondary
        "ACHIEVEMENT" -> MaterialTheme.colorScheme.tertiary
        "EVENT" -> MaterialTheme.colorScheme.primary
        "OTHER" -> MaterialTheme.colorScheme.secondary
        "TECHNICAL" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.tertiary
    }
    Card(
        modifier = Modifier
            .padding(start = (10 * FragmentWidth).dp)
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape((10 * FragmentWidth).dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
        ),
        shape = RoundedCornerShape((10*FragmentWidth).dp)

    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = (8*FragmentWidth).dp)
        ) {
            Text(text = Type,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = (12*FragmentWidth).sp,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }


    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageVideoLinkSlider(
    items: List<String>, // A list of URLs (image, video, or link)
    modifier: Modifier = Modifier,
    context: android.content.Context
) {
    val pagerState = rememberPagerState(
        pageCount = items.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    HorizontalPager(
        state = pagerState,
    ) { page ->
        val item = items[page]
        when {
            item.endsWith(".jpg") || item.endsWith(".png") || item.endsWith(".jpeg") -> {
                // If the URL is an image, use AsyncImage to load it
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(20.dp))
                ){
                    AsyncImage(
                        model = item,
                        contentDescription = "Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop

                    )
                }

            }
            item.endsWith(".mp4") -> {
                // If the URL is a video, use VideoView to stream it
                AndroidView(
                    factory = { context ->
                        VideoView(context).apply {
                            setVideoURI(Uri.parse(item))
                            start()
                        }
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            else -> {
                // If it's a link, show it as clickable text
                Text(
                    text = item,
                    modifier = Modifier.clickable {
                        val context = context
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

fun determineMediaType(url: String): String {
    // Extract the file extension
    val extension = url.substringAfterLast('.', "").lowercase()

    // Define photo and video extensions
    val photoExtensions = listOf("jpg", "jpeg", "png", "gif", "bmp", "webp", "svg")
    val videoExtensions = listOf("mp4", "mov", "avi", "mkv", "flv", "wmv", "webm", "3gp")

    return when {
        extension in photoExtensions -> "Photo"
        extension in videoExtensions -> "Video"
        else -> "Unknown"
    }
}