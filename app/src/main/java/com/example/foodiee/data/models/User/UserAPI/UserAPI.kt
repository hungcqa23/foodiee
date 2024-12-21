package com.example.foodiee.data.models.User.UserAPI

import com.example.foodiee.data.models.Role
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


data class User(
    val id: String,
    val fullName: String,
    val phoneNumber: String? = null,
    val address: String? = null,
    val email: String,
    val password: String,
    val role: Role = Role.USER,
    val profileImage: String? = null
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