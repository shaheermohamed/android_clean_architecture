package com.shahe.basiclearning.presentation.Authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.shahe.basiclearning.common.DataStoreManager
import com.shahe.basiclearning.common.EncryptedPrefs
import com.shahe.basiclearning.domain.model.AuthResult
import com.shahe.basiclearning.domain.use_case.auth_usecases.AuthUseCases
import com.shahe.basiclearning.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        checkLoginStatus()
    }

    fun checkLoginStatus() {
        _isLoggedIn.value = FirebaseAuth.getInstance().currentUser != null
    }

    var authResult by mutableStateOf<AuthResult?>(null)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authResult = AuthResult.Loading
            authResult = authUseCases.login(email, password)
            EncryptedPrefs.saveCredentials(email, password)
            checkLoginStatus()
        }

    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            authResult = AuthResult.Loading
            authResult = authUseCases.register(email, password)
            checkLoginStatus()
        }
    }
    fun logout(navController: NavController) {
        FirebaseAuth.getInstance().signOut()
        checkLoginStatus()
        viewModelScope.launch {
            delay(2000) // 2-second delay
            navController.navigate(Screen.Login.route)
        }
    }

}