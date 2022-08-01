package com.ahoy.weatherapp.data.models.current_weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ahoy.weatherapp.utils.Converters
import com.ahoy.weatherapp.utils.MainConverters
import com.ahoy.weatherapp.utils.WindConverters
import com.google.gson.annotations.SerializedName


@Entity(tableName = "weather")
data class CurrentWeather (
  @ColumnInfo(name = "weather")
  @SerializedName("weather"    )
  @TypeConverters(Converters::class)
  var weather    : List<Weather> = mutableListOf(),

  @ColumnInfo(name = "main")
  @TypeConverters(MainConverters::class)
  @SerializedName("main"       ) var main       : Main?              = Main(),

  @ColumnInfo(name = "visibility")
  @SerializedName("visibility" ) var visibility : Int?               = null,

  @ColumnInfo(name = "wind")
  @TypeConverters(WindConverters::class)
  @SerializedName("wind"       ) var wind       : Wind?              = Wind(),


  @ColumnInfo(name = "dt")
  @SerializedName("dt"         ) var dt         : Long?               = null,

  @ColumnInfo(name = "timezone")
  @SerializedName("timezone"   ) var timezone   : Int?               = null,

  @PrimaryKey
  @ColumnInfo(name = "id")
  @SerializedName("id"         ) var id         : Int?               = null,

  @ColumnInfo(name = "name")
  @SerializedName("name"       ) var name       : String?            = null,

  @ColumnInfo(name = "cod")
  @SerializedName("cod"        ) var cod        : Int?               = null

)