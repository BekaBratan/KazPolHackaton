package com.example.unihub.data.api

import com.example.kazpol.data.model.SOSRequest
import com.example.kazpol.data.model.SignInRequest
import com.example.kazpol.data.model.SignInResponse
import com.example.kazpol.data.model.SignUpRequest
import com.example.kazpol.data.model.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Response<ResponseBody>

    @POST("v1/auth/sign-in")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): SignInResponse

    @POST("v1/auth/sign-up")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): SignUpResponse

    @POST("v1/communications/sos")
    suspend fun SOS(
        @Header("Authorization") token: String,
        @Body sosRequest: SOSRequest
    ): SignUpResponse
}