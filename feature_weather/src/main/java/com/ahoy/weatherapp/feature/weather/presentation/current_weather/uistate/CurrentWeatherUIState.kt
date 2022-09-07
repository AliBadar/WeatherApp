package com.ahoy.weatherapp.feature.weather.presentation.current_weather.uistate

data class CurrentWeatherUIState (

  var temp       : Int?            = null,
  var humidity : Int?               = null,
  var wind       : Int?              = null,
  var date       : String,
  var name       : String,
  var iconCode       : String,

)