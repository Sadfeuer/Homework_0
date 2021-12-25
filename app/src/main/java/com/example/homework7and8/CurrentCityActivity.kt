package com.example.homework7and8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.homework7and8.fragments.AddCityFragment

class CurrentCityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_city)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.city_holder, AddCityFragment.create())
                .commit()
        }
    }
}