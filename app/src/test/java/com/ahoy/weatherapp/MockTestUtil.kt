
package com.ahoy.weatherapp


import com.ahoy.core.network.Resource
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.current_weather.Main
import com.ahoy.core.responses.current_weather.Wind
import com.ahoy.core.responses.forecast.ForeCastList
import com.ahoy.core.responses.forecast.ForecastWeather
import com.ahoy.core.uistates.CurrentWeatherUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockTestUtil {
    companion object {

        fun createCurrentWeather(): Flow<Resource<CurrentWeather>> = flow  {
            val currentWeather = CurrentWeather(
                name = "Africa",
                id = 6255146,
                wind = Wind(speed = 1.52),
                main = Main(temp = 293.68, humidity = 98),
            )

            emit(Resource.success(currentWeather))
        }

        fun getCurrentWeatherUIState(): CurrentWeatherUIState {
            return CurrentWeatherUIState(
                name = "Dubai",
                temp = 22,
                wind = 92,
                humidity = 12,
            )
        }

        fun createForeCastWeather(): Flow<Resource<ForecastWeather>> = flow {
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
            var forecast = ForecastWeather(
                list =  arrList)
            emit(Resource.success(forecast))
        }

//        fun getMapEntitxy(type: CurrentWeather = createCurrentWeather()): CurrentWeatherUIState {
//            return CurrentWeatherUIState(
//                temp = type.main?.temp?.toInt(),
//                humidity = type.main?.humidity,
//                wind = type.wind?.speed?.toInt(),
//                name = type.name.toString(),
//                )
//        }
    }
}
