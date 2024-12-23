package com.example.foodiee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.components.order_detail_screen.Header

@Composable
fun OrderDetailScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    courseViewModel: CourseViewModel
    orderId: String
) {
    val order = Order(
        "1", "John Doe", OrderStatus.COMPLETED, listOf("Burger", "Fries", "Soda"), "$15.99", "10:30 AM",
        note = "Please deliver without ketchup"
    )

    Scaffold(
        bottomBar = { Footer(navController, userViewModel) }
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
                OrderItemsWithReviews(order)
                TotalAmount(totalAmount = "$45.97")
                NoteSection(note = order.note)
            }

            MarkAsCompletedButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                isFinished = order.orderStatus == OrderStatus.FINISHED
            )
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
fun OrderItemsWithReviews(order: Order) {
    Text("Order Items", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

    order.items.forEachIndexed { index, item ->
        var isReviewed by remember { mutableStateOf(false) }
        OrderItemRowWithReview(
            item = item,
            price = "$5.33", // Adjust price as needed
            orderStatus = order.orderStatus,
            isReviewed = isReviewed,
            onReviewSubmitted = { isReviewed = true }
        )
    }

    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun OrderItemRowWithReview(
    item: String,
    price: String,
    orderStatus: OrderStatus,
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

        if (orderStatus != OrderStatus.PENDING && !isReviewed) {
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
                        painter = painterResource(id = if (rating > index) R.drawable.ic_star_filled else R.drawable.ic_star_outline),
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
fun MarkAsCompletedButton(modifier: Modifier, isFinished: Boolean) {
    Button(
        onClick = { /* Handle button click */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .then(modifier),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFinished) FoodieeeColors.Green600 else Color.Black
        )
    ) {
        Text(
            text = "Mark as Completed",
            color = Color.White,
            fontSize = 18.sp
        )
    }
}
