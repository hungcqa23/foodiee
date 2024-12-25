package com.example.foodiee.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.Role
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel

@Composable
fun FoodieeeNavHost(navController: NavHostController, userViewModel: UserViewModel, courseViewModel: CourseViewModel, userAPIViewmodel: UserAPIViewModel) {
    val isLoggedIn by userViewModel.isLoggedIn.observeAsState()
    val startDestination = if(isLoggedIn == true){
       when(userViewModel.userRole.observeAsState().value){
           Role.USER -> Routes.HomeScreen.route
           Role.STAFF -> Routes.OrdersManagementScreen.route
           Role.ADMIN -> Routes.OrdersManagementScreen.route
              else -> Routes.HomeScreen.route
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
        sharedNavGraph(navController, userViewModel, courseViewModel, userAPIViewmodel)

        // Admin Route
        adminNavGraph(navController, userViewModel,courseViewModel, userAPIViewmodel )

        // Client Route
        clientNavGraph(navController, userViewModel, courseViewModel, userAPIViewmodel)
    }
}