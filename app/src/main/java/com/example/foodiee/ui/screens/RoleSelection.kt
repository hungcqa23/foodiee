package com.example.foodiee.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun RoleSelectionScreen(navController: NavController) {
    val primaryColor = MaterialTheme.colorScheme.primary

    // Remember the border color state for each button
    var guestBorderColor by remember { mutableStateOf(FoodieeeColors.slate300) }
    var loginBorderColor by remember { mutableStateOf(FoodieeeColors.slate300) }
    var isButtonEnabled by remember { mutableStateOf(false) }

    // Function to handle button clicks
    fun onButtonClick(isGuestButton: Boolean) {
        if (isGuestButton) {
            guestBorderColor = primaryColor
            loginBorderColor = FoodieeeColors.slate300
        } else {
            guestBorderColor = FoodieeeColors.slate300
            loginBorderColor = primaryColor
        }

        isButtonEnabled = true
    }

    // Function for the Confirm Button's click action
    fun onConfirmButtonClick() {
        when {
            guestBorderColor == primaryColor -> navController.navigate("guestScreen")
            loginBorderColor == primaryColor -> navController.navigate("loginScreen")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Header Texts
            Text(
                text = "Choose Your Role",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = FoodieeeColors.slate900,
                letterSpacing = 0.2.sp
            )
            Spacer(modifier = Modifier.height(4.dp)) // Spacing between header texts
            Text(
                text = "Select how you want to proceed",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = FoodieeeColors.slate500
            )
            Spacer(modifier = Modifier.height(24.dp)) // Spacing before buttons

            // Buttons Container
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp), // Space between buttons
            ) {
                // Guest Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .border(
                            width = 2.dp,
                            color = guestBorderColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(2.dp)
                ) {
                    // Guest Button
                    Button(
                        onClick = { onButtonClick(isGuestButton = true) },
                        modifier = Modifier
                            .fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp) // Same shape for consistency
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Continue as Guest",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = FoodieeeColors.slate900
                            )
                            Spacer(modifier = Modifier.height(4.dp)) // Spacing between texts
                            Text(
                                text = "Continue without creating an account",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = FoodieeeColors.slate500
                            )
                        }
                    }
                }

                // Login Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(84.dp)
                        .border(
                            width = 2.dp, // Border thickness
                            color = loginBorderColor, // Border color
                            shape = RoundedCornerShape(8.dp) // Shape of the border
                        )
                        .padding(2.dp) // Padding to avoid overlap between border and button
                ) {
                    // Login Button
                    Button(
                        onClick = { onButtonClick(isGuestButton = false) },
                        modifier = Modifier
                            .fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp) // Same shape for consistency
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Login with Account",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = FoodieeeColors.slate900
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Access your personal account & information",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = FoodieeeColors.slate500,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Continue Button
                Button(
                    onClick = { onConfirmButtonClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isButtonEnabled) MaterialTheme.colorScheme.primary else FoodieeeColors.slate300
                    ),
                    shape = RoundedCornerShape(4.dp),
                    enabled = isButtonEnabled
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
