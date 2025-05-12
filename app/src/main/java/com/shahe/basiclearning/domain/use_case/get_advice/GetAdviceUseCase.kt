package com.shahe.basiclearning.domain.use_case.get_advice

import com.shahe.basiclearning.common.Resource
import com.shahe.basiclearning.data.remote.dto.toAdvice
import com.shahe.basiclearning.domain.model.Slip
import com.shahe.basiclearning.domain.repository.AdviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetAdviceUseCase @Inject constructor(
    private val repository: AdviceRepository
) {
    operator fun invoke(): Flow<Resource<Slip>> = flow {
        try {
            emit(Resource.Loading<Slip>())
            val response = repository.getAdvice().toAdvice()
            emit(Resource.Success<Slip>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<Slip>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: java.io.IOException) {
            emit(Resource.Error<Slip>("Couldn't reach server. Check your internet connection."))
        }
    }
}