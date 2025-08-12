package it.softbrain.barcomall.presentation.ui.filter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import it.softbrain.barcomall.R
import it.softbrain.barcomall.databinding.FragmentFilterBinding
import it.softbrain.barcomall.presentation.adapter.FilterAdapter
import org.json.JSONArray


class FilterFragment(private val applyFilter: (List<String>, List<String>, List<String>, List<String>) -> Unit) :
    Fragment() {

    private lateinit var binding: FragmentFilterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFilterBinding.inflate(inflater, container, false)
        getSetData()

        return binding.root
    }

    private fun getSetData() {
        val dataArrayStr = arguments?.getString("dataArray")
        val dataArray = JSONArray(dataArrayStr)

        val brandsList = ArrayList<String>()
        val technologyTypeList = ArrayList<String>()
        val capacitySizeList = ArrayList<String>()
        val energyRatingList = ArrayList<String>()

        for (i in 0 until dataArray.length()) {
            val dataObject = dataArray.getJSONObject(i)
            val brands = dataObject.getString("BrandName")
            val technologyType = dataObject.getString("Product_Type")
            val capacitySize = dataObject.getString("Size")
            val energyRating = dataObject.getString("EnergyRating")

            brandsList.add(brands)
            technologyTypeList.add(technologyType)
            capacitySizeList.add(capacitySize)
            energyRatingList.add(energyRating)

        }

        val distinctBrandsList = brandsList.distinct()
        val selectedBrandsList = ArrayList<String>()
        val brandsAdapter = FilterAdapter(distinctBrandsList)
        { filterList ->

            if (selectedBrandsList.contains(filterList)) {
                selectedBrandsList.remove(filterList)
            } else {
                selectedBrandsList.add(filterList)
            }
        }


        val distinctTechnologyTypeList = technologyTypeList.distinct()
        val selectedTechnologyTypeList = ArrayList<String>()
        val technologyTypeAdapter = FilterAdapter(distinctTechnologyTypeList)
        { filterList ->

            if (selectedTechnologyTypeList.contains(filterList)) {
                selectedTechnologyTypeList.remove(filterList)
            } else {
                selectedTechnologyTypeList.add(filterList)
            }
        }

        val distinctCapacitySizeList = capacitySizeList.distinct()
        val selectedCapacitySizeList = ArrayList<String>()
        val capacitySizeAdapter = FilterAdapter(distinctCapacitySizeList)
        { filterList ->

            if (selectedCapacitySizeList.contains(filterList)) {
                selectedCapacitySizeList.remove(filterList)
            } else {
                selectedCapacitySizeList.add(filterList)
            }
        }

        val distinctEnergyRatingList = energyRatingList.distinct()
        val selectedEnergyRatingList = ArrayList<String>()
        val energyRatingAdapter = FilterAdapter(distinctEnergyRatingList)
        { filterList ->

            if (selectedEnergyRatingList.contains(filterList)) {
                selectedEnergyRatingList.remove(filterList)
            } else {
                selectedEnergyRatingList.add(filterList)
            }
        }

        binding.apply {
            brandsRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            brandsRecycler.adapter = brandsAdapter

            technologyTypeRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            technologyTypeRecycler.adapter = technologyTypeAdapter

            capacitySizeRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            capacitySizeRecycler.adapter = capacitySizeAdapter

            energyRatingRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            energyRatingRecycler.adapter = energyRatingAdapter



            tvApply.setOnClickListener {
                applyFilter(
                    selectedBrandsList,
                    selectedTechnologyTypeList,
                    selectedCapacitySizeList,
                    selectedEnergyRatingList
                )
                activity?.supportFragmentManager?.popBackStackImmediate()
            }

        }


    }

}