package com.example.foodiee.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodiee.data.models.CourseDetails
import com.example.foodiee.ui.screens.DetailedCourseScreen
import com.example.foodiee.ui.screens.EditProfileScreen
import com.example.foodiee.ui.screens.OrderDetailScreen
import com.example.foodiee.ui.screens.PersonalInformationScreen
import com.example.foodiee.ui.screens.ProfileScreen

fun NavGraphBuilder.sharedNavGraph(
    navController: NavController,
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

    composable(Routes.DetailedCourseScreen.route) {backStackEntry ->
        val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
        DetailedCourseScreen(navController, mockCourse)
    }
    composable(Routes.OrderDetailScreen.route) { backStackEntry ->
        val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
        OrderDetailScreen(navController, orderId)
    }
    composable(Routes.ProfileScreen.route) {
        ProfileScreen(navController)
    }
    composable(Routes.EditProfileScreen.route) {
        EditProfileScreen(navController)
    }
    composable(Routes.PersonalInformationScreen.route) {
        PersonalInformationScreen(navController)
    }
}