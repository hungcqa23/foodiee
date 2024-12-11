package com.example.foodiee.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
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
            .clickable { navController.popBackStack() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.chevron_left),
            contentDescription = "Back arrow",
            modifier = Modifier.size(24.dp)
        )
        Text(
            textDisplay,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}