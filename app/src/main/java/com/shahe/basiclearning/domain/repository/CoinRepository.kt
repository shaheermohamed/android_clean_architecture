package com.shahe.basiclearning.domain.repository

import com.shahe.basiclearning.data.remote.dto.CoinDetailDto
import com.shahe.basiclearning.data.remote.dto.CoinDto

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailDto
}