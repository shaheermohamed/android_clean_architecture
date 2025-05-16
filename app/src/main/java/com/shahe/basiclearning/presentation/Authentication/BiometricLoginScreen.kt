package com.shahe.basiclearning.presentation.Authentication

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.shahe.basiclearning.common.BiometricHelper
import com.shahe.basiclearning.common.DataStoreManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun BiometricLoginScreen(dataStoreManager: DataStoreManager, auth: FirebaseAuth) {
    val context = LocalContext.current
    val biometricHelper = remember {
        BiometricHelper(
            context,
            onSuccess = {
                // After biometric success, sign in with Firebase
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    Toast.makeText(context, "Welcome ${user.displayName}", Toast.LENGTH_SHORT).show()
                    // Proceed to main content
                } else {
                    Toast.makeText(context, "Firebase user is not authenticated", Toast.LENGTH_SHORT).show()
                }
            },
            onFailure = {
                // Handle failure
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        )
    }
    val coroutineScope = rememberCoroutineScope()
    var isBiometricEnabled by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        // Collect the DataStore preference for biometric enablement
        dataStoreManager.isBiometricEnabled.collect {
            isBiometricEnabled = it
        }
    }

    // Handle the Biometric Login screen
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Biometric Login")
        Spacer(modifier = Modifier.height(16.dp))

        // Enable or disable biometric login via toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Enable Biometric Login")
            Switch(checked = isBiometricEnabled, onCheckedChange = { checked ->
                coroutineScope.launch {
                        dataStoreManager.setBiometricEnabled(checked)
                        if (checked && biometricHelper.isBiometricAvailable()) {
                            biometricHelper.showBiometricPrompt()
                        }
                    }
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (isBiometricEnabled && biometricHelper.isBiometricAvailable()) {
                biometricHelper.showBiometricPrompt()
            } else {
                Toast.makeText(context, "Biometric authentication not available or disabled", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Authenticate via Biometric")
        }
    }
}
