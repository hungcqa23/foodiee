package com.example.foodiee.data.models.User.UserAPI

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiee.data.models.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.lifecycle.*
import com.google.gson.Gson
import kotlinx.coroutines.launch

class UserAPIViewModel(context: Context) : ViewModel() {
    private val sharedPreferences = createEncryptedSharedPreferences(context)

    private val _users = MutableLiveData<List<User>?>()
    val users: LiveData<List<User>?> = _users

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    // Get all users
    fun getAllUsers() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.getAllUsers()
                _users.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update a user
    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.updateUser(user)
                _users.value = listOf(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Login a user and save token
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = RetrofitInstance.UserApi.loginUser(loginRequest)
                saveToken(response.data.token)
                _isLoggedIn.postValue(true)
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoggedIn.postValue(false)
            }
        }
    }

    // Sign up a user
    fun signUpUser(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val request = SignUpRequest(fullName, email, password)
                val response = RetrofitInstance.UserApi.signUpUser(request)
                _currentUser.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Get current user by ID
    fun getCurrentUser(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.getCurrentUser("Bearer $token")
                _currentUser.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Logout and clear token
    fun logout() {
        clearToken()
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    // Retrieve token from SharedPreferences
    fun getToken(): String? {
        return sharedPreferences.getString("TOKEN", null)
    }

    // Clear token from SharedPreferences
    private fun clearToken() {
        sharedPreferences.edit().remove("TOKEN").apply()
        _token.postValue(null)
    }

    // Save all variables to EncryptedSharedPreferences
    private fun saveState() {
        sharedPreferences.edit()
            .putString("TOKEN", _token.value)
            .putString("CURRENT_USER", _currentUser.value?.toJson())
            .putString("USERS", _users.value?.toJson())
            .putBoolean("IS_LOGGED_IN", _isLoggedIn.value ?: false)
            .apply()
    }

    // Restore state from EncryptedSharedPreferences
    private fun restoreState() {
        _token.value = sharedPreferences.getString("TOKEN", null)
        _currentUser.value = sharedPreferences.getString("CURRENT_USER", null)?.fromJson<User>()
        _users.value = sharedPreferences.getString("USERS", null)?.fromJson<List<User>>()
        _isLoggedIn.value = sharedPreferences.getBoolean("IS_LOGGED_IN", false)
    }

    // Save token and trigger saveState
    private fun saveToken(token: String) {
        _token.postValue(token)
        saveState()
    }

    // Clear all saved data
    private fun clearState() {
        sharedPreferences.edit().clear().apply()
        _token.postValue(null)
        _currentUser.postValue(null)
        _users.postValue(null)
        _isLoggedIn.postValue(false)
    }

    // Utility functions for JSON conversion
    private inline fun <reified T> String.fromJson(): T? {
        return try {
            Gson().fromJson(this, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun <T> T.toJson(): String {
        return Gson().toJson(this)
    }

    // Initialize EncryptedSharedPreferences
    private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "user_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    init {
        restoreState()
    }

}
