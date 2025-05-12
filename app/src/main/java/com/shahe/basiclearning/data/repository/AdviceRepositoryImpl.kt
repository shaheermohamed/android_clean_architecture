package com.shahe.basiclearning.data.repository

import com.shahe.basiclearning.data.remote.dto.AdviceApi
import com.shahe.basiclearning.data.remote.dto.SlipDto
import com.shahe.basiclearning.domain.repository.AdviceRepository
import javax.inject.Inject

class AdviceRepositoryImpl @Inject constructor(
    private val api: AdviceApi
) : AdviceRepository {
    override suspend fun getAdvice(): SlipDto {
        return api.getAdvice()
    }
}