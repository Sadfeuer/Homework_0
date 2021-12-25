package com.example.homework7and8.repository

import com.example.homework7and8.database.WeatherDao
import com.example.homework7and8.database.WeatherEntity
import com.example.homework7and8.externalRequest.TemperatureGet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val temperatureService: TemperatureGet,
    private val citiesDao: WeatherDao
) {
    private var addedCities: Set<WeatherEntity>? = null

    suspend fun searchTemp(city: WeatherEntity): Result<WeatherEntity> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                temperatureService.searchTempAsync(lat = city.latitude, lon = city.longitude)
                    .await()
                    .takeIf { it.isSuccessful }
                    ?.body()
                    ?.getTemperature(city)
                    ?: throw Exception("empty data")
            }
        }
    }

    suspend fun writeCityToBase(city: WeatherEntity): Set<WeatherEntity> {
        withContext(Dispatchers.IO) {
            citiesDao.insert(city = city)
            insertInMemory(city = city)
        }

        return addedCities ?: emptySet()
    }

    private fun insertInMemory(city: WeatherEntity) {
        addedCities = addedCities?.toMutableSet()?.let { cities ->
            cities.add(city)
            cities
        } ?: setOf(city)
    }

    suspend fun getAll(): Set<WeatherEntity> {
        if (addedCities == null) {
            addedCities = withContext(Dispatchers.IO) {
                citiesDao.getAll().toSet()
            }
        }

        return addedCities ?: emptySet()
    }

    suspend fun getChosenCity(): WeatherEntity =
        withContext(Dispatchers.IO) {
            citiesDao.getChosenCity()
        }

    suspend fun changeChosenCityByName(newChosenName: String) {
        withContext(Dispatchers.IO) {
            val lastChosenModified = citiesDao.getChosenCity()
            lastChosenModified.chosen = false
            citiesDao.update(lastChosenModified)
            val newChosen = citiesDao.getCityForecastByName(newChosenName)
            newChosen.chosen = true
            citiesDao.update(newChosen)
        }
    }

    fun isDbEmpty(): Boolean = addedCities.isNullOrEmpty()
}