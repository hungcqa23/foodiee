package com.example.foodiee.data.models.User

import android.content.Context
import android.content.SharedPreferences
import com.example.foodiee.data.models.Role

class UserModel(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ROLE = "user_role"
    }

    fun setLoginState(isLoggedIn: Boolean, role: Role) {
        sharedPreferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            .putString(KEY_USER_ROLE, role.name)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserRole(): Role {
        val roleName = sharedPreferences.getString(KEY_USER_ROLE, Role.CUSTOMER.name)
        return Role.valueOf(roleName!!)
    }

    fun clearLoginState() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }
}