package com.ahoy.weatherapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahoy.weatherapp.MockTestUtil
import com.ahoy.weatherapp.data.database.AppDao
import com.ahoy.weatherapp.data.database.AppDatabase
import com.ahoy.weatherapp.data.models.current_weather.CurrentWeather
import com.ahoy.weatherapp.data.models.forecast.ForecastWeather
import com.ahoy.weatherapp.data.remote.Resource
import com.ahoy.weatherapp.domain.repository.WeatherRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FakeWeatherRepository: WeatherRepository {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: AppDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java).build()
        dao = database.citiesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        q: String
    ): Flow<Resource<CurrentWeather>> {
        return flow {
            var currentWeather = MockTestUtil.createCurrentWeather()
            currentWeather.apply {
                emit(Resource(Resource.Status.SUCCESS, this, ""))
            }
        }
    }

    override suspend fun getForeCastWeather(
        lat: String,
        lon: String,
        q: String
    ): Flow<Resource<ForecastWeather>> {
        return flow {
            MockTestUtil.createForeCastWeather().apply {
                emit(Resource(Resource.Status.SUCCESS, this, ""))
            }
        }
    }

    override suspend fun saveFavCityWeather(currentWeather: CurrentWeather) {

    }

    override suspend fun getFavCitiesWeather(): Flow<List<CurrentWeather>> {

        return flow {
            emit(mutableListOf())
        }

    }
}