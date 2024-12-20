package com.example.foodiee.data.models.User.UserAPI

import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


data class User(
    val fullname: String,
    val email: String,
    val password: String
)

interface UserApiService {

    @POST("users")
    suspend fun getCurrentUser(user: User): User

    @GET("users")
    suspend fun getAllUsers(): List<User>

    @POST("users/login")
    suspend fun loginUser(email: String, password: String): User

    @POST("users/sign-up")
    suspend fun signUpUser(user: User): User

    @PATCH("users/current")
    suspend fun updateUser(user: User): User

    @GET("users/{id}")
    suspend fun getCurrentUser(@Path("id") id: Int): User

}