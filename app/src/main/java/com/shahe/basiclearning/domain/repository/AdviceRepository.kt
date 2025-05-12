package com.shahe.basiclearning.domain.repository

import com.shahe.basiclearning.data.remote.dto.SlipDto
import com.shahe.basiclearning.domain.model.Slip

interface AdviceRepository {
    suspend fun getAdvice(): SlipDto
}