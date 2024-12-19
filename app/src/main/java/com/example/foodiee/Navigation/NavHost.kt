package com.example.foodiee.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun FoodieeeNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.route
    ) {
        // Authentication Flow
        authNavGraph(navController)

        // Shared Route (accessible by both Admin and Client)
        sharedNavGraph(navController)

        // Admin Route
        adminNavGraph(navController)

        // Client Route
        clientNavGraph(navController)
    }
}