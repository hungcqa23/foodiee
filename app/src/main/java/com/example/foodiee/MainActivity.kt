package com.example.foodiee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.foodiee.data.models.CourseDetails
import com.example.foodiee.ui.screens.*
import com.example.foodiee.ui.screens.admin.OrdersManagementScreen
import com.example.foodiee.ui.theme.FoodieeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodieeeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val mockCourseDetails = listOf(
                        CourseDetails(
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
                    )

                    NavHost(
                        navController = navController,
                        startDestination = "addItemScreen"
                    ) {
                        // Auth screens
                        composable("roleSelectionScreen") {
                            RoleSelectionScreen(navController)
                        }

                        composable("signUpScreen") {
                            SignUpScreen(navController)
                        }

                        composable("loginScreen") {
                            LoginScreen(navController)
                        }

                        // Main screens
                        composable("homeScreen") {
                            HomeScreen(navController)
                        }

                        composable("detailedCourseScreen") {
                            DetailedCourseScreen(navController, mockCourseDetails[0])
                        }

                        composable("profileScreen") {
                            ProfileScreen(navController)
                        }

                        composable("editProfileScreen") {
                            EditProfileScreen(navController)
                        }

                        // Admin screens
                        composable("ordersManagementScreen") {
                            OrdersManagementScreen(navController)
                        }

                        composable("orderDetail/{orderId}") {
                            val orderId = it.arguments?.getString("orderId") ?: ""
                            OrderDetailScreen(navController, orderId)
                        }

                        composable("addItemScreen") {
                            AddItemScreen(navController)
                        }

                        composable("inventoryScreen") {
                            InventoryScreen(navController)
                        }

                        composable("statisticsScreen") {
                            StatisticsScreen(navController)
                        }

                        composable("CartScreen/{cartId}") {
                            val cartId = it.arguments?.getString("cartId") ?: ""
                            CartScreen(navController, cartId)
                        }
                    }
                }
            }
        }
    }
}
