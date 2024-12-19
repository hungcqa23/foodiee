package com.example.foodiee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.foodiee.Navigation.FoodieeeNavHost
import com.example.foodiee.data.models.CourseDetails
import com.example.foodiee.data.models.CustomerType
import com.example.foodiee.ui.screens.*
import com.example.foodiee.ui.screens.admin.InventoryScreen
import com.example.foodiee.ui.screens.auth.*
import com.example.foodiee.ui.screens.admin.OrdersManagementScreen
import com.example.foodiee.ui.screens.admin.PeopleManagementScreen
import com.example.foodiee.ui.screens.admin.StatisticsScreen
import com.example.foodiee.ui.screens.client.*
import com.example.foodiee.ui.theme.FoodieeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            FoodieeeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    FoodieeeNavHost(navController = navController)
                }
            }
        }
    }
}
