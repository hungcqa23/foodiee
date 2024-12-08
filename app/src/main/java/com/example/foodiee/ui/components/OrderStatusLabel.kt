package com.example.foodiee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodiee.data.models.OrderStatus
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun OrderStatusLabel(orderStatus: OrderStatus) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(
                when (orderStatus) {
                    OrderStatus.PENDING -> FoodieeeColors.orange400
                    OrderStatus.COMPLETED -> FoodieeeColors.green400
                    else -> Color.Red
                }
            )
            .padding(vertical = 1.dp, horizontal = 10.dp)
    ) {
        Text(
            text = if (orderStatus == OrderStatus.PENDING) "Pending" else "Completed",
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}