package com.ahoy.weatherapp.feature.weather.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahoy.common.utils.DateUtility
import com.ahoy.core.mapper.ForeCastWeatherMapper
import com.ahoy.core.mapper.WeatherMapper
import com.ahoy.core.network.Resource
import com.ahoy.core.uistates.*
import com.ahoy.core.uistates.favcity.FavCitiesMainUiState
import com.ahoy.core.uistates.favcity.FavContent
import com.ahoy.core.uistates.favcity.LoadingFavState
import com.ahoy.core.uistates.forecast.*
import com.ahoy.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.domain.usecase.GetFavCitiesWeatherUseCase
import com.ahoy.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.domain.usecase.SaveSearchCityWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForeCastWeatherUseCase: GetForeCastWeatherUseCase,
    private val saveSearchCityWeatherUseCase: SaveSearchCityWeatherUseCase,
    private val getFavCitiesWeatherUseCase: GetFavCitiesWeatherUseCase,
    private val weatherMapper: WeatherMapper,
    private val foreCastWeatherMapper: ForeCastWeatherMapper,
) : ViewModel() {

    private val _currentWeatherData = MutableStateFlow<MainUiState>(LoadingState)
    val currentWeatherData = _currentWeatherData.asStateFlow()

    private val _foreCastData = MutableStateFlow<ForeCastMainUiState>(ForeCastLoadingState)
    val foreCastData = _foreCastData.asStateFlow()
//
    private val _favCitiesData = MutableStateFlow<FavCitiesMainUiState>(LoadingFavState)
    val favCitiesData = _favCitiesData.asStateFlow()

    fun getCurrentWeather(lat: String = "", lon: String = "", q: String = "", saveIntoDB: Boolean = false){
        viewModelScope.launch {
            _currentWeatherData.value = LoadingState

            getCurrentWeatherUseCase(lat,lon,q).collect { resource ->
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
                            _foreCastData.value = foreCastWeatherMapper.mapToEntity(data.list)
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

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    suspend fun loadMessage(text: String): String {
        viewModelScope.launch {
            _message.value = "Greetings!"
        }

        return text
    }
}

