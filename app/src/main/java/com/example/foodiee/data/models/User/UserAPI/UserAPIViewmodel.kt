package com.example.foodiee.data.models.User.UserAPI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiee.data.models.RetrofitInstance
import kotlinx.coroutines.launch

class UserAPIViewModel : ViewModel(){
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> = _currentUser

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
    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.loginUser(email, password)
                _currentUser.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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
                val response = RetrofitInstance.UserApi.getCurrentUser(user)
                _currentUser.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
         }
    }
}