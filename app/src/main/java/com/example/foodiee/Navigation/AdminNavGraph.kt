package com.example.foodiee.Navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.screens.admin.EditDishScreen
import com.example.foodiee.ui.screens.admin.OrdersManagementScreen
import com.example.foodiee.ui.screens.admin.PeopleManagementScreen
import com.example.foodiee.ui.screens.admin.StatisticsScreen
import com.example.foodiee.ui.screens.client.AddItemScreen

fun NavGraphBuilder.adminNavGraph(navController: NavController, userViewModel: UserViewModel, courseViewModel: CourseViewModel, userAPIViewModel: UserAPIViewModel) {
    composable(Routes.OrdersManagementScreen.route) {
        OrdersManagementScreen(navController, userViewModel, courseViewModel, userAPIViewModel)
    }
    composable(Routes.PeopleManagementScreen.route) {
        PeopleManagementScreen(navController, userViewModel, userAPIViewModel)
    }
    composable(Routes.StatisticScreen.route) {
        StatisticsScreen(navController, userViewModel, courseViewModel, userAPIViewModel)
    }
    composable(Routes.EditDishScreen.route){
        EditDishScreen(navController, userViewModel,userAPIViewModel, courseViewModel)
    }
    composable(
        route = "addItemScreen", // Base route without arguments
        arguments = emptyList() // No arguments for this route
    ) {
        Log.e("Nav", "going 1")
        AddItemScreen(navController, userViewModel, courseViewModel,userAPIViewModel ,null)
    }

    composable(
        route = "addItemScreen/{dishId}", // Route with a dishId argument
        arguments = listOf(
            navArgument("dishId") {
                type = NavType.StringType
                nullable = true // Allow null values
            }
        )
    ) { backStackEntry ->
        Log.e("Nav", "going 2")

        val dishId = backStackEntry.arguments?.getString("dishId")
        AddItemScreen(navController, userViewModel, courseViewModel,userAPIViewModel ,dishId)
    }
}