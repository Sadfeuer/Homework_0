package com.example.homework_6

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.example.homework_6.data.DialogueProvider
import com.example.homework_6.models.CounterViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    private val viewModel = viewModels<CounterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intervalStatus: EditText = findViewById(R.id.interval)
        val currentNum: TextView = findViewById(R.id.сurrent_number)
        val currentStatus: TextView = findViewById(R.id.textViewStatus)
        val buttonSumm: Button = findViewById(R.id.buttonSumm)
        val buttonOn: Button = findViewById(R.id.buttonOn)
        val buttonOff: Button = findViewById(R.id.buttonOff)
        val buttonPause: Button = findViewById(R.id.buttonPause)
        val buttonHistory: Button = findViewById(R.id.buttonHistory)

        currentStatus.text = resources.getString(R.string.current_state_pause)

        viewModel.value.countLiveData.observe(this) {
            currentNum.text = it
        }

        if (intervalStatus.text != null) {
            val intervalSt = intervalStatus.text.toString()
            viewModel.value.changeIntervalTime(intervalSt.toIntOrNull())
        }

        buttonOn.setOnClickListener {
            if (!viewModel.value.countIsRunning) {
                startCount()
                currentStatus.text = resources.getString(R.string.current_state_on)
            } else return@setOnClickListener
        }

        buttonSumm.setOnClickListener {
            showAlertDialogue()
        }

        buttonHistory.setOnClickListener {
            val historyExtra = viewModel.value.history.toIntArray()
            startActivity(
                Intent(
                    this,
                    HistoryActivity::class.java
                )
                    .apply {
                        putExtra("history", historyExtra)
                    }
            )
        }

        buttonPause.setOnClickListener {
            viewModel.value.pauseCounting()
            currentStatus.text = resources.getString(R.string.current_state_pause)
        }

        buttonOff.setOnClickListener {
            viewModel.value.resetCounting()
            currentStatus.text = resources.getString(R.string.current_state_off)
            currentNum.text = 0.toString()
        }

    }

    private fun showAlertDialogue() {
        MaterialAlertDialogBuilder(this)
            .setTitle(DialogueProvider().alert1 + viewModel.value.actualNumber)
            .setMessage(DialogueProvider().alert2 + viewModel.value.summCounting())
            .setNeutralButton("OK", DialogInterface.OnClickListener { dialogue, _ ->
                dialogue.dismiss()
            })
            .show()
    }

    private fun startCount() {
        viewModel.value.startCounting()
    }
}