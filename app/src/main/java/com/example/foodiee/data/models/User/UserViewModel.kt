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

    init {
        // Initialize state from SharedPreferences
        _isLoggedIn.value = userModel.isLoggedIn()
        _userRole.value = userModel.getUserRole()
    }

    fun login(role: Role) {
        userModel.setLoginState(true, role)
        _isLoggedIn.value = true
        _userRole.value = role
    }

    fun logout() {
        userModel.clearLoginState()
        _isLoggedIn.value = false
        _userRole.value = Role.CUSTOMER
    }
    fun getUserStatus(): UserStatus {
        return UserStatus(
            isLoggedIn = _isLoggedIn.value ?: false,
            role = _userRole.value ?: Role.CUSTOMER
        )
    }
}

data class UserStatus(
    val isLoggedIn: Boolean,
    val role: Role
)