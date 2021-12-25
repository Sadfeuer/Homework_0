package com.example.homework7and8.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.homework7and8.operator.TypeConverterTool

@Database(entities = [WeatherEntity::class], version = 1)
@TypeConverters(TypeConverterTool::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun citiesDao(): WeatherDao
}
