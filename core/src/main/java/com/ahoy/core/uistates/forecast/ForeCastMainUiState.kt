package com.ahoy.core.uistates.forecast


sealed class ForeCastMainUiState

object ForeCastLoadingState : ForeCastMainUiState()
data class ForeCastContent(val foreCastListData: List<ForeCastWeatherUIState>) : ForeCastMainUiState()
class ForeCastErrorState(val message: String) : ForeCastMainUiState()
