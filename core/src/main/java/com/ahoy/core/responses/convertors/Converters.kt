package com.ahoy.core.responses.convertors

import androidx.room.TypeConverter
import com.ahoy.core.responses.current_weather.Weather
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(value: List<Weather>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Weather>::class.java).toList()
}