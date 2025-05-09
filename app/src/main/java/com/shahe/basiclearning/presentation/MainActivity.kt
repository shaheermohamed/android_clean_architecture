package com.shahe.basiclearning.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shahe.basiclearning.presentation.Authentication.AuthScreen
import com.shahe.basiclearning.presentation.Authentication.AuthViewModel
import com.shahe.basiclearning.presentation.coin_detail.CoinDetailScreen
import com.shahe.basiclearning.presentation.coin_list.CoinListScreen
import com.shahe.basiclearning.presentation.news_list.NewsListScreen
import com.shahe.basiclearning.presentation.splash.SplashScreen
import com.shahe.basiclearning.presentation.ui.theme.BasicLearningTheme
import com.shahe.basiclearning.presentation.weather.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicLearningTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val authViewModel: AuthViewModel = hiltViewModel()
                    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()
                    val navController = rememberNavController()
//                    LaunchedEffect(isLoggedIn.value) {
//                        if (isLoggedIn.value) {
//                            navController.navigate(Screen.CoinListScreen.route) {
//                                popUpTo(Screen.Login.route) { inclusive = true }
//                            }
//                        } else {
//                            navController.navigate(Screen.Login.route) {
//                                popUpTo(Screen.CoinListScreen.route) { inclusive = true }
//                            }
//                        }
//                    }
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route
                    ) {
                        composable(
                            route = Screen.CoinListScreen.route
                        ) {
                            CoinListScreen(navController)
                        }
                        composable(
                            route = Screen.CoinDetailScreen.route + "/{coinId}"
                        ) {
                            CoinDetailScreen()
                        }
                        composable(
                            route = Screen.NewsListScreen.route
                        ) {
                            NewsListScreen(navController)
                        }
                        composable(
                            route = Screen.WeatherScreen.route
                        ) {
                            WeatherScreen()
                        }
                        composable(
                            route = Screen.Login.route
                        ) {
                            AuthScreen(navController = navController)
                        }
                        composable(route = Screen.Splash.route) {
                            SplashScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
