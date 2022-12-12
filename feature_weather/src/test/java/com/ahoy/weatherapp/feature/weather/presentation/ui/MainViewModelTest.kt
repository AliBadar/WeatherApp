package com.ahoy.weatherapp.feature.weather.presentation.ui

import android.text.format.DateFormat
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.ahoy.common.utils.DateUtility
import com.ahoy.core.mapper.ForeCastWeatherMapper
import com.ahoy.core.mapper.WeatherMapper
import com.ahoy.core.network.Resource
import com.ahoy.core.uistates.*
import com.ahoy.core.uistates.forecast.ForeCastContent
import com.ahoy.core.uistates.forecast.ForeCastLoadingState
import com.ahoy.core.uistates.forecast.ForeCastMainUiState
import com.ahoy.core.uistates.forecast.ForeCastWeatherUIState
import com.ahoy.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.domain.usecase.GetFavCitiesWeatherUseCase
import com.ahoy.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.domain.usecase.SaveSearchCityWeatherUseCase
import com.ahoy.weatherapp.feature.weather.MainCoroutinesRule
import com.ahoy.weatherapp.feature.weather.MockTestUtil
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*


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

    @MockK
    lateinit var foreCastWeatherMapper: ForeCastWeatherMapper


    @MockK
    lateinit var calendar: Calendar


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        //Invoke
        mainViewModel = MainViewModel(
            getCurrentWeatherUseCase,
            getForeCastWeatherUseCase,
            saveSearchCityWeatherUseCase,
            getFavCitiesWeatherUseCase,
            weatherMapper,
            foreCastWeatherMapper
        )
    }

    @After
    fun tearDown() {

    }


    @Test
    fun `test when MainViewModel is initialized, current Location Weather is fetched`() =
        runBlocking {
            //Given
            val givenWeather = MockTestUtil.createCurrentWeather()
            val givenMapperData = MockTestUtil.getMapEntity()

//            Observers to observe for livedata
//            val uiObserver = mockk<Observer<MainUiState>>(relaxed = true)
//            val uiObserverCurrentWeather = mockk<Observer<CurrentWeatherUIState>>(relaxed = true)
//        val photosListObserver = mockk<Observer<List<PhotoModel>>>(relaxed = true)

            //When
            coEvery { getCurrentWeatherUseCase.invoke(any(), any(), any()) }
                .returns(flowOf(Resource.success(givenWeather)))

            coEvery { weatherMapper.mapToEntity(any()) }
                .returns(givenMapperData)



//            Setting Observer For Live Data
//            mainViewModel.currentWeatherData.observeForever(uiObserver)
//            mainViewModel.currentWeatherMainUIState.observeForever(uiObserverCurrentWeather)


            runTest {
                mainViewModel.getCurrentWeather("1212.11", "1212.11", "lahore")
            }

            mainViewModel.currentWeatherData.test {
                assertEquals(Content(givenMapperData), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            //Then
            coVerify(exactly = 1) {
                getCurrentWeatherUseCase(
                    "1212.11",
                    "1212.11",
                    "lahore"
                )
            }

//            Verifying the observer changed for different states
//            verify { uiObserver.onChanged(LoadingState) }
//            verify { uiObserver.onChanged(Content(MockTestUtil.getMapEntity())) }
//            verify { uiObserverCurrentWeather.onChanged(match { it.name == givenWeather.name }) }
        }

    @Test
    fun `test when MainViewModel is initialized, error is returned when current weather is fetched`() =
        runBlocking {
            //Given
            val givenMapperData = MockTestUtil.getMapEntity()

            val errorMessage = "Error message"
            //When
            coEvery { getCurrentWeatherUseCase.invoke(any(), any(), any()) }
                .returns(flowOf(Resource.error(null, errorMessage)))

            coEvery { weatherMapper.mapToEntity(any()) }
                .returns(givenMapperData)




            runTest {
                mainViewModel.getCurrentWeather("1212.11", "1212.11", "lahore")
            }


            mainViewModel.currentWeatherData.test {
                assertEquals(ErrorState(errorMessage), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            //Then
            coVerify(exactly = 1) {
                getCurrentWeatherUseCase(
                    "1212.11",
                    "1212.11",
                    "lahore"
                )
            }
        }


    @Test
    fun `test when forecast data Weather is fetched should return forecast list from use-case`() =
        runBlocking {
            //Given
            val givenForeCast = MockTestUtil.createForeCastWeather()
            val givenForeCastMapper = MockTestUtil.getForeCaseMapEntity()

            //When
            coEvery { getForeCastWeatherUseCase.invoke(any(), any(), any()) }
                .returns(flowOf(Resource.success(givenForeCast)))

            coEvery { foreCastWeatherMapper.mapToEntity(any()) }
                .returns(givenForeCastMapper)

            mainViewModel.getForeCast("1212.11", "1212.11", "lahore")

            mainViewModel.foreCastData.test {
                assertEquals(givenForeCastMapper, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            //Then
            coVerify(exactly = 1) {
                getForeCastWeatherUseCase.invoke(
                    "1212.11",
                    "1212.11",
                    "lahore",
                )
            }
        }
}