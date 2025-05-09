package com.shahe.basiclearning.data.repository

import com.shahe.basiclearning.data.remote.dto.CoinDetailDto
import com.shahe.basiclearning.data.remote.dto.CoinDto
import com.shahe.basiclearning.data.remote.dto.CoinPaprikaApi
import com.shahe.basiclearning.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val api: CoinPaprikaApi): CoinRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}