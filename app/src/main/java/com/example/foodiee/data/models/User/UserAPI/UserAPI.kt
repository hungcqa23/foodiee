package com.example.foodiee.data.models.User.UserAPI

import com.example.foodiee.data.models.Role
import retrofit2.http.Body
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
data class LoginResponse(
    val status: String,
    val data: LoginData
)
data class LoginData(
    val token: String
)
data class LoginRequest(
    val email: String,
    val password: String
)

interface UserApiService {
    @POST("users")
    suspend fun CurrentUser(@Body user: User): User

    @GET("users")
    suspend fun getAllUsers(): List<User>

    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse  // Added @Body annotation

    @POST("users/sign-up")
    suspend fun signUpUser(@Body user: User): User  // Added @Body annotation

    @PATCH("users/current")
    suspend fun updateUser(@Body user: User): User  // Added @Body annotation

    @GET("users/{id}")
    suspend fun getCurrentUser(@Path("id") id: Int): User
}