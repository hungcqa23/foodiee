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
    USER,
    STAFF,
    ADMIN;

    companion object {
        fun fromString(value: String): Role {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown role: $value")
        }
    }
    
}