package com.ahoy.domain.usecase

import com.ahoy.core.network.Resource
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.domain.repository.WeatherRepository
import com.ahoy.weatherapp.MockTestUtil
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class GetCurrentWeatherUseCaseTest {

    lateinit var sut: GetCurrentWeatherUseCase

    @MockK
    lateinit var weatherRepository: WeatherRepository

    val exceptionHandler = TestCoroutineExceptionHandler()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = GetCurrentWeatherUseCase(weatherRepository)
    }

    @Test
    fun `get currentWeather should return success result with current weather`() = runBlocking {
        //Given
        var givenWeather = MockTestUtil.createCurrentWeather()

        //Arrange

        coEvery { weatherRepository.getCurrentWeather(any(), any(), any()) }.returns(givenWeather)
        //Act

        val result = sut.invoke("1212.11", "1212.11", "lahore").single()
        coVerify { weatherRepository.getCurrentWeather("1212.11", "1212.11", "lahore") }
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        assertEquals(Resource.Status.SUCCESS, result.status)
    }

    @Test
    fun `get currentWeather should return error`() = runBlocking {
        //Given
        var givenError = flow { emit(Resource.error(CurrentWeather(), "Invalid token")) }

        //Arrange

        coEvery { weatherRepository.getCurrentWeather(any(), any(), any()) }.returns(givenError)
        //Act

        val result = sut.invoke("1212.11", "1212.11", "lahore").single()
        coVerify { weatherRepository.getCurrentWeather("1212.11", "1212.11", "lahore") }

        assertEquals(Resource.Status.ERROR, result.status)
        assertEquals("Invalid token", result.message)
    }
}