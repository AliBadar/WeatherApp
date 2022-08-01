package com.ahoy.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.weatherapp.utils.Converters
import com.ahoy.weatherapp.utils.MainConverters
import com.ahoy.weatherapp.utils.WindConverters

/**
 * SQLite Database for storing the articles.
 */
@Database(
    entities = [CurrentWeather::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class, MainConverters::class, WindConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citiesDao(): AppDao
}