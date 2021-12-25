package com.example.homework7and8.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework7and8.R
import com.example.homework7and8.adapter.CityAdapter
import com.example.homework7and8.adapter.OnCLickListener
import com.example.homework7and8.database.WeatherEntity
import com.example.homework7and8.viewmodel.CityViewModel
import kotlinx.android.synthetic.main.city_add_fragment.*

class AddCityFragment : Fragment(R.layout.city_add_fragment) {
    companion object {
        fun create() = AddCityFragment()
    }

    private lateinit var citiesAdapter: CityAdapter

    private val viewModel = viewModels<CityViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.value.getAddedCities()
        }

        viewModel.value.citiesLiveData.observe(viewLifecycleOwner) {
            Log.d("MY_ERROR", "onViewCreated: $it")
            if (it.isEmpty())
                showNoticeDialog()
            updateRecyclerView(it)
        }

        viewModel.value.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.d("MY_ERROR", "error: $it")
        }

        button_add_city.setOnClickListener {
            showNoticeDialog()
        }

        setRecyclerView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("InflateParams")
    private fun showNoticeDialog() {
        AlertDialog.Builder(requireContext()).create().apply {
            val inflater = requireActivity().layoutInflater
            setView(inflater.inflate(R.layout.put_city_dialog, null))
            setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
                val cityInput = findViewById<EditText>(R.id.city_edit_text)?.text.toString()
                viewModel.value.search(cityInput)
            }
            setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
    }

    private fun setRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val userRecycle: RecyclerView? = view?.findViewById(R.id.cities_recyclerView)
        userRecycle?.layoutManager = layoutManager

        citiesAdapter = CityAdapter(onClickListener = OnCLickListener {
            changeChosenCityInBase(it.name)
        })
        userRecycle?.adapter = citiesAdapter

    }

    private fun changeChosenCityInBase(newChosenName: String) {
        viewModel.value.changeChosenCities(newChosenName = newChosenName)
    }

    private fun updateRecyclerView(cities: Set<WeatherEntity>) {
        cities_recyclerView.adapter?.let {
            val adapter = it as CityAdapter
            adapter.updateValue(cities)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
    }
}