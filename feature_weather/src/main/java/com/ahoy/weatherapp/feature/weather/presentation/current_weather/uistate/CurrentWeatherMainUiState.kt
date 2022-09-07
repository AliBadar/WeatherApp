package com.ahoy.weatherapp.feature.weather.presentation.current_weather.uistate

import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather

sealed class MainUiState

object LoadingState : MainUiState()
data class Content(val currentWeatherUIState: CurrentWeatherUIState) : MainUiState()
class ErrorState(val message: String) : MainUiState()
