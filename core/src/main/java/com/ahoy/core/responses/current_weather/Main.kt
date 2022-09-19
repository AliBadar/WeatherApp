package com.ahoy.core.responses.current_weather

import com.google.gson.annotations.SerializedName


data class Main (

  @SerializedName("temp"       ) var temp      : Double? = null,
  @SerializedName("humidity"   ) var humidity  : Int?    = null

)