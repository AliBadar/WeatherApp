package com.ahoy.data

import com.ahoy.core.network.Resource
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.current_weather.Main
import com.ahoy.core.responses.current_weather.Wind
import com.ahoy.core.responses.forecast.City
import com.ahoy.core.responses.forecast.ForeCastList
import com.ahoy.core.responses.forecast.ForecastWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteData {

    companion object {
        fun createCurrentWeather(): Resource<CurrentWeather> {
            val currentWeather = CurrentWeather(
                name = "Africa",
                id = 6255146,
                wind = Wind(speed = 1.52),
                main = Main(temp = 293.68, humidity = 98),
            )

            return (Resource.success(currentWeather))
        }

        fun createForeCastWeather(): Resource<ForecastWeather> {
            val forecastList = mutableListOf<ForeCastList>()
            repeat(10){
                forecastList.add(ForeCastList())
            }
            val currentWeather = ForecastWeather(
                cod = "123",
                city = City(id = 1, name = "Lahore"),
                list = forecastList
            )

            return (Resource.success(currentWeather))
        }
    }

}