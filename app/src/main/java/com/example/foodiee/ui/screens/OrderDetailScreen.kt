package com.example.foodiee.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R
import com.example.foodiee.data.models.Course.CartItem
import com.example.foodiee.data.models.Course.Course
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.Course.OrderRespond
import com.example.foodiee.data.models.User.UserAPI.User
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun OrderDetailScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    courseViewModel: CourseViewModel,
    userAPIViewModel: UserAPIViewModel,
    orderId: String
) {
    val token = userAPIViewModel.getToken()
    var order = remember {
        mutableStateOf(
            OrderRespond(
                id = 0,
                paymentType = "Credit Card",
                status = "Pending",
                createdAt = "Now",
                user = User(
                    id = "0",
                    fullName = "Unknown",
                    email = "",
                    password = ""
                ),
                cartItems = listOf(
                    CartItem(
                        id = 0,
                        course = Course(
                            id = 0,
                            title = "Unknown",
                            description = "Unknown",
                            price = 0.0,
                            typeCourse = "Unknown",
                            quantity = 0,
                            ingredients = listOf(),
                            image = ""
                        ),
                        quantity = 0
                    )
                )
            )
        )
    }
    var totalAmount by remember { mutableStateOf("0.00") }

    LaunchedEffect(orderId) {
        Log.d("orderfatal", "fetching order")
        Log.d("order ID: ", orderId)  // Log the value of orderId to check if it's correct
        userAPIViewModel.getToken()?.let { token ->
            courseViewModel.getOrderById(token, orderId) { item ->
                Log.d("test: ", "Order fetched: ${item}")
                order.value = item
                totalAmount = calculateTotalAmount(order.value.cartItems)
            }
        }
        Log.d("orderfatal", "Order fetched: ${order.value}")
    }


    Scaffold(
        bottomBar = { Footer(navController, userAPIViewModel) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(ScrollState(1)),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
//                Header(
//                    orderId = orderId,
//                    customerName = order.value.user?.fullName ?: "Unknown",
//                    orderStatus = OrderStatus.PENDING
//                )
                OrderDetails(order.value)
                OrderItemsWithReviews(order.value)
                TotalAmount(totalAmount = totalAmount)
//                NoteSection(note = order.note)
                Spacer(modifier = Modifier.height(48.dp))
            }

            MarkAsCompletedButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                isFinished = if (order.value.status == "Completed") true else false,
                update = {
                    if (token != null) {
                        courseViewModel.updateOrder(
                            token,
                            orderId.toInt(),
                            "completed",
                            onSuccess = {
                                navController.popBackStack()
                            })
                    }
                }
            )
        }
    }
}

@Composable
fun OrderDetails(order: OrderRespond) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        order.createdAt?.let { OrderDetailRow(imageRes = R.drawable.clock, text = it) }
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
fun OrderItemsWithReviews(order: OrderRespond) {
    Text("Order Items", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

    val items = order.cartItems

    if (items != null) {
        items.forEachIndexed { index, item ->
            var isReviewed by remember { mutableStateOf(false) }
            OrderItemRowWithReview(
                item = item.course.title,  // Pass the individual item
                price = "$5.33", // Adjust price dynamically if needed
//                orderStatus = order.status?.let { OrderStatus.valueOf(it) } ?: OrderStatus.PENDING,
                isReviewed = isReviewed,
                onReviewSubmitted = { isReviewed = true }
            )
        }
    }

    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun OrderItemRowWithReview(
    item: String,
    price: String,
//    orderStatus: OrderStatus,
    isReviewed: Boolean,
    onReviewSubmitted: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = price,
                fontSize = 18.sp
            )
        }

        if (!isReviewed) {
            ReviewSection(onReviewSubmitted = onReviewSubmitted)
        } else if (isReviewed) {
            Text(
                text = "Review Submitted",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun ReviewSection(onReviewSubmitted: () -> Unit) {
    var rating by remember { mutableStateOf(0) }
    var reviewText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { index ->
                IconButton(
                    onClick = { rating = index + 1 },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = if (rating > index) R.drawable.star_filled else R.drawable.star),
                        tint = Color(0xFFffb84a),
                        contentDescription = "Star Rating"
                    )
                }
            }
        }

        TextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            placeholder = { Text("Leave a review...") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // Submit the review logic
                onReviewSubmitted()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
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
fun MarkAsCompletedButton(modifier: Modifier, isFinished: Boolean, update: () -> Unit) {
    Button(
        onClick = { update() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .then(modifier),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFinished) FoodieeeColors.green500 else Color.Black
        )
    ) {
        Text(
            text = "Mark as Completed",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

fun calculateTotalAmount(cartItems: List<CartItem>?): String {
    // Safely handle nullable cartItems, return 0.00 if null or empty
    val total = cartItems?.sumOf { it.quantity * it.course.price } ?: 0.0
    return "$${"%.2f".format(total)}"
}