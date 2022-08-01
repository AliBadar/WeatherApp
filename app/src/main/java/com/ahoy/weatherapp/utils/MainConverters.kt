package com.ahoy.weatherapp.utils

import androidx.room.TypeConverter
import com.ahoy.weatherapp.data.models.current_weather.Main
import com.ahoy.weatherapp.data.models.current_weather.Weather
import com.ahoy.weatherapp.data.models.current_weather.Wind
import com.google.gson.Gson
import org.json.JSONObject

class MainConverters {
    @TypeConverter
    fun fromMainSource(main: Main): String {
        return JSONObject().apply {
            put("temp", main.temp)
            put("humidity", main.humidity)
        }.toString()
    }

    @TypeConverter
    fun toMainSource(source: String): Main {
        val json = JSONObject(source)
        return Main(json.getDouble("temp"), json.getInt("humidity"))
    }
}