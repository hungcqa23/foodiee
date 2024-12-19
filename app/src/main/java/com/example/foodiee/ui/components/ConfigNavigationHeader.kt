package com.example.foodiee.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.Navigation.Routes

enum class ConfigSubPage {
    EDIT_DISH,
    STATISTICS,
}

@Composable
fun ConfigNavigationHeader(currentConfigSubPage: ConfigSubPage, navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Text("Edit Dish",
            fontSize = 18.sp,
            color = Color.Black,
            textDecoration = if (currentConfigSubPage == ConfigSubPage.EDIT_DISH) TextDecoration.Underline else TextDecoration.None,
            fontWeight = if (currentConfigSubPage == ConfigSubPage.EDIT_DISH) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.clickable { navController.navigate(Routes.EditDishScreen.route) }
        )
        Text("Statistics",
            fontSize = 18.sp,
            color = Color.Black,
            textDecoration = if (currentConfigSubPage == ConfigSubPage.STATISTICS) TextDecoration.Underline else TextDecoration.None,
            fontWeight = if (currentConfigSubPage == ConfigSubPage.STATISTICS) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.clickable { navController.navigate(Routes.StatisticScreen.route) }
        )
    }
}