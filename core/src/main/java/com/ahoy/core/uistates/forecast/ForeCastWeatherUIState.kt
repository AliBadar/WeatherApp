package com.ahoy.core.uistates.forecast

import com.google.gson.annotations.SerializedName


data class ForeCastWeatherUIState (

  var temp       : Int?            = null,
  var humidity : Int?               = null,
  var wind       : Double?              = null,
  var date       : String? = null,
  var iconCode       : String? = null,

)