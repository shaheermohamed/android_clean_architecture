package com.shahe.basiclearning.domain.model

import com.google.firebase.auth.FirebaseUser

sealed class AuthResult{
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
    object Loading : AuthResult()
}