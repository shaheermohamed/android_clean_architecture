package com.shahe.basiclearning.presentation.Authentication

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.shahe.basiclearning.common.BiometricHelper
import com.shahe.basiclearning.common.EncryptedPrefs
import com.shahe.basiclearning.presentation.Screen

@Composable
fun BiometricScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    var authError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf<Boolean?>(false) }

    LaunchedEffect(Unit) {
        activity?.let {
            val biometricHelper = BiometricHelper(
                context = context,
                activity = it,
                onSuccess = {
                    val email = EncryptedPrefs.getEmail()
                    val password = EncryptedPrefs.getPassword()
                    if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                        FirebaseAuth.getInstance()
                            .signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener {
                                navController.navigate(Screen.CoinListScreen.route) {
                                    popUpTo(Screen.BioMetric.route) { inclusive = true }
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(
                            context,
                            "No credentials found, Please make login with email and password",
                            Toast.LENGTH_SHORT
                        ).show()
                        authError =
                            "No credentials found, Please make login with email and password"
                    }
                },
                onError = { error ->
                    authError = error
                    Toast.makeText(context, "Biometric auth failed", Toast.LENGTH_SHORT).show()
                },
                isLoading = { state ->
                    isLoading = state
                }
            )

            if (biometricHelper.isBiometricAvailable()) {
                Log.d("BiometricScreen", "Launching biometric prompt...")
                biometricHelper.showBiometricPrompt()
            } else {
                authError = "Biometric not available or not enrolled"
            }
        } ?: run {
            authError = "Invalid activity context"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading == true) CircularProgressIndicator()
        Text("Please authenticate using biometrics")

        authError?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, color = Color.Red)
        }
    }
}
