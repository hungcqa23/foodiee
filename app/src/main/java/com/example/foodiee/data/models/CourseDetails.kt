package com.example.foodiee.data.models

import com.example.foodiee.R

data class CourseDetails(
    val title: String,
    val location: String,
    val price: String,
    val rating: Double,
    val description: String,
    val ingredients: List<String>,
    val mealType: String,
    val imageResId: Int = R.drawable.detailed_course
)