package com.example.foodiee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.foodiee.Navigation.FoodieeeNavHost
import com.example.foodiee.data.models.CourseDetails
import com.example.foodiee.data.models.User.UserModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.screens.*
import com.example.foodiee.ui.screens.auth.*
import com.example.foodiee.ui.screens.admin.OrdersManagementScreen
import com.example.foodiee.ui.screens.admin.PeopleManagementScreen
import com.example.foodiee.ui.screens.admin.StatisticsScreen
import com.example.foodiee.ui.screens.client.*
import com.example.foodiee.ui.theme.FoodieeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userModel = UserModel(applicationContext)
        val userViewModelFactory = UserViewModelFactory(userModel)
        val userViewModel: UserViewModel by viewModels { userViewModelFactory }

        enableEdgeToEdge()
        setContent {
            FoodieeeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    FoodieeeNavHost(navController = navController, userViewModel)
                }
            }
        }
    }
}


class UserViewModelFactory(private val userModel: UserModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}