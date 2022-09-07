package com.ahoy.weatherapp.di

import com.ahoy.weatherapp.data.database.AppLocalDataSource
import com.ahoy.weatherapp.data.remote.ApiRemoteDataSource
import com.ahoy.weatherapp.data.repository.WeatherRepositoryImp
import com.ahoy.weatherapp.domain.repository.WeatherRepository
import com.ahoy.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.weatherapp.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.weatherapp.domain.usecase.SaveSearchCityWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

//    @Provides
//    @Singleton
//    fun providesGetCurrentWeatherUserCase(weatherRepository: WeatherRepository) : GetCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository)
//
//    @Provides
//    @Singleton
//    fun providesGetForeCastWeatherUseCase(weatherRepository: WeatherRepository) : GetForeCastWeatherUseCase = GetForeCastWeatherUseCase(weatherRepository)
//
//    @Provides
//    @Singleton
//    fun providesSaveSearchCityWeatherUseCase(weatherRepository: WeatherRepository) : SaveSearchCityWeatherUseCase = SaveSearchCityWeatherUseCase(weatherRepository)
}