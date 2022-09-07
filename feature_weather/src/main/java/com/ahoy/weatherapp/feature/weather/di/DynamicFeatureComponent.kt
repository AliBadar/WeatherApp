package com.ahoy.weatherapp.feature.weather.di

import android.content.Context
import com.ahoy.weatherapp.di.DynamicFeatureDependencies
import com.ahoy.weatherapp.feature.weather.TestFragment
import dagger.BindsInstance
import dagger.Component

@Component
interface DynamicFeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            dependencies: DynamicFeatureDependencies
        ): DynamicFeatureComponent
    }

    fun inject(fragment: TestFragment)
}

internal fun TestFragment.inject() {
    DaggerDynamicFeatureComponent.factory().create(
        requireContext(),
        dagger.hilt.android.EntryPointAccessors.fromApplication(
            requireContext().applicationContext,
            DynamicFeatureDependencies::class.java
        )
    ).inject(this)
}