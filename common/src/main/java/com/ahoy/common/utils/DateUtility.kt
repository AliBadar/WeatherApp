package com.ahoy.common.utils

import android.text.format.DateFormat
import java.util.*

object DateUtility {

    fun getDate(timeStamp: Long?) : String{
        timeStamp?.let {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = timeStamp.times(1000L)
            val date = DateFormat.format("E, d MMM yyyy",calendar).toString()
            return date.toString()
        }
        return ""
    }

    fun getForeCastingDate(timeStamp: Long?) : String{
        timeStamp?.let {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = timeStamp.times(1000L)
            val date = DateFormat.format("hh a",calendar).toString()
            return date.toString()
        }
        return ""
    }
}