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

                        composable("signUpScreen") {
                            SignUpScreen()
                        }

                        composable("profileScreen") {
                            ProfileScreen()
                        }


                    }
                }
            }
        }
    }
}

@Composable
fun SignUpScreen() {
    // TODO: Add your sign up screen content here
    Text("Hello from Sign Up Screen")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodieeeTheme {
        Text("Hello from Login Screen")
    }
}
