package com.example.homework7and8.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.homework7and8.database.WeatherDao
import com.example.homework7and8.database.WeatherDatabase
import com.example.homework7and8.externalRequest.CityGet
import com.example.homework7and8.externalRequest.TemperatureGet
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appDatabase = Room.databaseBuilder(this, WeatherDatabase::class.java, APP_DATABASE).build()
    }

    companion object {
        private const val APP_DATABASE = "APP_DATABASE"

        private lateinit var appDatabase: WeatherDatabase

        private val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("https://api.openweathermap.org")
            .client(getHttpClientWithInterceptor())
            .build()

        val citiesService: CityGet = retrofit.create(CityGet::class.java)
        val forecastService: TemperatureGet =
            retrofit.create(TemperatureGet::class.java)

        private fun getHttpClientWithInterceptor(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        fun getCityDao(): WeatherDao = appDatabase.citiesDao()

        @RequiresApi(Build.VERSION_CODES.M)
        fun checkNetwork(context: Context?): Boolean {
            val manager: ConnectivityManager =
                context?.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                manager.getNetworkCapabilities(manager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
            return false
        }
    }
}