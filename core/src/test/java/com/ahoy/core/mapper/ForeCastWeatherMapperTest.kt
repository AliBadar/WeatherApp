package com.ahoy.core.mapper

import com.ahoy.core.MockTestUtil
import io.mockk.coEvery
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ForeCastWeatherMapperTest{

    private lateinit var sut: ForeCastWeatherMapper

    @Before
    fun setUp() {
        sut = ForeCastWeatherMapper()
    }

    @Test
    fun `map forecast weather entity to weather should return converted forecast weather`() {
        // Arrange (Given)
        val givenWeather = MockTestUtil.createForeCastWeather().list
        var weatherUiState = sut.mapToEntity(givenWeather)
        assertEquals(givenWeather.size, weatherUiState.foreCastListData.size)
        assertEquals(givenWeather[0].main?.humidity, 98)
    }
}