package com.ahoy.weatherapp.feature.weather.presentation.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahoy.weatherapp.feature.weather.data.mapper.WeatherMapper
import com.ahoy.weatherapp.data.remote.Resource
import com.ahoy.weatherapp.domain.usecase.SaveSearchCityWeatherUseCase
import com.ahoy.weatherapp.feature.weather.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.weatherapp.feature.weather.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.weatherapp.feature.weather.presentation.current_weather.forecast_uistate.*
import com.ahoy.weatherapp.feature.weather.presentation.current_weather.uistate.Content
import com.ahoy.weatherapp.feature.weather.presentation.current_weather.uistate.ErrorState
import com.ahoy.weatherapp.feature.weather.presentation.current_weather.uistate.LoadingState
import com.ahoy.weatherapp.feature.weather.presentation.current_weather.uistate.MainUiState
import com.ahoy.weatherapp.utils.DateUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForeCastWeatherUseCase: GetForeCastWeatherUseCase,
    private val saveSearchCityWeatherUseCase: SaveSearchCityWeatherUseCase,
    private val weatherMapper: WeatherMapper,
) : ViewModel() {

    private val _currentWeatherData = MutableStateFlow<MainUiState>(LoadingState)
    val currentWeatherData = _currentWeatherData.asStateFlow()

    private val _foreCastData = MutableStateFlow<ForeCastMainUiState>(ForeCastLoadingState)
    val foreCastData = _foreCastData.asStateFlow()

    fun getCurrentWeather(lat: String = "", lon: String = "", q: String = "", saveIntoDB: Boolean = false){
        viewModelScope.launch {
            _currentWeatherData.value = LoadingState

            getCurrentWeatherUseCase.invoke(lat,lon,q).collect { resource ->
                when(resource.status){
                    Resource.Status.SUCCESS -> {
                        var data = resource.data
                        data?.let {currentWeather ->
                            if (saveIntoDB){
                                saveSearchCityWeatherUseCase.invoke(currentWeather)
                            }
                        _currentWeatherData.value = Content(weatherMapper.mapToEntity(currentWeather))
                        }

                    }
                    Resource.Status.ERROR -> {
                        _currentWeatherData.value = ErrorState(resource.message.toString())
                    }
                    Resource.Status.LOADING -> {}
                }
            }
        }

    }

    fun getForeCast(lat: String = "", lon: String = "", q: String = ""){
        viewModelScope.launch {
            _foreCastData.value = ForeCastLoadingState

            getForeCastWeatherUseCase.invoke(lat,lon,q).collect { resource ->
                when(resource.status){
                    Resource.Status.SUCCESS -> {
                        var data = resource.data
                        data?.let {
                            _foreCastData.value = ForeCastContent(data.list.map { forecast ->
                                ForeCastWeatherUIState(temp = forecast.main?.temp?.toInt(),
                                    name = forecast.weather?.get(0)?.main.toString(),
                                    date = DateUtility.getForeCastingDate(forecast.dt),
                                    iconCode = forecast.weather?.get(0)?.icon?.replace("n", "d").toString()
                                  )

                            })
                        }
                    }
                    Resource.Status.ERROR -> {
                        _foreCastData.value = ForeCastErrorState(resource.message.toString())
                    }
                    Resource.Status.LOADING -> {}
                }
            }
        }

    }

}