package com.example.homework7and8.database

import androidx.room.*

@Dao
interface WeatherDao {

    @Query("SELECT * FROM $NAME_OF_TABLE")
    fun getAll(): List<WeatherEntity>

    @Query("SELECT * FROM $NAME_OF_TABLE WHERE name = :name")
    fun getCityForecastByName(name: String): WeatherEntity

    @Query("SELECT * FROM $NAME_OF_TABLE WHERE chosen = 1")
    fun getChosenCity(): WeatherEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: WeatherEntity)

    @Update
    fun update(city: WeatherEntity)

}