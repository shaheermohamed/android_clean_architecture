package com.shahe.basiclearning.domain.repository

import com.shahe.basiclearning.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeather(city:String): WeatherInfo
}