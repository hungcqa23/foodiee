package com.example.foodiee.ui.components.order_detail_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.foodiee.R
import com.example.foodiee.data.models.OrderStatus
import com.example.foodiee.ui.components.BackButton
import com.example.foodiee.ui.components.OrderStatusLabel

@Composable
fun CustomerInfo(customerName: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Name icon",
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = customerName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun Header(orderId: String, customerName: String, orderStatus: OrderStatus) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Order #$orderId",
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomerInfo(customerName = customerName)
        OrderStatusLabel(orderStatus = orderStatus)
    }
}