package com.example.foodiee.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun Footer(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .size(75.dp)
            .border(
                width = 2.dp,
                color = FoodieeeColors.slate200,
            )
    ) {
        BottomNavigationItem(
            navController = navController,
            icon = ImageVector.vectorResource(R.drawable.orders),
            text = "Orders",
            routeNavigate = "OrdersManagementScreen",
            selected = navController.currentDestination?.route == "ordersManagementScreen"
        )
        BottomNavigationItem(
            navController = navController,
            icon = ImageVector.vectorResource(R.drawable.config),
            text = "Config",
            routeNavigate = "Config",
            selected = navController.currentDestination?.route == "Config"
        )
        BottomNavigationItem(
            navController = navController,
            icon = ImageVector.vectorResource(R.drawable.people),
            text = "People",
            routeNavigate = "People",
            selected = navController.currentDestination?.route == "People"
        )
        BottomNavigationItem(
            navController = navController,
            icon = ImageVector.vectorResource(R.drawable.profile),
            text = "Profile",
            routeNavigate = "profileScreen",
            selected = navController.currentDestination?.route == "profileScreen"
        )
    }
}

@Composable
fun BottomNavigationItem(
    navController: NavController,
    icon: ImageVector,
    text: String,
    routeNavigate: String,
    selected: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        navController.navigate(routeNavigate) {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
        }
    }) {
        Icon(
            imageVector = icon,
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
