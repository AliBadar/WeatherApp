package com.ahoy.weatherapp.data.models.forecast

import com.google.gson.annotations.SerializedName


data class Sys (

  @SerializedName("pod" ) var pod : String? = null

)