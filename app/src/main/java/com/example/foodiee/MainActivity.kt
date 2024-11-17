package com.example.foodiee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.foodiee.ui.screens.*
import com.example.foodiee.ui.theme.FoodieeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodieeeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "roleSelectionScreen"
                    ) {
                        composable("roleSelectionScreen") {
                            RoleSelectionScreen(navController)
                        }

                        composable("loginScreen") {
                            LoginScreen(navController)
                        }

                        composable("homeScreen") {
                            HomeScreen(navController)
                        }

//                        composable("signUpScreen") {
//                            SignUpScreen()
//                        }

                        composable("profileScreen") {
                            ProfileScreen(navController)
                        }

                    }
                }
            }
        }
    }
}
