package com.ahoy.weatherapp.presentation.ui.splash

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val _splashFlow = MutableStateFlow<Boolean>(false)
    val splashFlow = _splashFlow.asStateFlow()

    init {
        initSplash()
    }

    private fun initSplash(){
        viewModelScope.launch {
            delay(2000)
            updateStateData()
        }
    }

    private fun updateStateData(){
        _splashFlow.value = true
    }
}