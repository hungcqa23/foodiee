package com.example.foodiee.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.foodiee.R
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        bottomBar = { Footer(navController = navController) }
    ) { paddingValues: PaddingValues -> // Use paddingValues here
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 28.dp
                )
                .padding(paddingValues) // Apply paddingValues here
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Text(
                    text = "Profile",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 56.dp, bottom = 24.dp),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.05.sp
                )

                ProfileImage(
                    imageUrl = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "John Doe",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.05.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "My Center",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FoodieeeColors.slate900
                )

                listOf(
                    "Personal Information",
                    "Order History",
                    "Payment Methods"
                ).forEach { label ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { /* TODO: Action */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FoodieeeColors.slate200,
                                contentColor = FoodieeeColors.slate900
                            ),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.circle_user_round),
                                    contentDescription = null
                                )
                                Text(
                                    text = label,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { navController.navigate("roleSelectionScreen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, FoodieeeColors.slate300),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = "Log out",
                            fontSize = 16.sp,
                            color = FoodieeeColors.red500,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProfileImage(imageUrl: String?, modifier: Modifier = Modifier) {
    if (!imageUrl.isNullOrEmpty()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile Image",
            modifier = modifier
        )
    } else {
        Box(
            modifier = modifier
                .background(FoodieeeColors.slate200, shape = CircleShape)
        )
    }
}
