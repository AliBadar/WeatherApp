package com.ahoy.core.mapper

import com.ahoy.core.MockTestUtil
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherMapperTest {

    private lateinit var sut: WeatherMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = WeatherMapper()
    }

    @Test
    fun `map weather entity to weather should return converted weather`() {
        runBlocking {
            // Arrange (Given)
            val givenWeather = MockTestUtil.createCurrentWeather()
            var weatherUiState = sut.mapToEntity(givenWeather)
            assertEquals(givenWeather.name, weatherUiState.name)
            assertEquals(givenWeather.wind?.speed?.toInt(), weatherUiState.wind)
            assertEquals(givenWeather.main?.temp?.toInt(), weatherUiState.temp)
        }
    }
}