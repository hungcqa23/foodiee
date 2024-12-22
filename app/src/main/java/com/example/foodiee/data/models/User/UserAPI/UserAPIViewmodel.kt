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

class UserAPIViewModel : ViewModel(){
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser
    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun getAllUsers(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.getAllUsers()
                _users.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateUser(user: User){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.updateUser(user)
                _users.value = listOf(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                Log.d("login", "Starting API call: $loginRequest")
                val response = RetrofitInstance.UserApi.loginUser(loginRequest)
                Log.d("login", "Response received: $response")
                saveToken(response.data.token)
                _isLoggedIn.postValue(true)
            } catch (e: Exception) {
                Log.e("login", "API call failed: ${e.localizedMessage}")
                _isLoggedIn.postValue(false)
            }
        }
    }

    private fun saveToken(token: String) {
        // Implement token storage in SharedPreferences or other secure storage
    }
    fun signUpUser(user: User){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.signUpUser(user)
                _currentUser.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getCurrentUser(user: User){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.getCurrentUser(user.id.toInt())
                _currentUser.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
         }
    }
    fun logout(){
        _currentUser.value = null
        _isLoggedIn.value = false
    }
}