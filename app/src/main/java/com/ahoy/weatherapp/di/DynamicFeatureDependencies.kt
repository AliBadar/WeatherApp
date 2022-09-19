package com.ahoy.weatherapp.di

import com.ahoy.domain.TestRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DynamicFeatureDependencies {
    fun testRepository(): TestRepository
}