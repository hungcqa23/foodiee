package com.example.foodiee.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.Navigation.Routes
import com.example.foodiee.R
import com.example.foodiee.data.models.Role
import com.example.foodiee.data.models.User.UserModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun Footer(navController: NavController, userViewModel: UserViewModel) {
    val userRole = userViewModel.userRole.observeAsState(initial = Role.CUSTOMER)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(WindowInsets.systemBars.asPaddingValues().calculateBottomPadding() + 64.dp)
            .border(
                width = 2.dp,
                color = FoodieeeColors.slate200,
            )
            .padding(bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding())
    ) {
        when (userRole.value) {
            Role.CUSTOMER -> {
                FooterItem(navController, R.drawable.home, "Home", Routes.HomeScreen.route)
                FooterItem(navController, R.drawable.orders, "Orders", Routes.OrdersManagementScreen.route)
                FooterItem(navController, R.drawable.profile, "Profile", Routes.ProfileScreen.route)
            }
            Role.EMPLOYEE -> {
                FooterItem(navController, R.drawable.orders, "Orders", Routes.OrdersManagementScreen.route)
                FooterItem(navController, R.drawable.file_cog, "Config", Routes.StatisticScreen.route)
                FooterItem(navController, R.drawable.people, "People", Routes.PeopleManagementScreen.route)
                FooterItem(navController, R.drawable.profile, "Profile", Routes.ProfileScreen.route)
            }
        }
    }
}

@Composable
fun FooterItem(
    navController: NavController,
    iconResId: Int,
    text: String,
    route: String,
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val selected = currentRoute == route

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                Log.d("nav", "before: ${navController.currentBackStackEntry?.destination?.route}")
                if (currentRoute != route) {
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        Log.d("nav", "after: $navController.currentBackStackEntry?.destination?.route")
                    }
                }
            }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconResId),
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = if (selected) FoodieeeColors.orange500 else Color.Black
        )
        Text(
            text = text,
            fontSize = 16.sp,
            letterSpacing = (-1).sp,
            color = if (selected) FoodieeeColors.orange500 else Color.Black,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}