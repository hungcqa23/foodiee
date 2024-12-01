package com.example.foodiee.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R

@Composable
fun BackButton(navController: NavController, textDisplay: String = "Back") {
    Row(
        modifier = Modifier
            .clickable { navController.popBackStack() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "Back arrow",
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = textDisplay,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}