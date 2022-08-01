package com.ahoy.weatherapp.domain.usecase

import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class SaveSearchCityWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke (currentWeather: CurrentWeather) = weatherRepository.saveFavCityWeather(currentWeather)
}