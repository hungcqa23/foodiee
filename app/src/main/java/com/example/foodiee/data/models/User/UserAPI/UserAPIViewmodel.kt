package com.example.foodiee.data.models.User.UserAPI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiee.data.models.RetrofitInstance
import kotlinx.coroutines.launch

class UserAPIViewmodel : ViewModel(){
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

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
    fun getCurrentUser(id: Int){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.getCurrentUser(id)
                _users.value = listOf(response)
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
                _users.value = listOf(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun signUpUser(user: User){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.signUpUser(user)
                _users.value = listOf(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getCurrentUser(user: User){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.UserApi.getCurrentUser(user)
                _users.value = listOf(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
         }
    }
}