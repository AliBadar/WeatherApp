package com.ahoy.weatherapp.feature.weather.presentation.ui.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahoy.core.mapper.WeatherMapper
import com.ahoy.core.uistates.favcity.FavCitiesMainUiState
import com.ahoy.core.uistates.favcity.FavContent
import com.ahoy.core.uistates.favcity.LoadingFavState
import com.ahoy.domain.usecase.GetFavCitiesWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

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