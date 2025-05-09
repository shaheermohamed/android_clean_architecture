package com.shahe.basiclearning.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.shahe.basiclearning.domain.model.AuthResult
import com.shahe.basiclearning.domain.use_case.auth_usecases.AuthUseCases
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl : AuthUseCases {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val result = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .await()
            val user = result.user
            if (user != null) {
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Login failed: user is null")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Login failed")
        }
    }

    override suspend fun register(email: String, password: String): AuthResult {
        return try {
            val result = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .await()
            val user = result.user
            if (user != null) {
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Registration failed: User is null")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Registration failed")
        }
    }
}