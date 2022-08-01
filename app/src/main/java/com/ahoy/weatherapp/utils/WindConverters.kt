package com.ahoy.weatherapp.utils

import androidx.room.TypeConverter
import com.ahoy.weatherapp.data.models.current_weather.Main
import com.ahoy.weatherapp.data.models.current_weather.Wind
import org.json.JSONObject

class WindConverters {

    @TypeConverter
    fun fromWindSource(wind: Wind): String {
        return JSONObject().apply {
            put("speed", wind.speed)
            put("deg", wind.deg)
        }.toString()
    }

    @TypeConverter
    fun toWindSource(source: String): Wind {
        val json = JSONObject(source)
        return Wind(json.getDouble("speed"), json.getInt("deg"))
    }
}