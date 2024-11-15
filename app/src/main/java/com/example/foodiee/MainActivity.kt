package com.example.foodiee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.example.foodiee.ui.screens.LoginScreen
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
//                    // Set up the nav controller
                    val navController = rememberNavController()
//                    // Set up Navigation Graph
                    NavHost(navController = navController, startDestination = "loginScreen") {
                        composable("loginScreen") {
                            // This will navigate to the LoginScreen
                            LoginScreen(navController)
                        }

                        composable("homeScreen") {
                            // This will navigate to the HomeScreen
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    // TODO: Add your home screen content here
    Text("Hello from Home Screen")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodieeeTheme {
        Text("Hello from Login Screen")
    }
}
