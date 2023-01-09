package com.ahoy.core.uistates


sealed class MainUiState

object LoadingState : MainUiState()
data class Content(val currentWeatherUIState: CurrentWeatherUIState) : MainUiState()
data class ErrorState(val message: String) : MainUiState()
