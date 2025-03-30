package com.example.unihub.data.api

import com.example.unihub.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val client = OkHttpClient.Builder().build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }


    private val retrofitNews by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiNews by lazy {
        retrofitNews.create(ApiServiceNews::class.java)
    }

    fun<T> buildServiceNews(service: Class<T>): T{
        return retrofitNews.create(service)
    }
}