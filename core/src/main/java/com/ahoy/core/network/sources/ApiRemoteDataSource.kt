package com.ahoy.core.network.sources

import com.ahoy.core.network.ApiService
import com.ahoy.core.network.BaseDataSource
import retrofit2.http.Query
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {
    suspend fun getCurrentWeather(lat: String,
                                  lon: String,
                                  q: String) = getResult {
        apiService.getCurrentWeather(lat, lon,q)
    }

    suspend fun getForeCastWeather(lat: String,
                                  lon: String,
                                  q: String) = getResult {
        apiService.getForeCastWeather(lat, lon,q)
    }
}