package com.shahe.basiclearning.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("name")
    val city: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("coord")
    val coordinates: Coordinates,
    @SerializedName("wind")
    val wind: Wind

)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double
)
data class Weather(val description: String)
data class Coordinates(val lon: Double, val lat: Double)
data class Wind(val speed: Double)