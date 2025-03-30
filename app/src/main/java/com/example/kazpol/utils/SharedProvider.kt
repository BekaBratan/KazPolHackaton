package com.example.kazpol.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.kazpol.data.model.SignInResponse

class SharedProvider(private val context: Context) {
    private val sharedToken = "AccessToken"
    private val tokenType = "TokenType"
    private val isAuthorized = "isAuthorized"

    private val preferences: SharedPreferences
        get() = context.getSharedPreferences("User", Context.MODE_PRIVATE)

    private val systemPreferences: SharedPreferences
        get() = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    fun saveUser(user: SignInResponse) {
        val editor = preferences.edit()
        editor.clear()
        editor.putBoolean("isAuthorized", true)
        editor.apply()
        saveToken(user.accessToken.toString())
    }

    fun isAuthorized(): Boolean {
        return preferences.getBoolean(isAuthorized, false)
    }

    fun saveToken(token: String) {
        preferences.edit().putString(sharedToken, token).apply()
    }

    fun getToken():String {
        return "${preferences.getString(tokenType, "without_token_type")} ${preferences.getString(sharedToken, "without_token")}"
    }

    fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(sharedToken, token)
        Log.d("AAA", "setToken: $token")
        editor.apply()
    }

    fun clearShared() {
        preferences.edit().clear().apply()
    }

}