package com.ahoy.weatherapp.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahoy.weatherapp.BuildConfig
import com.ahoy.weatherapp.data.mapper.WeatherMapper
import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.weatherapp.data.remote.Resource
import com.ahoy.weatherapp.domain.repository.WeatherRepository
import com.ahoy.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.weatherapp.domain.usecase.GetFavCitiesWeatherUseCase
import com.ahoy.weatherapp.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.weatherapp.domain.usecase.SaveSearchCityWeatherUseCase
import com.ahoy.weatherapp.utils.DateUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForeCastWeatherUseCase: GetForeCastWeatherUseCase,
    private val saveSearchCityWeatherUseCase: SaveSearchCityWeatherUseCase,
    private val getFavCitiesWeatherUseCase: GetFavCitiesWeatherUseCase,
    private val weatherMapper: WeatherMapper,
) : ViewModel() {

    private val _currentWeatherData = MutableStateFlow<MainUiState>(LoadingState)
    val currentWeatherData = _currentWeatherData.asStateFlow()

    private val _foreCastData = MutableStateFlow<ForeCastMainUiState>(ForeCastLoadingState)
    val foreCastData = _foreCastData.asStateFlow()

    private val _favCitiesData = MutableStateFlow<FavCitiesMainUiState>(LoadingFavState)
    val favCitiesData = _favCitiesData.asStateFlow()

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

    fun getFavCities(){
        viewModelScope.launch {
            _favCitiesData.value = LoadingFavState

            getFavCitiesWeatherUseCase.invoke().collect { resource ->
                if (resource.isNotEmpty()){
                    _favCitiesData.value = FavContent(resource.map { weatherMapper.mapToEntity(it) })
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
                                ForeCastWeatherUIState(temp = forecast.main?.temp,
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