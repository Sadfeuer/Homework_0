package com.example.homework7and8.operator

import androidx.recyclerview.widget.DiffUtil
import com.example.homework7and8.database.WeatherEntity

class SearchesDiffUtils(
    private val oldList: Set<WeatherEntity>,
    private val newList: Set<WeatherEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldQuery = oldList.elementAt(oldItemPosition)
        val newQuery = newList.elementAt(newItemPosition)
        return oldQuery == newQuery
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldQuery = oldList.elementAt(oldItemPosition)
        val newQuery = newList.elementAt(newItemPosition)
        return oldQuery == newQuery
    }
}
