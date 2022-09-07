package com.ahoy.weatherapp.feature.weather.domain.usecase

import com.ahoy.weatherapp.feature.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForeCastWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke (lat: String,
                                 lon: String,
                                 q: String) = weatherRepository.getForeCastWeather(lat, lon, q)
}