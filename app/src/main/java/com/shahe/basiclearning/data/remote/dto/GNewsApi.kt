package com.shahe.basiclearning.data.remote.dto

import retrofit2.http.GET
import retrofit2.http.Query

interface GNewsApi {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("category") category: String,
        @Query("lang") lang: String = "en",
        @Query("country") country: String = "in",
        @Query("max") max: Int = 100,
        @Query("apikey") apiKey: String
    ): NewsDto
}