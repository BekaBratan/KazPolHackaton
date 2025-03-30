package com.example.kazpol.data.model


import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)