package com.ahoy.weatherapp.data.repository

import androidx.annotation.WorkerThread
import com.ahoy.weatherapp.data.database.AppLocalDataSource
import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.weatherapp.data.models.forecast.ForecastWeather
import com.ahoy.weatherapp.data.remote.ApiRemoteDataSource
import com.ahoy.weatherapp.data.remote.Resource
import com.ahoy.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

class WeatherRepositoryImp @Inject constructor(private val apiRemoteDataSource: ApiRemoteDataSource, private val appLocalDataSource: AppLocalDataSource): WeatherRepository{

    @WorkerThread
    override suspend fun getCurrentWeather(lat: String,
                                           lon: String,
                                           q: String): Flow<Resource<CurrentWeather>> {
        return flow {
            apiRemoteDataSource.getCurrentWeather(lat, lon,q).apply {
                emit(this)
            }
        }
    }

    @WorkerThread
    override suspend fun getForeCastWeather(lat: String,
                                           lon: String,
                                           q: String): Flow<Resource<ForecastWeather>> {
        return flow {
            apiRemoteDataSource.getForeCastWeather(lat, lon,q).apply {
                emit(this)
            }
        }
    }

    override suspend fun saveFavCityWeather(currentWeather: CurrentWeather) {
        appLocalDataSource.addFavCityWeather(currentWeather)
    }

    override suspend fun getFavCitiesWeather(): Flow<List<CurrentWeather>> {
        return flow {
            appLocalDataSource.getFavCitiesWeather().apply {
                emit(this)
            }
        }

    }

}