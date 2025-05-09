package com.shahe.basiclearning.domain.use_case.auth_usecases

import com.shahe.basiclearning.domain.model.AuthResult

interface AuthUseCases {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, password: String): AuthResult
}