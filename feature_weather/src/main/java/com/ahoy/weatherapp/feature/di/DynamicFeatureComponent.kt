//package com.ahoy.weatherapp.feature.di
//
//import android.content.Context
//import com.ahoy.weatherapp.di.DynamicFeatureDependencies
//import com.ahoy.weatherapp.feature.weather.TestFragment
//import dagger.BindsInstance
//import dagger.Component
//import dagger.hilt.android.EntryPointAccessors
//
//@Component(
//    dependencies = [DynamicFeatureDependencies::class],
//    modules = [
//        DynamicFeatureModule::class
//    ]
//)
//interface DynamicFeatureComponent {
//    @Component.Factory
//    interface Factory {
//        fun create(
//            @BindsInstance context: Context,
//            dependencies: DynamicFeatureDependencies
//        ): DynamicFeatureComponent
//    }
//
//    fun inject(fragment: TestFragment)
//
//}
//
//internal fun TestFragment.inject() {
//    DaggerDynamicFeatureComponent.factory().create(
//        requireActivity(),
//        EntryPointAccessors.fromApplication(
//            requireContext().applicationContext,
//    DynamicFeatureDependencies::class.java
//    )
//    ).inject(this)
//}
