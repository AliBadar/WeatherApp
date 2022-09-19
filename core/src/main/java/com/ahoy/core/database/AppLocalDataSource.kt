package com.ahoy.core.database

import com.ahoy.core.responses.current_weather.CurrentWeather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLocalDataSource @Inject constructor(private val appDao: AppDao) {

    suspend fun addFavCityWeather(currentWeather: CurrentWeather) = appDao.addCurrentWeather(currentWeather)

    suspend fun getFavCitiesWeather() = appDao.getFavCitiesWeather()

}