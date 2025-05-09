package com.shahe.basiclearning.data.remote.dto

import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {
    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path(value = "coinId") coinId:String): CoinDetailDto
}