package com.ahoy.weatherapp.data.database

import androidx.room.*
import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrentWeather(currentWeather: CurrentWeather)

    @Query("SELECT * FROM weather")
    suspend fun getFavCitiesWeather(): List<CurrentWeather>
}