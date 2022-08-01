package com.ahoy.weatherapp.data.models.current_weather

import com.google.gson.annotations.SerializedName


data class Clouds (

  @SerializedName("all" ) var all : Int? = null

)