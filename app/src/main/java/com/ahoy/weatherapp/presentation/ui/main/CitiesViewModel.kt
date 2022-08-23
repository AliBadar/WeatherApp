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
class CitiesViewModel @Inject constructor(
    private val getFavCitiesWeatherUseCase: GetFavCitiesWeatherUseCase,
    private val weatherMapper: WeatherMapper,
) : ViewModel() {

    init {
        getFavCities()
    }

    private val _favCitiesData = MutableStateFlow<FavCitiesMainUiState>(LoadingFavState)
    val favCitiesData = _favCitiesData.asStateFlow()


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

}