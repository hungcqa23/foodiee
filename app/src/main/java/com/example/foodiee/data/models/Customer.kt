package com.example.foodiee.data.models

data class Customer(
    val id: String,
    val name: String,
    val phone: String,
    val cccd: String,
    val address: String,
    val type: Role
)

enum class Role {
    CUSTOMER,
    EMPLOYEE
}