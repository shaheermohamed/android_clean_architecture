package com.shahe.basiclearning.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shahe.basiclearning.common.DataStoreManager
import com.shahe.basiclearning.common.EncryptedPrefs
import com.shahe.basiclearning.presentation.Authentication.AuthScreen
import com.shahe.basiclearning.presentation.Authentication.BiometricScreen
import com.shahe.basiclearning.presentation.Authentication.RegisterScreen
import com.shahe.basiclearning.presentation.coin_detail.CoinDetailScreen
import com.shahe.basiclearning.presentation.coin_list.CoinListScreen
import com.shahe.basiclearning.presentation.news_list.NewsListScreen
import com.shahe.basiclearning.presentation.splash.SplashScreen
import com.shahe.basiclearning.presentation.ui.theme.BasicLearningTheme
import com.shahe.basiclearning.presentation.weather.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var dataStoreManager: DataStoreManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(applicationContext)
        // Init EncryptedPrefs
        EncryptedPrefs.init(applicationContext)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    BasicLearningTheme {
                        Surface(color = MaterialTheme.colors.background) {
                            val navController = rememberNavController()
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
                    }
                }
            })
    }
}
