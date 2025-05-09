package com.shahe.basiclearning.domain.use_case.get_weather

import com.shahe.basiclearning.common.Resource
import com.shahe.basiclearning.domain.model.WeatherInfo
import com.shahe.basiclearning.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
fun getWeatherByCity(city: String): Flow<Resource<WeatherInfo>> = flow {
    try {
        emit(Resource.Loading())
        val response = repository.getWeather (city = city)
        val weather = response // map API response to domain model
        emit(Resource.Success(weather))
    } catch (e: HttpException) {
        emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
    } catch (e: java.io.IOException) {
        emit(Resource.Error("Couldn't reach server. Check your internet connection."))
    }
}
}