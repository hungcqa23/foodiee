package com.example.foodiee.ui.components

import androidx.compose.foundation.*
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
import com.example.foodiee.Navigation.Routes
import com.example.foodiee.R
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun Footer(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
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
            selected = navController.currentDestination?.route == Routes.OrdersManagementScreen.route
        )
        BottomNavigationItem(
            navController = navController,
            icon = ImageVector.vectorResource(R.drawable.file_cog),
            text = "Config",
            routeNavigate = "inventoryScreen",
            selected = navController.currentDestination?.route == Routes.InventoryScreen.route ||
                    navController.currentDestination?.route == Routes.StatisticScreen.route ||
                    navController.currentDestination?.route == Routes.EditDishScreen.route
        )
        BottomNavigationItem(
            navController = navController,
            icon = ImageVector.vectorResource(R.drawable.people),
            text = "People",
            routeNavigate = "peopleManagementScreen",
            selected = navController.currentDestination?.route == Routes.PeopleManagementScreen.route
        )
        BottomNavigationItem(
            navController = navController,
            icon = ImageVector.vectorResource(R.drawable.profile),
            text = "Profile",
            routeNavigate = "profileScreen",
            selected = navController.currentDestination?.route == Routes.ProfileScreen.route
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                navController.navigate(routeNavigate) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
            }
    ) {
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
