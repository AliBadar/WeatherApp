package com.ahoy.weatherapp.utils

import androidx.room.TypeConverter
import com.ahoy.weatherapp.data.models.current_weather.Main
import com.ahoy.weatherapp.data.models.current_weather.Weather
import com.ahoy.weatherapp.data.models.current_weather.Wind
import com.google.gson.Gson
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun listToJson(value: List<Weather>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Weather>::class.java).toList()
}