package com.ahoy.data.repository

import androidx.annotation.WorkerThread
import com.ahoy.core.database.AppLocalDataSource
import com.ahoy.core.network.Resource
import com.ahoy.core.network.sources.ApiRemoteDataSource
import com.ahoy.domain.repository.WeatherRepository
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.forecast.ForecastWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(private val apiRemoteDataSource: ApiRemoteDataSource, private val appLocalDataSource: AppLocalDataSource):
    WeatherRepository {

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