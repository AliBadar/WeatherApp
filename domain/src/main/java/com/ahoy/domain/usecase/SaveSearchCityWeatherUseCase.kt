package com.ahoy.domain.usecase


import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.domain.repository.WeatherRepository
import javax.inject.Inject

class SaveSearchCityWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke (currentWeather: CurrentWeather) = weatherRepository.saveFavCityWeather(currentWeather)
}