package com.example.homework7and8.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.homework7and8.operator.TypeConverterTool

@Entity(tableName = NAME_OF_TABLE)

data class WeatherEntity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey @ColumnInfo
        (name = "id") val id: String,
    @ColumnInfo
        (name = "name") val name: String,
    @ColumnInfo
        (name = "country") val country: String,
    var latitude: String = "",
    var longitude: String = "",
    @TypeConverters(TypeConverterTool::class)
    var temperatures: ArrayList<Pair<Int, String>> = mutableListOf<Pair<Int,
            String>>() as ArrayList<Pair<Int, String>>,
    var chosen: Boolean = false,
    @ColumnInfo
        (name = "forecastDate") val forecastDate: String
)

const val NAME_OF_TABLE = "CITIES"