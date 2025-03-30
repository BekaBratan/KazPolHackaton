package com.example.unihub.data.api

import com.example.kazpol.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceNews {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("v2/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesBySource(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

}