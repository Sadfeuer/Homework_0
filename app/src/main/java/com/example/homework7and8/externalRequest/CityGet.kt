package com.example.homework7and8.externalRequest

import com.example.homework7and8.externalData.CityResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "3d0005a34fa1b0dc023bbe2617e7a6e0"

interface CityGet {
    @GET("data/2.5/weather")
    fun searchCityAsync(
        @Query("q") query: String,
        @Query("appid") ApiKey: String = API_KEY
    ): Deferred<Response<CityResponse>>
}