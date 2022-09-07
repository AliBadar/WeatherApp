package com.ahoy.weatherapp.feature.weather.domain.repository

import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.weatherapp.data.models.forecast.ForecastWeather
import com.ahoy.weatherapp.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: String,
                                  lon: String,
                                  q: String): Flow<Resource<CurrentWeather>>

    suspend fun getForeCastWeather(lat: String,
                                  lon: String,
                                  q: String): Flow<Resource<ForecastWeather>>

    suspend fun saveFavCityWeather(currentWeather: CurrentWeather)

    suspend fun getFavCitiesWeather(): Flow<List<CurrentWeather>>
}