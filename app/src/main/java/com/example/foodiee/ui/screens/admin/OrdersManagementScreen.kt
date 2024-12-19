package com.example.foodiee.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.data.models.Order
import com.example.foodiee.data.models.OrderStatus
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.theme.Slate500

@Composable
fun OrdersManagementScreen(navController: NavController, userViewModel: UserViewModel) {
    val tabs = listOf("Pending", "Completed")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val ordersData = mapOf(
        "Pending" to listOf(
            Order(
                "1",
                "John Doe",
                OrderStatus.PENDING,
                "Burger, Fries, Soda",
                "$15.99",
                "10:30 AM"
            ),
            Order("2", "Jane Smith", OrderStatus.PENDING, "Pizza, Salad", "$12.49", "11:00 AM")
        ),
        "Completed" to listOf(
            Order(
                "1",
                "Alice Johnson",
                OrderStatus.COMPLETED,
                "Pasta, Garlic Bread",
                "$18.75",
                "9:45 AM"
            ),
            Order("2", "Bob Brown", OrderStatus.COMPLETED, "Steak, Fries", "$22.99", "8:30 AM")
        )
    )

    Scaffold(
        bottomBar = { Footer(navController = navController, userViewModel) }
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    title,
                                    fontSize = 18.sp
                                )
                            },
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val selectedTab = tabs[selectedTabIndex]
                    val orders = ordersData[selectedTab] ?: emptyList()

                    items(orders.size) { index ->
                        OrderItem(order = orders[index], navController = navController)
                    }
                }
            }
        }
    }
}


@Composable
fun OrderItem(order: Order, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp)) // Add shadow here
            .clip(RoundedCornerShape(8.dp))         // Clip the shape
            .background(Color.White)               // Background color for the shadowed card
            .padding(16.dp)
            .clickable(
                onClick = { navController.navigate("orderDetail/${order.orderId}") }
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = order.customerName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                letterSpacing = (-0.2).sp
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black)
                    .padding(vertical = 1.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = if (order.orderStatus == OrderStatus.PENDING) "Pending" else "Completed",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }

        Text(
            text = order.orderDetails,
            color = Slate500
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = order.price,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = order.time,
                letterSpacing = (-0.3).sp
            )
        }
    }
}