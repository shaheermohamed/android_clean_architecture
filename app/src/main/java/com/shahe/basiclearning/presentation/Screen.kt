package com.shahe.basiclearning.presentation

sealed class Screen(val route: String) {
    object CoinListScreen: Screen("coin_list_screen")
    object CoinDetailScreen: Screen("coin_detail_screen")
    object NewsListScreen: Screen("news_list_screen")
    object WeatherScreen: Screen("weather_screen")
    object Login : Screen("login")
    object Splash : Screen("splash")
}