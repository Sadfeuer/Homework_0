package com.example.homework7and8.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework7and8.app.App
import com.example.homework7and8.database.WeatherEntity
import com.example.homework7and8.repository.WeatherRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val forecastRepository = WeatherRepository(App.forecastService, App.getCityDao())


    private val _citiesLiveData = MutableLiveData<WeatherEntity>()
    val citiesLiveData: LiveData<WeatherEntity> get() = _citiesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private var dbJob: Job? = null

    override fun onCleared() {
        super.onCleared()

        dbJob = null
    }

    fun getCurrentCity() {
        dbJob?.cancel()
        dbJob = viewModelScope.launch {
            delay(500)
            val cityWeatherResponse = forecastRepository.getChosenCity()
            _citiesLiveData.postValue(cityWeatherResponse)
        }
    }
}