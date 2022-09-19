package com.ahoy.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahoy.core.responses.convertors.Converters
import com.ahoy.core.responses.convertors.MainConverters
import com.ahoy.core.responses.convertors.WindConverters
import com.ahoy.core.responses.current_weather.CurrentWeather

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