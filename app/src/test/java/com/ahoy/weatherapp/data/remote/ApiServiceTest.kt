package com.ahoy.weatherapp.data.remote

import com.ahoy.weatherapp.MainCoroutinesRule
import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.core.responses.forecast.ForecastWeather
import com.ahoy.weatherapp.data.remote.api.ApiAbstract
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ApiServiceTest : ApiAbstract<ApiService>() {

    private lateinit var apiService: ApiService

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        apiService = createService(ApiService::class.java)
    }

    @After
    fun tearDown() {
    }

    @Throws(IOException::class)
    @Test
    fun `test loadCurrentLocationWeather returns currentWeather` () = runBlocking {
        enqueueResponse("/weather_response.json")

        // Invoke
        val response = apiService.getCurrentWeather("74.3764", "31.5239", "")
        val responseBody = requireNotNull((response as Response<CurrentWeather>).body())
        mockWebServer.takeRequest()

        // Then
        MatcherAssert.assertThat(responseBody.name, CoreMatchers.`is`("Africa"))
        MatcherAssert.assertThat(responseBody.id, CoreMatchers.`is`(6255146))
        MatcherAssert.assertThat(responseBody.main?.humidity, CoreMatchers.`is`(98))
        MatcherAssert.assertThat(responseBody.main?.temp, CoreMatchers.`is`(293.68))
        MatcherAssert.assertThat(responseBody.wind?.speed, CoreMatchers.`is`(1.52))
    }

    @Throws(IOException::class)
    @Test
    fun `test searchCityName returns cityWeatherData` () = runBlocking {
        enqueueResponse("/weather_response.json")

        // Invoke
        val response = apiService.getCurrentWeather("", "", "Africa")
        val responseBody = requireNotNull((response as Response<CurrentWeather>).body())
        mockWebServer.takeRequest()

        // Then
        MatcherAssert.assertThat(responseBody.name, CoreMatchers.`is`("Africa"))
        MatcherAssert.assertThat(responseBody.id, CoreMatchers.`is`(6255146))
        MatcherAssert.assertThat(responseBody.main?.humidity, CoreMatchers.`is`(98))
        MatcherAssert.assertThat(responseBody.main?.temp, CoreMatchers.`is`(293.68))
        MatcherAssert.assertThat(responseBody.wind?.speed, CoreMatchers.`is`(1.52))
    }

    @Throws(IOException::class)
    @Test
    fun `test foreCasting with CurrentLocation returns FiveDaysForeCastingData` () = runBlocking {
        enqueueResponse("/weather_response.json")

        // Invoke
        val response = apiService.getForeCastWeather("74.3764", "31.5239", "")
        val responseBody = requireNotNull((response as Response<ForecastWeather>).body())
        mockWebServer.takeRequest()

        // Then
        MatcherAssert.assertThat(responseBody.list[0].weather[0].main, CoreMatchers.`is`("Clouds"))
        MatcherAssert.assertThat(responseBody.list[0].dt, CoreMatchers.`is`(1659322800))
        MatcherAssert.assertThat(responseBody.list[0].main?.humidity, CoreMatchers.`is`(98))
        MatcherAssert.assertThat(responseBody.list[0].main?.temp, CoreMatchers.`is`(293.51))
        MatcherAssert.assertThat(responseBody.list[0].wind?.speed, CoreMatchers.`is`(0.51))
    }

    @Throws(IOException::class)
    @Test
    fun `test foreCasting with searchQuery returns FiveDaysForeCastingData` () = runBlocking {
        enqueueResponse("/weather_response.json")

        // Invoke
        val response = apiService.getForeCastWeather("", "", "Africa")
        val responseBody = requireNotNull((response as Response<ForecastWeather>).body())
        mockWebServer.takeRequest()

        // Then
        MatcherAssert.assertThat(responseBody.list[0].weather[0].main, CoreMatchers.`is`("Clouds"))
        MatcherAssert.assertThat(responseBody.list[0].dt, CoreMatchers.`is`(1659322800))
        MatcherAssert.assertThat(responseBody.list[0].main?.humidity, CoreMatchers.`is`(98))
        MatcherAssert.assertThat(responseBody.list[0].main?.temp, CoreMatchers.`is`(293.51))
        MatcherAssert.assertThat(responseBody.list[0].wind?.speed, CoreMatchers.`is`(0.51))
    }
}