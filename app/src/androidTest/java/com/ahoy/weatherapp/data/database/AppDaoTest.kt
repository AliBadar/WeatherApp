package com.ahoy.weatherapp.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahoy.weatherapp.MockTestUtil
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDaoTest {

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

    @Test
    fun saveCityData_AfterFetched(): Unit = runBlocking{
        var mockData = MockTestUtil.createCurrentWeather()
        dao.addCurrentWeather(mockData)

        val allCars = dao.getFavCitiesWeather()
        Truth.assertThat(allCars).contains(mockData)
    }
}