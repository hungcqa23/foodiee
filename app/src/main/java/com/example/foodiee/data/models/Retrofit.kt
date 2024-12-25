package com.example.foodiee.data.models

import com.example.foodiee.data.models.Course.CourseApiService
import com.example.foodiee.data.models.User.UserAPI.RoleDeserializer
import com.example.foodiee.data.models.User.UserAPI.UserApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://c648-14-161-13-78.ngrok-free.app"

    // Create a Gson instance with the RoleDeserializer
    private val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Role::class.java, RoleDeserializer())
            .create()
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val CourseApi: CourseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use the Gson instance here
            .build()
            .create(CourseApiService::class.java)
    }

    val UserApi: UserApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use the Gson instance here
            .build()
            .create(UserApiService::class.java)
    }
}