package com.shahe.basiclearning.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object CoinListScreen: Screen("coin_list_screen")
    object CoinDetailScreen: Screen("coin_detail_screen")
    object NewsListScreen: Screen("news_list_screen")
    object WeatherScreen: Screen("weather_screen")
    object Login : Screen("login")
    object Splash : Screen("splash")
}
sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Coins : BottomNavScreen("coin_list_screen", "Coins", Icons.Default.Info)
    object News : BottomNavScreen("news_list_screen", "News", Icons.Default.Search)
    object Weather : BottomNavScreen("weather_screen", "Weather", Icons.Default.Place)
}