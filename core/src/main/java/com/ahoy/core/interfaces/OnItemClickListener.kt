package com.ahoy.core.interfaces

import com.ahoy.core.uistates.CurrentWeatherUIState

interface OnItemClickListener {
    fun onItemClick(currentWeatherUIState: CurrentWeatherUIState)
}