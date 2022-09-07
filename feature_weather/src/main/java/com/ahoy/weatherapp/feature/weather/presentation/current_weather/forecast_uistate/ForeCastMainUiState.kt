package com.ahoy.weatherapp.feature.weather.presentation.current_weather.forecast_uistate

import com.ahoy.weatherapp.data.models.forecast.ForeCastList


sealed class ForeCastMainUiState

object ForeCastLoadingState : ForeCastMainUiState()
data class ForeCastContent(val foreCastListData: List<ForeCastWeatherUIState>) : ForeCastMainUiState()
class ForeCastErrorState(val message: String) : ForeCastMainUiState()
