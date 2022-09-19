package com.ahoy.core.responses.convertors

import androidx.room.TypeConverter
import com.ahoy.core.responses.current_weather.Main
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