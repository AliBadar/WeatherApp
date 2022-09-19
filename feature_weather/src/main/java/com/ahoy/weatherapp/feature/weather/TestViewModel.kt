//package com.ahoy.weatherapp.feature.weather
//
//import androidx.lifecycle.ViewModel
//import com.ahoy.domain.TestRepository
//import com.ahoy.domain.TestUseCase
//import javax.inject.Inject
//
//class TestViewModel  @Inject constructor(val testUseCase: TestUseCase): ViewModel() {
//    // TODO: Implement the ViewModel
//
//    lateinit var repository: TestRepository
//
//    fun setRepo(repository: TestRepository){
//        this.repository = repository
//    }
//
//    fun getDescription() = testUseCase.invoke()
//
//}
