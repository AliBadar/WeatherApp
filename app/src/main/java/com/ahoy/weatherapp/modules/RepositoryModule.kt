
package com.ahoy.weatherapp.modules


import com.ahoy.weatherapp.data.database.AppLocalDataSource
import com.ahoy.weatherapp.data.remote.ApiRemoteDataSource
import com.ahoy.weatherapp.data.repository.WeatherRepositoryImp
import com.ahoy.weatherapp.domain.repository.WeatherRepository
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
    fun provideWeatherRepository(apiRemoteDataSource: ApiRemoteDataSource, localDataSource: AppLocalDataSource): WeatherRepository {
        return WeatherRepositoryImp(apiRemoteDataSource, localDataSource)
    }
}
