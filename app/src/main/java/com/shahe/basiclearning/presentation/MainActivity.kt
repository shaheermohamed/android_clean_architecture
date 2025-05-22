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
import com.shahe.basiclearning.common.NavigationHost
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
                            NavigationHost(navController)
                        }
                    }
                }
            })
    }
}
