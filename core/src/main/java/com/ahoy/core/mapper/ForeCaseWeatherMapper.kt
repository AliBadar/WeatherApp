package com.ahoy.core.mapper

import com.ahoy.common.utils.DateUtility
import com.ahoy.core.responses.forecast.ForeCastList
import com.ahoy.core.uistates.forecast.ForeCastContent
import com.ahoy.core.uistates.forecast.ForeCastWeatherUIState
import javax.inject.Inject

open class ForeCastWeatherMapper  @Inject constructor()  : Mapper<ForeCastContent, List<ForeCastList>> {

    override fun mapToEntity(arrayList: List<ForeCastList>): ForeCastContent {
        return  ForeCastContent(arrayList.map { forecast ->
            ForeCastWeatherUIState(temp = forecast.main?.temp?.toInt(),
                date = DateUtility.getForeCastingDate(forecast.dt),
            )

        })
    }

    fun testFunctionCalled(){

    }
}