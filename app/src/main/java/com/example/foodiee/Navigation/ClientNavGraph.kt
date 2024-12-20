package com.example.foodiee.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.screens.DishDescriptionScreen
import com.example.foodiee.ui.screens.HomeScreen
import com.example.foodiee.ui.screens.client.AddItemScreen
import com.example.foodiee.ui.screens.client.CartScreen

fun NavGraphBuilder.clientNavGraph(navController: NavController, userViewModel: UserViewModel, courseViewModel: CourseViewModel) {
    composable(Routes.CartScreen.route) { backStackEntry ->
        val cartId = backStackEntry.arguments?.getString("cartId") ?: ""
        CartScreen(navController, userViewModel, cartId)
    }
    composable(Routes.HomeScreen.route) {
        HomeScreen(navController, userViewModel, courseViewModel)
    }

}