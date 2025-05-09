package com.shahe.basiclearning.di

import com.shahe.basiclearning.common.Constants
import com.shahe.basiclearning.data.remote.dto.CoinPaprikaApi
import com.shahe.basiclearning.data.remote.dto.GNewsApi
import com.shahe.basiclearning.data.remote.dto.WeatherApiService
import com.shahe.basiclearning.data.repository.CoinRepositoryImpl
import com.shahe.basiclearning.data.repository.FirebaseAuthRepositoryImpl
import com.shahe.basiclearning.data.repository.NewsRepositoryImpl
import com.shahe.basiclearning.data.repository.WeatherRepositoryImpl
import com.shahe.basiclearning.domain.repository.CoinRepository
import com.shahe.basiclearning.domain.repository.NewsRepository
import com.shahe.basiclearning.domain.repository.WeatherRepository
import com.shahe.basiclearning.domain.use_case.auth_usecases.AuthUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePaprikaApi(): CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi): CoinRepository{
        return CoinRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGNewsApi(): GNewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_NEWS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GNewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: GNewsApi): NewsRepository{
        return NewsRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService): WeatherRepository{
        return WeatherRepositoryImpl(api)
    }

    @Provides
    fun provideAuthUseCases(): AuthUseCases = FirebaseAuthRepositoryImpl()
}