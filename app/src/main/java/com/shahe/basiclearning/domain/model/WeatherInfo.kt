package com.shahe.basiclearning.domain.model

import com.shahe.basiclearning.data.remote.dto.Coordinates

data class WeatherInfo(
    val city: String,
    val temperature: Double,
    val temperatureFeelsLike: Double,
    val description: String,
    val coordinates: Coordinates?,
    val wind: Double,
)
