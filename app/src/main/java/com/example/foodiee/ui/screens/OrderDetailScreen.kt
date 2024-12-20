package com.example.foodiee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R
import com.example.foodiee.data.models.Order
import com.example.foodiee.data.models.OrderStatus
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.components.order_detail_screen.Header

@Composable
fun OrderDetailScreen(navController: NavController, orderId: String) {
    val order = Order(
        "1", "John Doe", OrderStatus.PENDING, "Burger, Fries, Soda", "$15.99", "10:30 AM",
        note = "Please deliver without ketchup" // Example note
    )

    Scaffold(
        bottomBar = { Footer(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Header(
                    orderId = orderId,
                    customerName = order.customerName,
                    orderStatus = order.orderStatus
                )
                OrderDetails(order)
                OrderItems(orderItems = listOf("Burger", "Fries", "Soda"), price = "$15.99")
                TotalAmount(totalAmount = "$45.97")
                NoteSection(note = order.note)
            }

            MarkAsCompletedButton(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun OrderDetails(order: Order) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OrderDetailRow(imageRes = R.drawable.clock, text = order.time)
        OrderDetailRow(imageRes = R.drawable.map_pin, text = "123 Main St, Anytown, AN 12345")
    }

    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun OrderDetailRow(imageRes: Int, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Icon",
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun OrderItems(orderItems: List<String>, price: String) {
    Text("Order Items", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

    orderItems.forEach { item ->
        OrderItemRow(item = item, price = price)
    }

    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun OrderItemRow(item: String, price: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "3x",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = item,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            text = price,
            fontSize = 18.sp
        )
    }
}

@Composable
fun TotalAmount(totalAmount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Total",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = totalAmount,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun NoteSection(note: String?) {
    if (!note.isNullOrEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    ) {
                        append("Note: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(note)
                    }
                }
            )
        }
    }
}

@Composable
fun MarkAsCompletedButton(modifier: Modifier) {
    Button(
        onClick = { /* Handle button click */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .then(modifier),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text(
            text = "Mark as Completed",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}
