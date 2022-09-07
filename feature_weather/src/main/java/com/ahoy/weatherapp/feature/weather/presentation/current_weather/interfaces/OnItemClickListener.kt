package com.ahoy.weatherapp.feature.weather.presentation.current_weather.interfaces

import com.ahoy.weatherapp.feature.weather.presentation.current_weather.uistate.CurrentWeatherUIState


interface OnItemClickListener {
    fun onItemClick(currentWeatherUIState: CurrentWeatherUIState)
}