package com.example.foodiee.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.Role
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel

@Composable
fun FoodieeeNavHost(navController: NavHostController, userViewModel: UserViewModel, courseViewModel: CourseViewModel, userAPIViewmodel: UserAPIViewModel) {
    val startDestination = if(userViewModel.getUserStatus().isLoggedIn){
       when(userViewModel.getUserStatus().role){
           Role.USER -> Routes.HomeScreen.route
           Role.STAFF -> Routes.OrdersManagementScreen.route
           Role.ADMIN -> Routes.OrdersManagementScreen.route
       }
    }else{
        Routes.LoginScreen.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Authentication Flow
        authNavGraph(navController, userViewModel, userAPIViewmodel)

        // Shared Route (accessible by both Admin and Client)
        sharedNavGraph(navController, userViewModel, courseViewModel)

        // Admin Route
        adminNavGraph(navController, userViewModel, courseViewModel)

        // Client Route
        clientNavGraph(navController, userViewModel, courseViewModel)
    }
}