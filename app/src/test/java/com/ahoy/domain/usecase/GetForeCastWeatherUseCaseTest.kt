package com.ahoy.domain.usecase

import com.ahoy.core.network.Resource
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.forecast.ForecastWeather
import com.ahoy.domain.repository.WeatherRepository
import com.ahoy.weatherapp.MockTestUtil
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetForeCastWeatherUseCaseTest{
    lateinit var sut: GetForeCastWeatherUseCase

    @MockK
    lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = GetForeCastWeatherUseCase(weatherRepository)
    }

    @Test
    fun `get foreCastWeather should return success result with forecast weather`() = runBlocking {
        //Given
        var givenWeather = MockTestUtil.createForeCastWeather()

        //Arrange

        coEvery { weatherRepository.getForeCastWeather(any(), any(), any()) }.returns(givenWeather)
        //Act

        val result = sut.invoke("1212.11", "1212.11", "lahore").single()
        coVerify { sut.invoke("1212.11", "1212.11", "lahore") }
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        assertEquals(Resource.Status.SUCCESS, result.status)
    }

    @Test
    fun `get currentWeather should return error`() = runBlocking {
        //Given
        var givenError = flow { emit(Resource.error(ForecastWeather(), "Invalid token")) }

        //Arrange

        coEvery { weatherRepository.getForeCastWeather(any(), any(), any()) }.returns(givenError)
        //Act

        val result = sut.invoke("1212.11", "1212.11", "lahore").single()
        coVerify { sut.invoke("1212.11", "1212.11", "lahore") }

        assertEquals(Resource.Status.ERROR, result.status)
        assertEquals("Invalid token", result.message)
    }
}