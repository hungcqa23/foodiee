package com.example.foodiee.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.screens.auth.LoginScreen
import com.example.foodiee.ui.screens.auth.RoleSelectionScreen
import com.example.foodiee.ui.screens.auth.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavController, userAPIViewModel: UserAPIViewModel, userAPIViewModel: UserAPIViewModel) {
    composable(Routes.RoleSelectionScreen.route) {
        RoleSelectionScreen(navController, userViewModel)
    }
    composable(Routes.SignUpScreen.route) {
        SignUpScreen(navController, userViewModel, userAPIViewModel)
    }
    composable(Routes.LoginScreen.route) {
        LoginScreen(navController, userViewModel, userAPIViewModel)
    }
}