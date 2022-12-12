package com.ahoy.weatherapp.feature.weather


import com.ahoy.common.utils.DateUtility
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.current_weather.Main
import com.ahoy.core.responses.current_weather.Wind
import com.ahoy.core.responses.forecast.ForeCastList
import com.ahoy.core.responses.forecast.ForecastWeather
import com.ahoy.core.responses.forecast.Weather
import com.ahoy.core.uistates.CurrentWeatherUIState
import com.ahoy.core.uistates.forecast.ForeCastContent
import com.ahoy.core.uistates.forecast.ForeCastWeatherUIState

class MockTestUtil {
    companion object {

        fun createCurrentWeather(): CurrentWeather {
            return CurrentWeather(
                name = "Africa",
                id = 6255146,
                wind = Wind(speed = 1.52),
                main = Main(temp = 293.68, humidity = 98),
            )
        }

        fun createForeCastWeather(): ForecastWeather {
            var foreCastList = (0 until 5).map {
                ForeCastList(
                    main = com.ahoy.core.responses.forecast.Main(
                        temp = 293.51,
                        humidity = 98
                    ),
                    wind = com.ahoy.core.responses.forecast.Wind(speed = 0.51),
                    weather = arrayListOf(Weather(icon = "wind")),
                )

            }
            var arrList = mutableListOf<ForeCastList>()
            arrList.addAll(foreCastList)

            return ForecastWeather(
                list = arrList
            )
        }

        fun getMapEntity(type: CurrentWeather = createCurrentWeather()): CurrentWeatherUIState {
            return CurrentWeatherUIState(
                temp = type.main?.temp?.toInt(),
                humidity = type.main?.humidity,
                wind = type.wind?.speed?.toInt(),
                date = DateUtility.getDate(type.dt),
                name = type.name.toString(),

                )
        }

        fun getForeCaseMapEntity(type: ForecastWeather = createForeCastWeather()): ForeCastContent {
            return ForeCastContent(type.list.map { forecast ->
                ForeCastWeatherUIState(
                    temp = forecast.main?.temp?.toInt(),
                    date = DateUtility.getForeCastingDate(forecast.dt),
                    iconCode = forecast.weather?.get(0)?.icon?.replace("n", "d").toString()
                )

            })
        }
    }

}
