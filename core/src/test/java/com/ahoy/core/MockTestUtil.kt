
package com.ahoy.core


import com.ahoy.common.utils.DateUtility
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.current_weather.Main
import com.ahoy.core.responses.current_weather.Wind
import com.ahoy.core.responses.forecast.ForeCastList
import com.ahoy.core.responses.forecast.ForecastWeather
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

        fun getCurrentWeatherUIState(): CurrentWeatherUIState {
            return CurrentWeatherUIState(
                name = "Dubai",
                temp = 22,
                wind = 92,
                humidity = 12,
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
                )

            }
            var arrList =  mutableListOf<ForeCastList>()
            arrList.addAll(foreCastList)

            return ForecastWeather(
                list =  arrList)
        }

        fun getMapEntity(type: CurrentWeather = createCurrentWeather()): CurrentWeatherUIState {
            return CurrentWeatherUIState(
                temp = type.main?.temp?.toInt(),
                humidity = type.main?.humidity,
                wind = type.wind?.speed?.toInt(),
                name = type.name.toString(),
                )
        }

        fun getForeCaseMapEntity(type: ForecastWeather = createForeCastWeather()): ForeCastContent {
            return ForeCastContent(type.list.map { forecast ->
                ForeCastWeatherUIState(
                    temp = forecast.main?.temp?.toInt(),
                    humidity = forecast.main?.humidity,
                    wind = forecast.wind?.speed,
                )

            })
        }
    }
}
