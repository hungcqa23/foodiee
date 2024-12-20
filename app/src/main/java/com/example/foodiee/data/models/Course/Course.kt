package com.example.foodiee.data.models.Course

import retrofit2.Call
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


data class CourseResponse(
    val status: String,
    val data: List<Course>
)
data class CourseResponseDetail(
    val status: String,
    val data: Course
)

data class Course(
    val id: Int? = null,
    val title: String,
    val description: String,
    val typeCourse: String,
    val quantity: Int,
    val price: Double,
    val ingredients: List<String>,
    val image: String? = null
)

interface CourseApiService {
    @POST("courses")
    suspend fun createCourse(@Body course: Course): Course

    @GET("courses")
    suspend fun getAllCourses(): CourseResponse

    @GET("courses/{id}")
    suspend fun getCourseById(@Path("id") id: Int): CourseResponseDetail

    @PATCH("courses/{id}")
    suspend fun updateCourse(@Path("id") id: Int, @Body updatedCourse: Course): Course
}



object RetrofitInstance {
    private const val BASE_URL = "https://294e-171-233-142-38.ngrok-free.app/"



    private val client = OkHttpClient.Builder()
        .build()

    val api: CourseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseApiService::class.java)
    }
}