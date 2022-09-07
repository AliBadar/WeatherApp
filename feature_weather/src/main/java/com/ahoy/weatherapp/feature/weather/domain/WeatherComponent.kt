package com.ahoy.weatherapp.feature.weather.domain

import com.ahoy.weatherapp.di.AppModule
import com.ahoy.weatherapp.feature.weather.presentation.current_weather.WeatherFragment
import dagger.Component


@Component(modules = [AppModule::class])
interface WeatherComponent {

    /**
     * Inject dependencies on component.
     *
     * @param searchFragment
     */
    fun inject(weatherFragment: WeatherFragment)
}