package com.example.foodiee.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.screens.admin.EditDishScreen
import com.example.foodiee.ui.screens.admin.OrdersManagementScreen
import com.example.foodiee.ui.screens.admin.PeopleManagementScreen
import com.example.foodiee.ui.screens.admin.StatisticsScreen
import com.example.foodiee.ui.screens.client.AddItemScreen

fun NavGraphBuilder.adminNavGraph(navController: NavController, userViewModel: UserViewModel) {
    composable(Routes.OrdersManagementScreen.route) {
        OrdersManagementScreen(navController, userViewModel)
    }
    composable(Routes.PeopleManagementScreen.route) {
        PeopleManagementScreen(navController, userViewModel)
    }
    composable(Routes.StatisticScreen.route) {
        StatisticsScreen(navController, userViewModel)
    }
    composable(Routes.EditDishScreen.route){
        EditDishScreen(navController, userViewModel)
    }
    composable(Routes.AddItemScreen.route){
        AddItemScreen(navController, userViewModel)
    }
}