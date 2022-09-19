package com.ahoy.weatherapp.di

import com.ahoy.data.TestRepositoryImp
import com.ahoy.data.repository.WeatherRepositoryImp
import com.ahoy.domain.TestRepository
import com.ahoy.domain.repository.WeatherRepository
import com.ahoy.domain.usecase.GetCurrentWeatherUseCase
import com.ahoy.domain.usecase.GetFavCitiesWeatherUseCase
import com.ahoy.domain.usecase.GetForeCastWeatherUseCase
import com.ahoy.domain.usecase.SaveSearchCityWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSampleRepository(): TestRepository = TestRepositoryImp()

    @Provides
    @Singleton
    fun provideCurrentWeatherUseCase(weatherRepository: WeatherRepository): GetCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository)

    @Provides
    @Singleton
    fun provideForeCastWeatherUseCase(weatherRepository: WeatherRepository): GetForeCastWeatherUseCase = GetForeCastWeatherUseCase(weatherRepository)


    @Provides
    @Singleton
    fun provideFavCitiesWeatherUseCase(weatherRepository: WeatherRepository): GetFavCitiesWeatherUseCase = GetFavCitiesWeatherUseCase(weatherRepository)

    @Provides
    @Singleton
    fun provideSaveSearchCityUseCase(weatherRepository: WeatherRepository): SaveSearchCityWeatherUseCase = SaveSearchCityWeatherUseCase(weatherRepository)
}
