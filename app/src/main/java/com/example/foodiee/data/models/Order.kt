package com.example.foodiee.data.models

import com.example.foodiee.R

enum class OrderStatus {
    PENDING, CANCELED, COMPLETED
}

data class Order(
    val orderId: String,
    val customerName: String,
    val orderStatus: OrderStatus,
    val orderDetails: String,
    val price: String,
    val time: String,
    val note: String? = null
)


fun MockInventoryItems(): List<InventoryItem> {
    return listOf(
        InventoryItem(
            name = "Apple",
            type = "Fruit",
            price = "$1.00",
            quantity = 50,
            image = R.drawable.detailed_course
        ),
        InventoryItem(
            name = "Bread",
            type = "Bakery",
            price = "$2.50",
            quantity = 30,
            image = R.drawable.detailed_course
        ),
        InventoryItem(
            name = "Carrot",
            type = "Vegetable",
            price = "$0.75",
            quantity = 100,
            image = R.drawable.detailed_course
        ),
        InventoryItem(
            name = "Milk",
            type = "Dairy",
            price = "$3.00",
            quantity = 20,
            image = R.drawable.detailed_course
        ),
        InventoryItem(
            name = "Chicken",
            type = "Meat",
            price = "$5.00",
            quantity = 15,
            image = R.drawable.detailed_course
        )
    )
}

data class InventoryItem(
    val name: String,
    val type: String,
    val price: String,
    val quantity: Int,
    val image: Int
)
