package com.example.homework7and8.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.homework7and8.database.WeatherEntity
import com.example.homework7and8.externalRequest.CityGet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CityRepository(
    private val citiesService: CityGet
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun search(query: String): Result<WeatherEntity> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                citiesService.searchCityAsync(query = query)
                    .await()
                    .takeIf { it.isSuccessful }
                    ?.body()
                    ?.toCityWeather(query)
                    ?: throw Exception("Empty data")
            }
        }
    }
}