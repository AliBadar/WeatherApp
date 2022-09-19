package com.ahoy.core.network

import com.ahoy.core.utils.AppConstants
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.forecast.ForecastWeather
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(RestConfig.GET_CURRENT_WEATHER)
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("q") q: String,
        @Query("units") units: String = AppConstants.WEATHER_UNIT,
        @Query("appid") appid: String = "0a419c1eb986d9cd21909e2cc58c8214"
    ): Response<CurrentWeather>

    @GET(RestConfig.GET_FORECAST)
    suspend fun getForeCastWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("q") q: String?,
        @Query("units") units: String = AppConstants.WEATHER_UNIT,
        @Query("appid") appid: String = "0a419c1eb986d9cd21909e2cc58c8214"
    ): Response<ForecastWeather>
}