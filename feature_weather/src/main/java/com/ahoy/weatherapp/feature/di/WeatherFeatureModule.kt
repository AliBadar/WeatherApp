package com.ahoy.weatherapp.feature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahoy.weatherapp.di.ViewModelFactory
import com.ahoy.weatherapp.di.ViewModelKey
import com.ahoy.weatherapp.feature.weather.presentation.ui.MainViewModel
import com.ahoy.weatherapp.feature.weather.presentation.ui.cities.CitiesViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ViewModelComponent::class)
abstract class WeatherFeatureModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CitiesViewModel::class)
    internal abstract fun bindCitiesViewModel(viewModel: CitiesViewModel): ViewModel
}
