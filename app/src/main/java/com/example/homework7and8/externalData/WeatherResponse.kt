package com.example.homework7and8.externalData

import com.google.gson.annotations.SerializedName
import android.util.Log
import com.example.homework7and8.database.WeatherEntity
import kotlin.math.roundToInt

data class WeatherResponse(
    val lat: String,
    val lon: String,
    val timezone: String,
    val timezoneOffset: String,
    val current: Any,
    val minutely: Any,
    val hourly: Any,
    @SerializedName("daily") val daily: List<Daily>,
    val alerts: Any
) {
    fun getTemperature(city: WeatherEntity) = city.apply {
        Log.d("MY_ERROR", "changing for the city $city, daily $daily")
        val result = ArrayList<Pair<Int, String>>(emptyList())
        for (i in daily.indices)
            result.add(Pair(daily[i].day.roundToInt() - 273, daily[i].weather[0].main))
        Log.d("MY_ERROR", "city chacnged $this")
        temperatures = result
    }
}


data class WeatherData(
    val id: String?,
    val main: String,
    val description: String,
)

data class Daily(
    val day: Double,
    val weather: List<WeatherData>
)