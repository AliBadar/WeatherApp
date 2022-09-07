package com.ahoy.weatherapp.di.modules

import android.content.Context
import androidx.room.Room
import com.ahoy.weatherapp.data.database.AppDatabase
import com.ahoy.weatherapp.data.database.AppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "cities_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideWeathersDao(database: AppDatabase): AppDao {
        return database.citiesDao()
    }
}