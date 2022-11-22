
package com.ahoy.weatherapp.feature.weather


import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.current_weather.Main
import com.ahoy.core.responses.current_weather.Wind
import com.ahoy.core.responses.forecast.ForeCastList
import com.ahoy.core.responses.forecast.ForecastWeather

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
                    wind = com.ahoy.core.responses.forecast.Wind(speed = 0.51)
                )

            }
            var arrList =  mutableListOf<ForeCastList>()
            arrList.addAll(foreCastList)

            return ForecastWeather(
                list =  arrList)
        }
    }
}
