package com.example.kazpol.data.model


import com.google.gson.annotations.SerializedName

data class SOSRequest(
    @SerializedName("latitude")
    val latitude: Int,
    @SerializedName("longitude")
    val longitude: Int
)