package com.ahoy.weatherapp.presentation.ui.main

import com.ahoy.weatherapp.data.models.forecast.ForeCastList


sealed class ForeCastMainUiState

object ForeCastLoadingState : ForeCastMainUiState()
data class ForeCastContent(val foreCastListData: List<ForeCastWeatherUIState>) : ForeCastMainUiState()
class ForeCastErrorState(val message: String) : ForeCastMainUiState()
