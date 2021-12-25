package com.example.homework7and8.operator

import androidx.room.TypeConverter

class TypeConverterTool {

    @TypeConverter
    fun convertFrom(temperatures: ArrayList<Pair<Int, String>>): String =
        temperatures.joinToString(separator = ";")

    @TypeConverter
    fun convertTo(data: String): ArrayList<Pair<Int, String>> {

        val result: ArrayList<Pair<Int, String>> =
            mutableListOf<Pair<Int, String>>() as ArrayList<Pair<Int, String>>
        data.split(";").forEach {
            val split = it.split(", ")
            val num = split[0].substring(1).toInt()
            val desc = split[1].subSequence(0, split[1].length - 1)
            result.add(
                Pair(
                    num,
                    desc.toString()
                )
            )
        }

        return result
    }
}