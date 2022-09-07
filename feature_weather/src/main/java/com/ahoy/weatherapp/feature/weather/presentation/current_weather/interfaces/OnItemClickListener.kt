package com.ahoy.weatherapp.presentation.ui

import com.ahoy.weatherapp.presentation.ui.main.CurrentWeatherUIState


interface OnItemClickListener {
    fun onItemClick(currentWeatherUIState: CurrentWeatherUIState)
}