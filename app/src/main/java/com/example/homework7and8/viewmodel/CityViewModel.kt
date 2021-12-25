package com.example.homework7and8.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework7and8.app.App
import com.example.homework7and8.database.WeatherEntity
import com.example.homework7and8.repository.CityRepository
import com.example.homework7and8.repository.WeatherRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CityViewModel : ViewModel() {
    private val citiesSearchRepos = CityRepository(App.citiesService)
    private val forecastSearchRepos = WeatherRepository(App.forecastService, App.getCityDao())
    private val exceptionHandler = CoroutineExceptionHandler { _, t ->
        _errorLiveData.postValue(t.toString())
    }

    private val _citiesLiveData = MutableLiveData<Set<WeatherEntity>>()
    val citiesLiveData: LiveData<Set<WeatherEntity>> get() = _citiesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private var searchJob: Job? = null

    override fun onCleared() {
        super.onCleared()

        searchJob = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun search(text: CharSequence) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exceptionHandler) {
            delay(500)
            Log.d("MY_ERROR", "search: $text")
            val cityWeatherResponse = citiesSearchRepos.search(text as String)
            cityWeatherResponse.getOrNull()?.let { city ->
                searchForecast(city)
            } ?: run {
                _errorLiveData.postValue(
                    cityWeatherResponse.exceptionOrNull()?.message ?: "unexpected exception"
                )
            }
        }
    }

    private fun searchForecast(city: WeatherEntity) {
        val delayTime:Long=1000

        searchJob?.cancel()
        searchJob = viewModelScope.launch(exceptionHandler) {
            delay(delayTime)
            val cityTemperatureResponse = forecastSearchRepos.searchTemp(city)
            cityTemperatureResponse.getOrNull()?.let {
                if (isDbEmpty()) city.chosen = true
                _citiesLiveData.postValue(forecastSearchRepos.writeCityToBase(city = city))
            } ?: run {
                _errorLiveData.postValue(
                    cityTemperatureResponse.exceptionOrNull()?.message ?: "unexpected exception"
                )
            }
        }
    }

    fun getAddedCities() {
        viewModelScope.launch {
            _citiesLiveData.postValue(forecastSearchRepos.getAll())
        }
    }

    fun changeChosenCities(newChosenName: String) {
        viewModelScope.launch {
            forecastSearchRepos.changeChosenCityByName(newChosenName = newChosenName)
        }
    }

    private fun isDbEmpty(): Boolean = forecastSearchRepos.isDbEmpty()
}