package com.ahoy.domain.repository

import com.ahoy.core.network.Resource
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.forecast.ForecastWeather
import kotlinx.coroutines.flow.Flow

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