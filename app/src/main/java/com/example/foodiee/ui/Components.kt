package com.example.foodiee.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
            .size(75.dp)
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .border(width = 1.dp, color = Color(0xFF4A403A), shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(color = Color(0xFFFDFCFB))
    ){
        BottomNavigationItem(icon = ImageVector.vectorResource(R.drawable.orders),"Orders", selected = if(navController.currentDestination?.route == "Orders") true else false) { }
        BottomNavigationItem(icon = ImageVector.vectorResource(R.drawable.config),"Config", selected = if(navController.currentDestination?.route == "Config") true else false) { }
        BottomNavigationItem(icon = ImageVector.vectorResource(R.drawable.people),"People", selected = if(navController.currentDestination?.route == "People") true else false) { }
        BottomNavigationItem(icon = ImageVector.vectorResource(R.drawable.profile),"Profile", selected = if(navController.currentDestination?.route == "Profile") true else false) { }

    }
}

@Composable
fun BottomNavigationItem(icon: ImageVector, text: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview (showBackground = false)
@Composable
fun PreviewNavBar(){
    BottomNavigationBar(navController = NavController(LocalContext.current))
//    BottomNavigationItem(icon = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),"testing", selected = true) { }
}