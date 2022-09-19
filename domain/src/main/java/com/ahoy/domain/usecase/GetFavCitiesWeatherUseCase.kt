package com.ahoy.domain.usecase

import com.ahoy.domain.repository.WeatherRepository
import javax.inject.Inject

class GetFavCitiesWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke () = weatherRepository.getFavCitiesWeather()
}