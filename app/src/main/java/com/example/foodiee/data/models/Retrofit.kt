package com.example.foodiee.data.models

import com.example.foodiee.data.models.Course.CourseApiService
import com.example.foodiee.data.models.User.UserAPI.UserApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://94b0-171-233-142-38.ngrok-free.app"



    private val client = OkHttpClient.Builder()
        .build()

    val CourseApi: CourseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseApiService::class.java)
    }
    val UserApi: UserApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }
}
