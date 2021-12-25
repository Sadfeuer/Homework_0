package com.example.homework7and8.externalRequest

import com.example.homework7and8.externalData.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TemperatureGet {
    @GET("data/2.5/onecall")
    fun searchTempAsync(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") ApiKey: String = API_KEY
    ): Deferred<Response<WeatherResponse>>
}