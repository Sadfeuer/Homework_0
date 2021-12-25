package com.example.homework7and8.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework7and8.R
import com.example.homework7and8.database.WeatherEntity
import com.example.homework7and8.operator.SearchesDiffUtils
import java.util.*

class CityAdapter(private val onClickListener: OnCLickListener) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private val cities = LinkedHashSet<WeatherEntity>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.cityItemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val string = cities
            .elementAt(position)
            .name + ", " +
                cities.elementAt(position)
                    .country
                    .uppercase(Locale.getDefault())
        holder.textView.text = string
        holder.itemView.setOnClickListener {
            changeChosen(position)
            onClickListener.onCLick(cities.elementAt(position))
        }
    }

    fun updateValue(newValues: Set<WeatherEntity>) {
        val historyDiffUtilCallback = SearchesDiffUtils(cities, newValues)
        val historyDiffResult = DiffUtil.calculateDiff(historyDiffUtilCallback, true)
        cities.clear()
        cities.addAll(newValues)
        historyDiffResult.dispatchUpdatesTo(this)
    }

    private fun changeChosen(position: Int) {
        val firstChosen = cities.indexOfFirst { it.chosen }
        cities.elementAt(firstChosen).chosen = false
        cities.elementAt(position).chosen = true
        notifyItemChanged(position)
        notifyItemChanged(firstChosen)
    }

    override fun getItemCount(): Int = cities.size
}

class OnCLickListener(val clickListener: (newChosenCity: WeatherEntity) -> Unit) {
    fun onCLick(newChosenCity: WeatherEntity) = clickListener(newChosenCity)
}