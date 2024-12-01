package com.example.foodiee.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodiee.ui.components.Footer

@Composable
fun EditProfileScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            Footer(navController)
        }
    ) { paddingValues: PaddingValues ->
        Text(
            text = "Edit Profile",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Handle scaffold padding
        )
    }
}
