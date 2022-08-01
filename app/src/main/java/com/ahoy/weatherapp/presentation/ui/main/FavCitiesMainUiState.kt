package com.ahoy.weatherapp.presentation.ui.main

import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather

sealed class FavCitiesMainUiState

object LoadingFavState : FavCitiesMainUiState()
data class FavContent(val favCitiesUIState: List<CurrentWeatherUIState>) : FavCitiesMainUiState()
