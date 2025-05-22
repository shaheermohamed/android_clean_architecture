package com.shahe.basiclearning.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shahe.basiclearning.presentation.Authentication.AuthScreen
import com.shahe.basiclearning.presentation.Authentication.BiometricScreen
import com.shahe.basiclearning.presentation.Authentication.RegisterScreen
import com.shahe.basiclearning.presentation.Screen
import com.shahe.basiclearning.presentation.coin_detail.CoinDetailScreen
import com.shahe.basiclearning.presentation.coin_list.CoinListScreen
import com.shahe.basiclearning.presentation.news_list.NewsListScreen
import com.shahe.basiclearning.presentation.splash.SplashScreen
import com.shahe.basiclearning.presentation.weather.WeatherScreen

@Composable
fun NavigationHost(navController: NavHostController) {
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
            CoinDetailScreen(navController)
        }
        composable(
            route = Screen.NewsListScreen.route
        ) {
            NewsListScreen(navController)
        }
        composable(
            route = Screen.WeatherScreen.route
        ) {
            WeatherScreen(navController)
        }
        composable(
            route = Screen.Login.route
        ) {
            AuthScreen(navController = navController)
        }
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.BioMetric.route) { BiometricScreen(navController) }
    }
}