package com.shahe.basiclearning.domain.use_case.get_coin

import com.shahe.basiclearning.common.Resource
import com.shahe.basiclearning.data.remote.dto.toCoin
import com.shahe.basiclearning.data.remote.dto.toCoinDetail
import com.shahe.basiclearning.domain.model.Coin
import com.shahe.basiclearning.domain.model.CoinDetail
import com.shahe.basiclearning.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading<CoinDetail>())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success<CoinDetail>(coin))
        } catch(e: HttpException) {
            emit(Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<CoinDetail>("Couldn't reach server. Check your internet connection."))
        }
    }
}