package com.example.foodiee.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.foodiee.data.models.CustomerType

enum class configSubPage{
    EDIT_DISH,
    STATISTICS,
    INVENTORY
}

@Composable
fun NavigationHeader(currentConfigSubPage: configSubPage ,navController: NavController){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
            .fillMaxWidth()
            .height(64.dp)
    ){
        Text("Edit Dish",
            fontSize = 18.sp,
            color = Color.Black,
            textDecoration = if (currentConfigSubPage == configSubPage.EDIT_DISH) TextDecoration.Underline else TextDecoration.None,
            fontWeight = if (currentConfigSubPage == configSubPage.EDIT_DISH) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.clickable { navController.navigate("editDish") }
        )
        Text("Statistics",
            fontSize = 18.sp,
            color = Color.Black,
            textDecoration = if (currentConfigSubPage == configSubPage.STATISTICS) TextDecoration.Underline else TextDecoration.None,
            fontWeight = if (currentConfigSubPage == configSubPage.STATISTICS) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.clickable { navController.navigate("statistics") }
        )
        Text("Inventory",
            fontSize = 18.sp,
            color = Color.Black,
            textDecoration = if (currentConfigSubPage == configSubPage.INVENTORY) TextDecoration.Underline else TextDecoration.None,
            fontWeight = if (currentConfigSubPage == configSubPage.INVENTORY) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.clickable { navController.navigate("inventory") }
        )
    }
}