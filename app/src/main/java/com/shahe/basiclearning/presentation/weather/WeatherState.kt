package com.shahe.basiclearning.presentation.weather

import com.shahe.basiclearning.domain.model.WeatherInfo

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: WeatherInfo? = WeatherInfo(
        city = "No Data Available",
        description = "No Data Available",
        temperature = 0.0,
        temperatureFeelsLike = 0.0,
        wind = 0.0,
        coordinates = null
    ),
    val error: String = ""
)
