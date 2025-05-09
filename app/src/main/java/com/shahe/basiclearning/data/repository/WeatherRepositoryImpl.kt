package com.shahe.basiclearning.data.repository

import com.shahe.basiclearning.common.Constants
import com.shahe.basiclearning.data.remote.dto.WeatherApiService
import com.shahe.basiclearning.domain.model.WeatherInfo
import com.shahe.basiclearning.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(city: String): WeatherInfo {
        val response = api.getWeather(city, Constants.WEATHER_API_KEY)
        return WeatherInfo(
            city = response.city,
            temperature = response.main.temp,
            temperatureFeelsLike = response.main.feelsLike,
            description = response.weather.first().description,
            coordinates = response.coordinates,
            wind = response.wind.speed
        )
    }
}