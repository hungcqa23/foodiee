package com.example.foodiee.data.models.User.UserAPI

import com.example.foodiee.data.models.Role
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

class RoleDeserializer : JsonDeserializer<Role> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Role {
        return Role.fromString(json.asString)
    }
}

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
data class SignUpRequest(
    val fullName: String,
    val email: String,
    val password: String
)
data class ApiRespond<T>(
    val status: String,
    val data: List<T>
)
data class currentUser(
    val status: String,
    val data: User
)
data class UpdateRequest(
    val email: String,
    val phoneNumber: String,
    val address: String,
)

interface UserApiService {
    @POST("users")
    suspend fun CurrentUser(@Body user: User): User

    @GET("users")
    suspend fun getAllUsers(): ApiRespond<User>

    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse  // Added @Body annotation

    @POST("users/sign-up")
    suspend fun signUpUser(@Body request: SignUpRequest): User  // Added @Body annotation

    @PATCH("users/current")
    suspend fun updateUser(
        @Body user: UpdateRequest,
        @Header("Authorization") token: String
    ): User  // Added @Body annotation

    @GET("users/current")
    suspend fun getCurrentUser(@Header("Authorization") token:String): currentUser

    @PATCH("users/{id}")
    suspend fun updateRole(
        @Body role : Role,
        @Path ("id") id: String
    ): User
}