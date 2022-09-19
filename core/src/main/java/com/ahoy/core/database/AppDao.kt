package com.ahoy.core.database

import androidx.room.*
import com.ahoy.core.responses.current_weather.CurrentWeather

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrentWeather(currentWeather: CurrentWeather)

    @Query("SELECT * FROM weather")
    suspend fun getFavCitiesWeather(): List<CurrentWeather>
}