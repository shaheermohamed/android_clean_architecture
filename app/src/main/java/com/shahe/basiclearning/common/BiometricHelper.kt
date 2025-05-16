package com.shahe.basiclearning.common

import android.app.Activity
import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

class BiometricHelper(
    private val context: Context,
    private val onSuccess: () -> Unit,
    private val onFailure: (String) -> Unit
) {

    // Method to show the biometric prompt
    fun showBiometricPrompt() {
        val executor: Executor = ContextCompat.getMainExecutor(context)

        // Check if the context is an instance of FragmentActivity
        if (context is FragmentActivity) {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Authenticate using your fingerprint or face")
                .setNegativeButtonText("Cancel")
                .build()

            val biometricPrompt = BiometricPrompt(
                context, // Pass FragmentActivity here
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        onSuccess()  // Call success callback
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        onFailure("Authentication failed. Try again.")
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        onFailure("Authentication error: $errString")
                    }
                })

            biometricPrompt.authenticate(promptInfo)
        } else {
            onFailure("Biometric authentication is not supported on this device.")
        }
    }

    // Check if biometric authentication is available
    fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS
    }
}