package com.example.foodiee.data.models

data class Customer(
    val id: String,
    val name: String,
    val phone: String,
    val cccd: String,
    val address: String,
    val type: CustomerType
)

enum class CustomerType {
    CUSTOMER, EMPLOYEE
}