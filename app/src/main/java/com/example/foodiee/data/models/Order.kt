package com.example.foodiee.data.models

enum class OrderStatus {
    PENDING, COMPLETED
}

data class Order(
    val orderId: String,
    val customerName: String,
    val orderStatus: OrderStatus,
    val orderDetails: String,
    val price: String,
    val time: String
)