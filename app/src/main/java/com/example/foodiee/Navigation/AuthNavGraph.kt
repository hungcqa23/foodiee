package com.example.foodiee.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodiee.ui.screens.auth.LoginScreen
import com.example.foodiee.ui.screens.auth.RoleSelectionScreen
import com.example.foodiee.ui.screens.auth.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    composable(Routes.RoleSelectionScreen.route) {
        RoleSelectionScreen(navController)
    }
    composable(Routes.SignUpScreen.route) {
        SignUpScreen(navController)
    }
    composable(Routes.LoginScreen.route) {
        LoginScreen(navController)
    }
}