package com.ahoy.weatherapp.feature.weather.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahoy.core.mapper.WeatherMapper
import com.ahoy.core.network.Resource
import com.ahoy.core.uistates.Content
import com.ahoy.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.domain.usecase.GetFavCitiesWeatherUseCase
import com.ahoy.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.domain.usecase.SaveSearchCityWeatherUseCase
import com.ahoy.weatherapp.feature.weather.MainCoroutinesRule
import com.ahoy.weatherapp.feature.weather.MockTestUtil
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {

    //SUT
    private lateinit var mainViewModel: MainViewModel


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @MockK
    lateinit var getForeCastWeatherUseCase: GetForeCastWeatherUseCase

    @MockK
    lateinit var saveSearchCityWeatherUseCase: SaveSearchCityWeatherUseCase

    @MockK
    lateinit var getFavCitiesWeatherUseCase: GetFavCitiesWeatherUseCase

    @MockK
    lateinit var weatherMapper: WeatherMapper


    @Before
    fun setUp() {
        MockKAnnotations.init(this)



    }

    @After
    fun tearDown(){

    }

    @Test
    fun `test when MainViewModel is initialized, current Location Weather is fetched`() = runBlocking {
        //Given
        val givenWeather = MockTestUtil.createCurrentWeather()

        //When
        coEvery { getCurrentWeatherUseCase.invoke(any(), any(), any()) }
            .returns(flowOf(Resource.success(givenWeather)))

        //Invoke
        mainViewModel = MainViewModel(
            getCurrentWeatherUseCase,
            getForeCastWeatherUseCase,
            saveSearchCityWeatherUseCase,
            getFavCitiesWeatherUseCase,
            weatherMapper
        )

        //Then
        coVerify(exactly = 1) { getCurrentWeatherUseCase.invoke("", "", "") }
        var value = mainViewModel.currentWeatherData.value
        assertEquals(givenWeather, mainViewModel.currentWeatherData.value)
//        verify { match { mainViewModel.currentWeatherData.value == givenWeather } }
    }

    @Test
    fun demoTest() {


        //Invoke
        mainViewModel = MainViewModel(
            getCurrentWeatherUseCase,
            getForeCastWeatherUseCase,
            saveSearchCityWeatherUseCase,
            getFavCitiesWeatherUseCase,
            weatherMapper
        )


        mainViewModel.loadMessage() // Uses testDispatcher, runs its coroutine eagerly
        assertEquals("Greetings!", mainViewModel.message.value)
    }
}