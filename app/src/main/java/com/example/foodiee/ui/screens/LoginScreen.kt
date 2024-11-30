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
import androidx.compose.ui.unit.sp
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
            textStyle = TextStyle(
                fontSize = 16.sp,
            ),
            placeholder = {
                Text(text = "Enter your username...")
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
            onClick = { handleLogin() },
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
                    navController.navigate("homeScreen")
                },
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}