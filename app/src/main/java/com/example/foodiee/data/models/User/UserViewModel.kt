package com.example.foodiee.data.models.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodiee.data.models.Role

class UserViewModel(private val userModel: UserModel) : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    private val _userRole = MutableLiveData<Role>()
    val userRole: LiveData<Role> get() = _userRole

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> get() = _token

    init {
        // Initialize state from SharedPreferences
        _isLoggedIn.value = userModel.isLoggedIn()
        _userRole.value = userModel.getUserRole()
        _token.value = userModel.getToken()
    }

    fun login(role: Role, token: String) {
        userModel.setLoginState(true, role, token)
        _isLoggedIn.value = true
        _userRole.value = role
        _token.value = token
    }

    fun logout() {
        userModel.clearLoginState()
        _isLoggedIn.value = false
        _userRole.value = Role.USER
        _token.value = null
    }

    fun getUserStatus(): UserStatus {
        return UserStatus(
            isLoggedIn = _isLoggedIn.value ?: false,
            role = _userRole.value ?: Role.USER,
            token = _token.value
        )
    }
}


data class UserStatus(
    val isLoggedIn: Boolean,
    val role: Role,
    val token: String?
)
