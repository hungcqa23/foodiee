package com.example.foodiee.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodiee.ui.screens.HomeScreen
import com.example.foodiee.ui.screens.client.AddItemScreen
import com.example.foodiee.ui.screens.client.CartScreen

fun NavGraphBuilder.clientNavGraph(navController: NavController) {
    composable("addItemScreen") {
        AddItemScreen(navController)
    }
    composable(Routes.CartScreen.route) { backStackEntry ->
        val cartId = backStackEntry.arguments?.getString("cartId") ?: ""
        CartScreen(navController, cartId)
    }
    composable(Routes.HomeScreen.route) {
        HomeScreen(navController)
    }
}