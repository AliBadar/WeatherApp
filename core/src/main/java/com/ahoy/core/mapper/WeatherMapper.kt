package com.ahoy.core.mapper

import com.ahoy.common.utils.DateUtility
import com.ahoy.common.utils.DateUtility.getDate
import com.ahoy.core.mapper.Mapper
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.forecast.ForeCastList
import com.ahoy.core.uistates.CurrentWeatherUIState
import com.ahoy.core.uistates.forecast.ForeCastContent
import com.ahoy.core.uistates.forecast.ForeCastWeatherUIState


import javax.inject.Inject

open class WeatherMapper  @Inject constructor()  : Mapper<CurrentWeatherUIState, CurrentWeather> {


    override fun mapToEntity(type: CurrentWeather): CurrentWeatherUIState {
        return CurrentWeatherUIState(
            temp = type.main?.temp?.toInt(),
            humidity = type.main?.humidity,
            wind = type.wind?.speed?.toInt(),
            date = getDate(type.dt),
            name = type.name.toString(),
//            iconCode = type.weather?.get(0)?.icon?.replace("n", "d").toString()

        )
    }
}
