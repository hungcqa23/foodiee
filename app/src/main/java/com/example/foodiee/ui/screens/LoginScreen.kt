package com.example.foodiee.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    // State to store user input
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // handle login logic
    fun handleLogin() {
        if (username == "admin" && password == "password") {
            // Navigate to home screen
            navController.navigate("homeScreen")
        } else {
            errorMessage = "Invalid username or password"
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
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
        )
        if (errorMessage.isNotEmpty()) Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error
        )

        Text(
            text = "Password",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            visualTransformation = PasswordVisualTransformation(),
        )
        if (errorMessage.isNotEmpty()) Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Button(
            onClick = { handleLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
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
                text = buildAnnotatedString {
                    append("Don’t have an account? ")
                    pushStringAnnotation(tag = "signUp", annotation = "signUp")
                    withStyle(
                        style = SpanStyle(
                            color = colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append("Create one")
                    }
                    pop()
                },
                modifier = Modifier.clickable {
                    navController.navigate("homeScreen")
                },
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}