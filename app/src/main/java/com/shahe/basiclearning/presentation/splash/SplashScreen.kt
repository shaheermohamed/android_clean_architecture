package com.shahe.basiclearning.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahe.basiclearning.R
import com.shahe.basiclearning.presentation.Authentication.AuthViewModel
import com.shahe.basiclearning.presentation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    val isLoggedIn = viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(Unit) {
        delay(5000) // 2 sec splash
        viewModel.checkLoginStatus()
    }

    LaunchedEffect(isLoggedIn.value) {
        isLoggedIn.value.let { loggedIn ->
            navController.navigate(
                if (loggedIn) {
                    Screen.CoinListScreen.route
                } else {
                    Screen.Login.route
                }
            ) {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_playstore),
                contentDescription = "Image from drawable",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Clean Architecture", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }


}
