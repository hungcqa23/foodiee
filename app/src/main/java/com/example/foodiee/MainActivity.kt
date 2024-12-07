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

                        ),
                        CourseDetails(
                            title = "Chicken Tikka Masala",
                            location = "Delhi, India",
                            price = "$12.99",
                            rating = 4.7,
                            description = "A creamy and flavorful dish made with marinated grilled chicken in a spiced curry sauce.",
                            ingredients = listOf(
                                "Chicken",
                                "Yogurt",
                                "Tomato Paste",
                                "Garlic",
                                "Garam Masala",
                                "Cream"
                            ),
                            mealType = "Main Course"
                        ),
                        CourseDetails(
                            title = "Sushi Platter",
                            location = "Tokyo, Japan",
                            price = "$25.00",
                            rating = 4.9,
                            description = "An assortment of fresh sushi rolls and nigiri made with premium ingredients.",
                            ingredients = listOf(
                                "Sushi Rice",
                                "Nori",
                                "Tuna",
                                "Salmon",
                                "Avocado",
                                "Soy Sauce",
                                "Wasabi"
                            ),
                            mealType = "Appetizer"
                        ),
                        CourseDetails(
                            title = "Classic Cheeseburger",
                            location = "New York, USA",
                            price = "$10.99",
                            rating = 4.6,
                            description = "A juicy beef patty topped with cheese, lettuce, tomato, and pickles on a toasted bun.",
                            ingredients = listOf(
                                "Beef Patty",
                                "Cheddar Cheese",
                                "Lettuce",
                                "Tomato",
                                "Pickles",
                                "Bun"
                            ),
                            mealType = "Main Course"
                        ),
                        CourseDetails(
                            title = "Caesar Salad",
                            location = "San Diego, USA",
                            price = "$8.50",
                            rating = 4.4,
                            description = "A fresh salad made with romaine lettuce, croutons, parmesan cheese, and Caesar dressing.",
                            ingredients = listOf(
                                "Romaine Lettuce",
                                "Croutons",
                                "Parmesan Cheese",
                                "Caesar Dressing",
                                "Lemon"
                            ),
                            mealType = "Appetizer"
                        )
                    )

                    NavHost(
                        navController = navController,
                        startDestination = "statisticsScreen"
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

                    }
                }
            }
        }
    }
}
