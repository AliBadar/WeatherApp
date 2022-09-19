
package com.ahoy.weatherapp.di


import com.ahoy.core.database.AppLocalDataSource
import com.ahoy.core.network.sources.ApiRemoteDataSource
import com.ahoy.data.repository.WeatherRepositoryImp
import com.ahoy.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(apiRemoteDataSource: ApiRemoteDataSource, appLocalDataSource: AppLocalDataSource): WeatherRepository {
        return WeatherRepositoryImp(apiRemoteDataSource, appLocalDataSource)
    }
}
