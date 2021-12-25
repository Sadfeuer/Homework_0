package com.example.homework7and8.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework7and8.CurrentCityActivity
import com.example.homework7and8.R
import com.example.homework7and8.adapter.WeatherAdapter
import com.example.homework7and8.app.App
import com.example.homework7and8.database.WeatherEntity
import com.example.homework7and8.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_page_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainFragment : Fragment(R.layout.main_page_fragment) {
    companion object {
        fun create() = MainFragment()
    }

    private lateinit var weatherAdapter: WeatherAdapter

    private val viewModel = viewModels<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.value.citiesLiveData.observe(viewLifecycleOwner) {
            it?.let {
                updateView(it)
            }
        }

        viewModel.value.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        }

        view.findViewById<ImageButton>(R.id.mainAddButton).setOnClickListener {
            addCityActivity()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateView(city: WeatherEntity) {
        val cityInfoText = "${city.name}, ${city.country}"
        currentCity.text = cityInfoText
        todaysTemp.text = city.temperatures[0].first.toString()
        today_sunny.text = city.temperatures[0].second
        currentDate.text = city.forecastDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        setRecyclerView(city)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerView(city: WeatherEntity) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val forecastRecycle: RecyclerView? = view?.findViewById(R.id.forecast_recycler)
        forecastRecycle?.layoutManager = layoutManager
        val date = LocalDate.parse(city.forecastDate, DateTimeFormatter.ISO_LOCAL_DATE)

        weatherAdapter = WeatherAdapter(city.temperatures.apply { removeFirst() }, date)
        forecastRecycle?.adapter = weatherAdapter
    }

    private fun addCityActivity() {
        startActivity(Intent(context, CurrentCityActivity::class.java))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        if (!App.checkNetwork(context))
            offline.visibility = View.VISIBLE
        else offline.visibility = View.INVISIBLE
        viewModel.value.getCurrentCity()
    }
}