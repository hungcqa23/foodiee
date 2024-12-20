package com.example.foodiee.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.Role
import com.example.foodiee.data.models.User.UserViewModel

@Composable
fun FoodieeeNavHost(navController: NavHostController, userViewModel: UserViewModel, courseViewModel: CourseViewModel) {
    val startDestination = if(userViewModel.getUserStatus().isLoggedIn){
       when(userViewModel.getUserStatus().role){
           Role.CUSTOMER -> Routes.HomeScreen.route
           Role.EMPLOYEE -> Routes.OrdersManagementScreen.route
       }
    }else{
        Routes.LoginScreen.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Authentication Flow
        authNavGraph(navController, userViewModel)

        // Shared Route (accessible by both Admin and Client)
        sharedNavGraph(navController, userViewModel, courseViewModel)

        // Admin Route
        adminNavGraph(navController, userViewModel)

        // Client Route
        clientNavGraph(navController, userViewModel, courseViewModel)
    }
}