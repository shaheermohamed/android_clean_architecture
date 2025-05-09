package com.shahe.basiclearning.presentation.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahe.basiclearning.common.Resource
import com.shahe.basiclearning.domain.model.WeatherInfo
import com.shahe.basiclearning.domain.use_case.get_weather.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {
    var state by mutableStateOf<WeatherState?>(
        WeatherState(
            isLoading = false,
            weather = WeatherInfo(
                city = "",
                description = "",
                temperature = 0.0,
                temperatureFeelsLike = 0.0,
                coordinates = null,
                wind = 0.0
            ),
            error = ""
        )
    )

//    init {
//        fetchWeather(city = "Sharjah")
//    }

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            getWeatherUseCase.getWeatherByCity(city).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = WeatherState(isLoading = true)
                    }

                    is Resource.Success -> {
                        println("getWeatherUseCase == ${result.data}")
                        state = WeatherState(weather = result.data)
                    }

                    is Resource.Error -> {
                        println("getWeatherUseCase == ${result.message}")
                        state = WeatherState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }
                }
            }
        }
    }
}