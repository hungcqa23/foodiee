package com.example.foodiee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun ProfileScreen() {
    Column {
        Text(
            text = "Profile",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 56.dp, bottom = 28.dp), // Add space between text and image
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.05.sp
        )

        ProfileImage(
            imageUrl = null,
            modifier = Modifier
                .size(80.dp) // Set size for the profile picture
                .align(Alignment.CenterHorizontally)
        )
    }
//    Footer()
}

@Composable
fun ProfileImage(imageUrl: String?, modifier: Modifier = Modifier) {
    if (!imageUrl.isNullOrEmpty()) {
        // Load the image using Coil
        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile Image",
        )
    } else {
        Box(
            modifier = modifier
                .background(Color(0xFFcbd5e1), shape = CircleShape)
        ) {
        }
    }
}
