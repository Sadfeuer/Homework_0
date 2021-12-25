package com.example.homework7and8.externalData

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.homework7and8.database.WeatherEntity
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CityResponse(
    @SerializedName("coord") val coord: Coordinate,
    val weather: Any,
    val base: String,
    val main: Any,
    val visibility: Int,
    val wind: Any,
    val clouds: Any,
    val dt: Any,
    @SerializedName("sys") val sys: Sys,
    val timezone: Any,
    val id: String,
    val name: String,
    val cod: Int


) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toCityWeather(name: String) = WeatherEntity(
        name = name,
        id = id,
        country = sys.country,
        latitude = coord.lat,
        longitude = coord.lon,
        forecastDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    )
}

data class Coordinate(
    val lat: String,
    val lon: String
)

data class Sys(
    val type: Int,
    val ID: Int,
    val message: Double,
    val country: String
)