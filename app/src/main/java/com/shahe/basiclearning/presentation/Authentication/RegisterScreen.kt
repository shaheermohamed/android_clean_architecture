package com.shahe.basiclearning.presentation.Authentication

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahe.basiclearning.domain.model.AuthResult
import com.shahe.basiclearning.presentation.Screen

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authResult = viewModel.authResult
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Welcome, Please Register")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Register")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it.trim() },
                label = { Text("Email") })
            OutlinedTextField(
                value = password,
                onValueChange = { password = it.trim() },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Row {
                Button(onClick = { viewModel.register(email, password) }) {
                    Text("Register")
                }
            }
            Spacer(Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already registered user, then!")
                TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                    Text("Login")
                }
            }

            when (authResult) {
                is AuthResult.Loading -> androidx.compose.material.CircularProgressIndicator()
                is AuthResult.Error -> Text(authResult.message, color = Color.Red)
                is AuthResult.Success -> {
                    val user = authResult.user
                    // ✅ You now have full access to FirebaseUser here
                    Log.d("FirebaseUser", "UID: ${user.uid}")
                    Log.d("FirebaseUser", "Email: ${user.email}")
                    Log.d("FirebaseUser", "DisplayName: ${user.displayName}")
                    Log.d("FirebaseUser", "Phone: ${user.phoneNumber}")
                    Log.d("FirebaseUser", "Provider: ${user.providerId}")

                    // Show on UI (Optional)
                    Text("Welcome: ${user.email}")
                    Text("UID: ${user.uid}")

                    Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                    LaunchedEffect(Unit) { navController.navigate(Screen.Login.route) }

                }

                null -> {}
            }
        }
    }
}