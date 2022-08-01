package com.ahoy.weatherapp.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahoy.weatherapp.MockTestUtil
import com.ahoy.weatherapp.data.mapper.WeatherMapper
import com.ahoy.weatherapp.data.repository.FakeWeatherRepository
import com.ahoy.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.weatherapp.domain.usecase.GetFavCitiesWeatherUseCase
import com.ahoy.weatherapp.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.weatherapp.domain.usecase.SaveSearchCityWeatherUseCase
import com.ahoy.weatherapp.presentation.ui.main.MainViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {


    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    lateinit var getForeCastWeatherUseCase: GetForeCastWeatherUseCase

    lateinit var saveSearchCityWeatherUseCase: SaveSearchCityWeatherUseCase

    lateinit var getFavCitiesWeatherUseCase: GetFavCitiesWeatherUseCase

    @MockK
    lateinit var weatherMapper: WeatherMapper

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    lateinit var fakeMovieRepository: FakeWeatherRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fakeMovieRepository = FakeWeatherRepository()
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(fakeMovieRepository)
        getForeCastWeatherUseCase = GetForeCastWeatherUseCase(fakeMovieRepository)
        saveSearchCityWeatherUseCase = SaveSearchCityWeatherUseCase(fakeMovieRepository)
        getFavCitiesWeatherUseCase = GetFavCitiesWeatherUseCase(fakeMovieRepository)
        mainViewModel = MainViewModel(getCurrentWeatherUseCase, getForeCastWeatherUseCase, saveSearchCityWeatherUseCase, getFavCitiesWeatherUseCase, weatherMapper)
    }

    @Test
    fun `test when MainViewModel is initialized, current Location Weather is fetched`() = runBlocking {
        getCurrentWeatherUseCase.invoke("121.23", "21312.1", "").collect {
            var responseBody = it.data
            responseBody?.let {
                MatcherAssert.assertThat(responseBody.name, CoreMatchers.`is`("Africa"))
                MatcherAssert.assertThat(responseBody.id, CoreMatchers.`is`(6255146))
                MatcherAssert.assertThat(responseBody.main?.humidity, CoreMatchers.`is`(98))
                MatcherAssert.assertThat(responseBody.main?.temp, CoreMatchers.`is`(293.68))

            }
        }

    }

    @Test
    fun `test when City Weather is filter, City Data is fetched`() = runBlocking {
        getCurrentWeatherUseCase.invoke("", "", "Africa").collect {
            var responseBody = it.data
            responseBody?.let {
                MatcherAssert.assertThat(responseBody.name, CoreMatchers.`is`("Africa"))
                MatcherAssert.assertThat(responseBody.id, CoreMatchers.`is`(6255146))
                MatcherAssert.assertThat(responseBody.main?.humidity, CoreMatchers.`is`(98))
                MatcherAssert.assertThat(responseBody.main?.temp, CoreMatchers.`is`(293.68))

            }
        }
    }

    @Test
    fun `test when filtered ForeCast Data is Fetched`() = runBlocking {

        getForeCastWeatherUseCase.invoke("", "", "Africa").collect {
            var responseBody = it.data?.list?.get(0)
            responseBody?.let {
                MatcherAssert.assertThat(responseBody.wind?.speed, CoreMatchers.`is`(0.51))
                MatcherAssert.assertThat(responseBody.main?.humidity, CoreMatchers.`is`(98))
                MatcherAssert.assertThat(responseBody.main?.temp, CoreMatchers.`is`(293.51))

            }
        }
    }


}