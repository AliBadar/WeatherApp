package com.ahoy.weatherapp.data.mapper

import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.weatherapp.presentation.ui.main.CurrentWeatherUIState
import com.ahoy.weatherapp.utils.DateUtility.getDate
import javax.inject.Inject

class WeatherMapper  @Inject constructor()  : Mapper<CurrentWeatherUIState, CurrentWeather> {


    override fun mapToEntity(type: CurrentWeather): CurrentWeatherUIState {
        return CurrentWeatherUIState(
            temp = type.main?.temp?.toInt(),
            humidity = type.main?.humidity,
            wind = type.wind?.speed?.toInt(),
            date = getDate(type.dt),
            name = type.name.toString(),
            iconCode = type.weather?.get(0)?.icon?.replace("n", "d").toString()

        )
    }
}
