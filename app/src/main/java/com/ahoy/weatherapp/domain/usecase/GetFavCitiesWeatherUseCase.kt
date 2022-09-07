//package com.ahoy.weatherapp.domain.usecase
//
//import com.ahoy.weatherapp.domain.repository.WeatherRepository
//import javax.inject.Inject
//
//class GetFavCitiesWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
//
//    suspend operator fun invoke () = weatherRepository.getFavCitiesWeather()
//}