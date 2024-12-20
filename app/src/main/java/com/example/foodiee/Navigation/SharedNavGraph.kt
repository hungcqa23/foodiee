package com.example.foodiee.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.CourseDetails
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.screens.DishDescriptionScreen
import com.example.foodiee.ui.screens.EditProfileScreen
import com.example.foodiee.ui.screens.OrderDetailScreen
import com.example.foodiee.ui.screens.PersonalInformationScreen
import com.example.foodiee.ui.screens.ProfileScreen

fun NavGraphBuilder.sharedNavGraph(
    navController: NavController,
    userViewModel: UserViewModel,
    courseViewModel: CourseViewModel
) {

    val mockCourse = CourseDetails(
        title = "Spaghetti Carbonara",
        location = "Rome, Italy",
        price = "$15.99",
        rating = 4.8,
        description = "A classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.",
        ingredients = listOf(
            "Spaghetti",
            "Eggs",
            "Parmesan Cheese",
            "Pancetta",
            "Black Pepper"
        ),
        mealType = "Main Course"

    )
    composable(
        route = Routes.DishDescriptionScreen.route,
        arguments = listOf(navArgument("dishId") { type = NavType.IntType })
    ) { backStackEntry ->
        val courseId = backStackEntry.arguments?.getInt("dishId") ?: 0
        DishDescriptionScreen(navController, userViewModel, courseViewModel, courseId)
    }
    composable(Routes.OrderDetailScreen.route) { backStackEntry ->
        val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
        OrderDetailScreen(navController, userViewModel, orderId)
    }
    composable(Routes.ProfileScreen.route) {
        ProfileScreen(navController, userViewModel)
    }
    composable(Routes.EditProfileScreen.route) {
        EditProfileScreen(navController, userViewModel)
    }
    composable(Routes.PersonalInformationScreen.route) {
        PersonalInformationScreen(navController, userViewModel)
    }
}