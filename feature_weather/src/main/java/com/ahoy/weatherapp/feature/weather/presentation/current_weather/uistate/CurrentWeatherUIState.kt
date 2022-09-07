package com.ahoy.weatherapp.presentation.ui.main

import com.google.gson.annotations.SerializedName


data class CurrentWeatherUIState (

  var temp       : Int?            = null,
  var humidity : Int?               = null,
  var wind       : Int?              = null,
  var date       : String,
  var name       : String,
  var iconCode       : String,

)