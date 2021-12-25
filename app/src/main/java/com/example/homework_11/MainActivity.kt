package com.example.homework_11

import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.homework_11.PaintView.Companion.currentBrush

class MainActivity : AppCompatActivity() {

    companion object {
        var path = Path()
        var paintBrush = Paint()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val redBtn = findViewById<ImageButton>(R.id.red_pot)
        val greenBtn = findViewById<ImageButton>(R.id.green_pot)
        val blueBtn = findViewById<ImageButton>(R.id.blue_pot)
        val pinkBtn = findViewById<ImageButton>(R.id.pink_pot)
        val violetBtn = findViewById<ImageButton>(R.id.violet_pot)
        val greyBtn = findViewById<ImageButton>(R.id.grey_pot)
        val blackBtn = findViewById<ImageButton>(R.id.black_pot)
        val yellowBtn = findViewById<ImageButton>(R.id.yellow_pot)


        redBtn?.setOnClickListener {
            val khorne_red: Int = applicationContext.resources.getColor(R.color.khorne_red)
            setUp(khorne_red)
        }
        greenBtn?.setOnClickListener {
            val caliban_green: Int = applicationContext.resources.getColor(R.color.caliban_green)
            setUp(caliban_green)

        }
        blueBtn?.setOnClickListener {
            val macragge_blue: Int = applicationContext.resources.getColor(R.color.macragge_blue)
            setUp(macragge_blue)

        }
        pinkBtn?.setOnClickListener {
            val screamer_pink: Int = applicationContext.resources.getColor(R.color.screamer_pink)
            setUp(screamer_pink)

        }
        violetBtn?.setOnClickListener {
            val druchii_violet: Int = applicationContext.resources.getColor(R.color.druchii_violet)
            setUp(druchii_violet)

        }
        greyBtn?.setOnClickListener {
            val space_wolves_grey: Int =
                applicationContext.resources.getColor(R.color.space_wolves_grey)
            setUp(space_wolves_grey)

        }
        blackBtn?.setOnClickListener {
            val nuln_oil: Int = applicationContext.resources.getColor(R.color.nuln_oil)
            setUp(nuln_oil)
        }
        yellowBtn?.setOnClickListener {
            val inneadian_yellow: Int =
                applicationContext.resources.getColor(R.color.inneadian_yellow)
            setUp(inneadian_yellow)
        }

    }

    private fun currentColor(color: Int) {
        currentBrush = color
        path = Path()
    }

    private fun setUp(color: Int) {
        paintBrush.color = color
        currentColor(paintBrush.color)
        Toast.makeText(this, "Paint chosen", Toast.LENGTH_SHORT).show()
    }
}