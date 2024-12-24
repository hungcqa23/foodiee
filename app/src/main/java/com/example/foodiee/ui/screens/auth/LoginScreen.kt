package com.example.foodiee.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.Navigation.Routes
import com.example.foodiee.data.models.Role
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel, userAPIViewmodel: UserAPIViewModel) {
    // State to store user input
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val isLoggedIn by userAPIViewmodel.isLoggedIn.observeAsState()
    Log.d("login", isLoggedIn.toString())

    fun login(){
        userAPIViewmodel.loginUser(username, password)
        if(userAPIViewmodel.isLoggedIn.value == true){
            userAPIViewmodel.getToken()?.let { userViewModel.login(Role.ADMIN, it) }
            navController.navigate(Routes.HomeScreen.route)
        }else if(userAPIViewmodel.isLoggedIn.value == false){
            errorMessage = "Invalid username or password"
        } else{
            errorMessage = "Something went wrong"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Username",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            textStyle = TextStyle(
                fontSize = 16.sp,
            ),
            placeholder = {
                Text(text = "Enter your username...")
            }
        )

        Text(
            text = "Password",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            textStyle = TextStyle(
                fontSize = 16.sp,
            ),
            placeholder = {
                Text(text = "Enter your password...")
            }
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = colorScheme.error
            )
        } else {
            Spacer(modifier = Modifier.height(20.dp))
        }

        Button(
            onClick = { login() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary
            )
        ) {
            Text(
                "Sign In",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp), // Add some padding from the button
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don’t have an account? ",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Create one",
                modifier = Modifier.clickable {
                    navController.navigate(Routes.SignUpScreen.route)
                },
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}