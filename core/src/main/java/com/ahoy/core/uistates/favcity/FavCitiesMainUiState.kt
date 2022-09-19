package com.ahoy.core.uistates.favcity

import com.ahoy.core.uistates.CurrentWeatherUIState


sealed class FavCitiesMainUiState

object LoadingFavState : FavCitiesMainUiState()
data class FavContent(val favCitiesUIState: List<CurrentWeatherUIState>) : FavCitiesMainUiState()
