package com.ahoy.weatherapp.feature.weather

import com.ahoy.weatherapp.feature.weather.presentation.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestContextProvider : CoroutineContextProvider {
    val test = TestCoroutineDispatcher()

    override val io: CoroutineDispatcher = test

    override val default: CoroutineDispatcher = test

    override val main: CoroutineDispatcher = test
}
