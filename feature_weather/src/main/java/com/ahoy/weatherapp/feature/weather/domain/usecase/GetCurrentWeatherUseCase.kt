package com.ahoy.weatherapp.domain.usecase

import com.ahoy.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke (lat: String,
                                 lon: String,
                                 q: String) = weatherRepository.getCurrentWeather(lat, lon, q)
}