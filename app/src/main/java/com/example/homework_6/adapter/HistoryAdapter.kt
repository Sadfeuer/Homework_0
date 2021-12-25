package com.example.homework_6.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_6.R

class HistoryAdapter(private val receivedList: List<Int>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(historyView: View) : RecyclerView.ViewHolder(historyView) {
        val textView: TextView = historyView.findViewById(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val historyView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item, parent, false)

        return HistoryViewHolder(historyView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentInList = receivedList[position]
        holder.textView.text = currentInList.toString()
        holder.textView.setBackgroundColor(Color.CYAN)
    }

    override fun getItemCount() = receivedList.size

}
