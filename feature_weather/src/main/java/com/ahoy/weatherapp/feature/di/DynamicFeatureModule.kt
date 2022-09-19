//package com.ahoy.weatherapp.feature.di
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.ahoy.weatherapp.di.ViewModelFactory
//import com.ahoy.weatherapp.di.ViewModelKey
//import com.ahoy.weatherapp.feature.weather.TestViewModel
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//import dagger.multibindings.IntoMap
//
//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class DynamicFeatureModule {
//    @Binds
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(TestViewModel::class)
//    internal abstract fun bindHomeViewModel(viewModel: TestViewModel): ViewModel
//}
