package com.ahoy.core.responses.forecast

import com.google.gson.annotations.SerializedName


data class Weather (

  @SerializedName("id"          ) var id          : Int?    = null,
  @SerializedName("description" ) var description : String? = null,
  @SerializedName("icon"        ) var icon        : String? = null

)