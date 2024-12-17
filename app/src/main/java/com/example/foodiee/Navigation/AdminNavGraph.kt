package com.example.foodiee.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodiee.data.models.CustomerType
import com.example.foodiee.ui.screens.admin.EditDishScreen
import com.example.foodiee.ui.screens.admin.InventoryScreen
import com.example.foodiee.ui.screens.admin.OrdersManagementScreen
import com.example.foodiee.ui.screens.admin.PeopleManagementScreen
import com.example.foodiee.ui.screens.admin.StatisticsScreen

fun NavGraphBuilder.adminNavGraph(navController: NavController) {
    composable(Routes.OrdersManagementScreen.route) {
        OrdersManagementScreen(navController)
    }
    composable(Routes.InventoryScreen.route) {
        InventoryScreen(navController)
    }
    composable(Routes.PeopleManagementScreen.route) {
        PeopleManagementScreen(navController, CustomerType.CUSTOMER)
    }
    composable(Routes.StatisticScreen.route) {
        StatisticsScreen(navController)
    }
    composable(Routes.EditDishScreen.route){
        EditDishScreen(navController)
    }
}