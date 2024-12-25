package com.example.foodiee.ui.screens.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.Course.OrderRespond
import com.example.foodiee.data.models.Order
import com.example.foodiee.data.models.OrderStatus
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.theme.Slate500

@Composable
fun OrdersManagementScreen(navController: NavController, userViewModel: UserViewModel, courseViewModel: CourseViewModel, userAPIViewModel: UserAPIViewModel) {
    val tabs = listOf("Pending", "Completed")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val ordersData = courseViewModel.orders.collectAsState().value
    LaunchedEffect(selectedTabIndex) {
        Log.d("orderfatal", "fetching orders")
        userAPIViewModel.getToken()?.let { courseViewModel.getOrders(it, tabs[selectedTabIndex]) }
        Log.d("orderfatal", "fetched orders")
        Log.d("orderfatal", ordersData.toString())
    }

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
                    Log.d("orderfatal", (ordersData == emptyList<Order>()).toString())
                    if(ordersData.isEmpty()) {
                        item {
                            Text(
                                text = "No orders found",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                color = Color.Gray
                            )
                        }
                    }else{
                        items(ordersData) { item ->
                            Log.d("orderfatal", item.toString())
                            OrderItem(order = item, navController = navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OrderItem(order: OrderRespond, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp)) // Add shadow here
            .clip(RoundedCornerShape(8.dp))         // Clip the shape
            .background(Color.White)               // Background color for the shadowed card
            .padding(16.dp)
            .clickable(
                onClick = { navController.navigate("orderDetail/${order.id}") }
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            order.user?.fullName?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    letterSpacing = (-0.2).sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black)
                    .padding(vertical = 1.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = order.status ?: "Pending",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }

        order.paymentType?.let {
            Text(
                text = it,
                color = Slate500
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            order.cartItems?.let {
                val sum = it.sumOf { cartItem -> cartItem.course.price * cartItem.quantity }
                val roundedSum = String.format("%.2f", sum) // Rounds to 2 decimal places
                Text(
                    text = roundedSum,
                    fontWeight = FontWeight.SemiBold,
                )
            }

                Text(
                    text = order.createdAt ?: "Unknown",
                    letterSpacing = (-0.3).sp
                )
        }
    }
}