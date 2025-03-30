package com.example.kazpol.data.model


import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("current_location")
    val currentLocation: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("iin")
    val iin: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)