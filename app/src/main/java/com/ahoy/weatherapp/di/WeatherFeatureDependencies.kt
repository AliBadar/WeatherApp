package com.ahoy.weatherapp.di

import com.ahoy.domain.repository.WeatherRepository
import com.ahoy.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.domain.usecase.GetFavCitiesWeatherUseCase
import com.ahoy.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.domain.usecase.SaveSearchCityWeatherUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WeatherFeatureDependencies {
    fun getCurrentWeatherUseCase(): GetCurrentWeatherUseCase
    fun getForeCastWeatherUseCase(): GetForeCastWeatherUseCase
    fun saveSearchCityWeatherUseCase(): SaveSearchCityWeatherUseCase
    fun getFavCitiesWeatherUseCase(): GetFavCitiesWeatherUseCase
}