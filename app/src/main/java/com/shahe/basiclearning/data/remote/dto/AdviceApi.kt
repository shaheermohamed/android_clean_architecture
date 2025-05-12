package com.shahe.basiclearning.data.remote.dto

import retrofit2.http.GET

interface AdviceApi {
    @GET("advice")
    suspend fun getAdvice(): SlipDto
}