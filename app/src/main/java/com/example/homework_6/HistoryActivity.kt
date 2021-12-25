package com.example.homework_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_6.adapter.HistoryAdapter
import com.example.homework_6.models.CounterViewModel


class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerHistory)
        val historyItem = intent.getIntArrayExtra("history")
        recyclerView.adapter = historyItem?.toList()?.let { HistoryAdapter(it) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

}
