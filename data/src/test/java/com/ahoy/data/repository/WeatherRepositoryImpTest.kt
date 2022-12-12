package com.ahoy.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahoy.core.database.AppLocalDataSource
import com.ahoy.core.network.Resource
import com.ahoy.core.network.sources.ApiRemoteDataSource
import com.ahoy.core.responses.current_weather.CurrentWeather
import com.ahoy.core.responses.forecast.ForecastWeather
import com.ahoy.data.FakeRemoteData
import com.ahoy.data.MainCoroutinesRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherRepositoryImpTest {

    lateinit var sut: WeatherRepositoryImp

    @MockK
    lateinit var apiRemoteDataSource: ApiRemoteDataSource

    @MockK
    lateinit var appLocalDataSource: AppLocalDataSource

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = WeatherRepositoryImp(apiRemoteDataSource, appLocalDataSource)
        sut = spyk(sut)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `get currentWeather should return success response from remote server`() = runBlocking {

        val givenWeather = FakeRemoteData.createCurrentWeather()
        coEvery { apiRemoteDataSource.getCurrentWeather(any(), any(), any()) }.returns(givenWeather)

        sut.getCurrentWeather("1212.11", "1212.11", "lahore")

        coVerify { sut.getCurrentWeather("1212.11", "1212.11", "lahore") }
    }

    @Test
    fun `get currentWeather should return error`() = runBlocking {

        val givenWeather = Resource.error(CurrentWeather(), "Invalid token")
        coEvery { apiRemoteDataSource.getCurrentWeather(any(), any(), any()) }.returns(givenWeather)

        var result = sut.getCurrentWeather("1212.11", "1212.11", "lahore").single()

        coVerify { sut.getCurrentWeather("1212.11", "1212.11", "lahore") }

        assertEquals(Resource.Status.ERROR, result.status)
        assertEquals("Invalid token", result.message)
    }

    @Test
    fun `get forecast Weather should return success response from remote server`() = runBlocking {

        val givenWeather = FakeRemoteData.createForeCastWeather()
        coEvery { apiRemoteDataSource.getForeCastWeather(any(), any(), any()) }.returns(givenWeather)

        var result = sut.getForeCastWeather("1212.11", "1212.11", "lahore").single()

        coVerify { sut.getForeCastWeather("1212.11", "1212.11", "lahore") }

        MatcherAssert.assertThat(result.data?.list?.size, CoreMatchers.`is`(10))
    }

    @Test
    fun `get forecast Weather should return error`() = runBlocking {

        val givenWeather = Resource.error(ForecastWeather(), "Invalid token")
        coEvery { apiRemoteDataSource.getForeCastWeather(any(), any(), any()) }.returns(givenWeather)

        var result = sut.getForeCastWeather("1212.11", "1212.11", "lahore").single()

        coVerify { sut.getForeCastWeather("1212.11", "1212.11", "lahore") }

        assertEquals(Resource.Status.ERROR, result.status)
        assertEquals("Invalid token", result.message)
    }

    @Test
    fun saveFavCityWeather() {
    }

    @Test
    fun getFavCitiesWeather() {
    }
}