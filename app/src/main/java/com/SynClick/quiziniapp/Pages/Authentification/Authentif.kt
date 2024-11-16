package com.SynClick.quiziniapp.Pages.Authentification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.SynClick.quiziniapp.Data.DAOs.TopicDAO
import com.SynClick.quiziniapp.Data.DAOs.UserDAO
import com.SynClick.quiziniapp.Data.Models.userEntityDto
import com.SynClick.quiziniapp.Pages.Topic.TopicSelection
import com.SynClick.quiziniapp.ui.theme.QuiziniAppTheme
import kotlinx.coroutines.launch

class Authentif : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuiziniAppTheme {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularBackground(modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.7f)

                        )
                    Authentification(
                        modifier = Modifier

                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(0.7f)
                    )
                }
            }
        }
    }
}
@SuppressLint("Range")
@Composable
fun CircularBackground(modifier: Modifier = Modifier) {

    var halfHeight by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxSize() // Fill the whole screen
            .background(MaterialTheme.colorScheme.primary) // Background color of the screen
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Size of the box (width and height are the same)
                .aspectRatio(1f) // Aspect ratio of the box
                .onGloballyPositioned { coordinates ->
                    val height = coordinates.size.height // Get the height of the box
                    halfHeight = 700 / 2f // Calculate half the height and update the state
                }
        )

    }
}
    // Remember to hold the value of the corner radius dynamically

var topicDao=TopicDAO()

@Composable
fun Authentification(modifier: Modifier = Modifier) {
    var SignUp by remember { mutableStateOf(false) }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActionSelector(SignUp) { isSignInSelected ->
                SignUp = isSignInSelected
            }
            if (!SignUp) {
                SignInFragment()
            } else {
                SignUpFragment()
            }
        }
    }
}

@Composable
fun ActionSelector(SignUp: Boolean, onSignInToggle: (Boolean) -> Unit) {
    val widthFactor1 by animateFloatAsState(
        targetValue = if (SignUp) 0.5f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    val widthFactor2 by animateFloatAsState(
        targetValue = if (SignUp) 0f else 0.5f,
        animationSpec = tween(durationMillis = 500)
    )

    Card(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(20.dp))
            .fillMaxWidth(0.7f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Box {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(widthFactor1)
                        .height(50.dp)
                        .clickable { onSignInToggle(false) }
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth(widthFactor1 + 0.5f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) { }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(widthFactor2)
                        .height(50.dp)
                        .clickable { onSignInToggle(true) }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (SignUp) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
                    text = "Log in"
                )
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (!SignUp) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
                    text = "Sign up"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthentificationPreview() {
    QuiziniAppTheme {
        Authentification()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInFragment() {

    val context = LocalContext.current
    val errorMessage = remember { mutableStateOf("") }
    // Remember a mutable state for the text field's value
    val Email = remember { mutableStateOf(TextFieldValue("")) }
    val isEmailValid = remember { mutableStateOf(true) }


    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            shape = RoundedCornerShape(15.dp),
            value = Email.value,
            isError = !isEmailValid.value,
            onValueChange = { Email.value = it ;isEmailValid.value=checkEmail(Email.value.text)},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,       // Color when focused
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,     // Color when unfocused
                disabledBorderColor =MaterialTheme.colorScheme.primary, // Color when disabled
                errorBorderColor =MaterialTheme.colorScheme.secondary           // Color when there's an error
            ),
            label = { Text("Enter your e-mail address", color = MaterialTheme.colorScheme.onBackground) }, // Label similar to TextInputLayout
            modifier = Modifier.fillMaxWidth()
        )
    }
    val password = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            shape = RoundedCornerShape(15.dp),
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Enter your password", color = MaterialTheme.colorScheme.onBackground) }, // Label similar to TextInputLayout
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,       // Color when focused
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,     // Color when unfocused
                disabledBorderColor = MaterialTheme.colorScheme.primary,      // Color when disabled
                errorBorderColor = MaterialTheme.colorScheme.secondary        // Color when there's an error
            ),
            visualTransformation = PasswordVisualTransformation(),            // Mask the input
            modifier = Modifier.fillMaxWidth()
        )
    }

    val checkedState = remember { mutableStateOf(false) }
    Text(text =errorMessage.value,modifier = Modifier.padding(16.dp),color = MaterialTheme.colorScheme.secondary)
    Row(
        modifier = Modifier
            .padding(end = 30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End // Align elements at the end of the row
    ) {
        // The checkbox
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )
        // The text next to the checkbox
        Text(text = "Stay Registered?")
    }

    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            coroutineScope.launch {
                val resultat = UserDAO().login(
                    userEntityDto(
                        Email.value.text,
                        password.value.text
                    )
                )
                if (resultat.first) {
                        val intent = Intent(context, /*OnBoardingScreen*/TopicSelection::class.java)
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish() // Optional: finish the current activity so the user can't go back to it

                } else{
                    errorMessage.value = resultat.second
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Log In", color = MaterialTheme.colorScheme.surface)
    }

}

fun checkEmail(text: String): Boolean {
    if (text.contains("@") && text.contains(".")) {
        println("Email is valid")
        return true;
    } else {
        println("Email is not valid")
        return false;
    }
}

@Composable
fun SignUpFragment() {
    // Remember a mutable state for the text field's value
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text("Enter your password", color = MaterialTheme.colorScheme.onBackground) }, // Label similar to TextInputLayout
            modifier = Modifier.fillMaxWidth()
        )
    }
}
