package com.ahoy.weatherapp.feature.di

import android.content.Context
import com.ahoy.weatherapp.di.DynamicFeatureDependencies
import com.ahoy.weatherapp.di.WeatherFeatureDependencies
import com.ahoy.weatherapp.feature.weather.presentation.ui.WeatherFragment
import com.ahoy.weatherapp.feature.weather.presentation.ui.cities.FragmentCities
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.EntryPointAccessors

@Component(
    dependencies = [WeatherFeatureDependencies::class],
    modules = [
        WeatherFeatureModule::class
    ]
)
interface WeatherFeatureComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            dependencies: WeatherFeatureDependencies
        ): WeatherFeatureComponent
    }

    fun inject(fragment: WeatherFragment)
    fun inject(fragment: FragmentCities)

}

internal fun WeatherFragment.inject() {
    DaggerWeatherFeatureComponent.factory().create(
        requireActivity(),
        EntryPointAccessors.fromApplication(
            requireContext().applicationContext,
            WeatherFeatureDependencies::class.java
    )
    ).inject(this)
}

internal fun FragmentCities.inject() {
    DaggerWeatherFeatureComponent.factory().create(
        requireActivity(),
        EntryPointAccessors.fromApplication(
            requireContext().applicationContext,
            WeatherFeatureDependencies::class.java
        )
    ).inject(this)
}
