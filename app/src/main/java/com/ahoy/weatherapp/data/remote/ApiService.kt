package com.ahoy.weatherapp.data.remote

import com.ahoy.weatherapp.BuildConfig
import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.weatherapp.data.models.forecast.ForecastWeather
import com.ahoy.weatherapp.utils.AppConstants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(RestConfig.GET_CURRENT_WEATHER)
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("q") q: String,
        @Query("units") units: String = AppConstants.WEATHER_UNIT,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<CurrentWeather>

    @GET(RestConfig.GET_FORECAST)
    suspend fun getForeCastWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("q") q: String?,
        @Query("units") units: String = AppConstants.WEATHER_UNIT,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<ForecastWeather>
}