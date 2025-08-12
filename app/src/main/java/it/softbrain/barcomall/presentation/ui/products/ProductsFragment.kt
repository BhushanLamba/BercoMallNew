package it.softbrain.barcomall.presentation.ui.products

import android.os.Bundle
import android.service.autofill.Validators.or
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.softbrain.barcomall.data.models.Products
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.databinding.FragmentProductsBinding
import it.softbrain.barcomall.presentation.adapter.ProductsAdapter
import it.softbrain.barcomall.presentation.ui.dasboard.DashboardActivity
import it.softbrain.barcomall.presentation.ui.filter.FilterFragment
import it.softbrain.barcomall.presentation.viewModel.products.ProductsViewModel
import it.softbrain.barcomall.presentation.viewModel.products.ProductsViewModelFactory
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding

    private lateinit var productsViewModel: ProductsViewModel

    @Inject
    lateinit var productsViewModelFactory: ProductsViewModelFactory

    private lateinit var categoryId: String

    private val dataList = ArrayList<Products>()
    private lateinit var adapter: ProductsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        categoryId = arguments?.getString("categoryId").toString()


        productsViewModel =
            ViewModelProvider(this, productsViewModelFactory)[ProductsViewModel::class]

        productsViewModel.getProducts(categoryId, "Berco001")

        setUpObservers()

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        return binding.root;
    }

    private fun searchData(query: String) {
        val filterDataList = ArrayList<Products>()
        for (position in 0 until dataList.size) {
            val data = dataList[position]

            if (data.productName.lowercase().contains(query.lowercase())) {
                filterDataList.add(data)
            }
            if (filterDataList.isNotEmpty()) {
                adapter.filterData(filterDataList)
            }

        }
    }

    private fun setUpObservers() {
        productsViewModel.productsData.observe(viewLifecycleOwner)
        {
            when (it) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {

                    val response = it.data
                    val responseObject = JSONObject(response.toString())
                    val statusCode = responseObject.getString("Status_Code").toString()


                    if (statusCode.equals("1", true)) {
                        val dataArray = responseObject.getJSONArray("Data")


                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val id = dataObject.getString("ProductId")
                            val brand = dataObject.getString("BrandName")
                            var name = dataObject.getString("CategoryName")
                            var picture = dataObject.getString("Picture")
                            picture = picture.replaceFirst(".", "")
                            val status = dataObject.getString("Status")
                            val productName = dataObject.getString("ProductName")
                            val mrp = dataObject.getString("MRP")
                            val discount = dataObject.getString("DisAmount")
                            val discountPercentage = dataObject.getString("Discountper")
                            val price = dataObject.getString("Price")
                            val productType = dataObject.getString("Product_Type")
                            val size = dataObject.getString("Size")
                            val energyRating = dataObject.getString("EnergyRating")

                            if (name.contains(" ")) {
                                name = name.replaceFirst(" ", "\n")
                            }
                            if (name.length > 15) {
                                name = name.substring(0, 15) + "..."
                            }

                            dataList.add(
                                Products(
                                    id,
                                    productName,
                                    mrp,
                                    discount,
                                    price,
                                    picture,
                                    status,
                                    discountPercentage,
                                    brand, productType, size, energyRating
                                )
                            )
                        }

                        adapter = ProductsAdapter(dataList)
                        { product ->
                            val productId = product.productId
                            val bundle = Bundle()
                            bundle.putString("productId", productId)

                            (activity as DashboardActivity).replaceFragment(
                                ProductDetailsFragment(),
                                bundle
                            )

                        }

                        val gridLayoutManager =
                            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)

                        binding.apply {
                            recycler.adapter = adapter
                            recycler.layoutManager = gridLayoutManager


                            btnFilter.setOnClickListener {
                                val bundle = Bundle()
                                bundle.putString("dataArray", dataArray.toString())


                                (activity as DashboardActivity).addFragment(
                                    FilterFragment(applyFilter = { brandsList, technologyTypeList, capacitySizeList, energyRatingList ->
                                        filterData(
                                            brandsList,
                                            technologyTypeList,
                                            capacitySizeList,
                                            energyRatingList
                                        )
                                    }),
                                    bundle
                                )
                            }
                        }


                    }


                }
            }
        }
    }
    private fun filterData(
        brandsList: List<String>,
        technologyTypeList: List<String>,
        capacitySizeList: List<String>,
        energyRatingList: List<String>
    ) {


        val filterDataList = dataList.filter { dataItem ->
            (brandsList.isEmpty() || brandsList.contains(dataItem.brand)) &&
                    (technologyTypeList.isEmpty() || technologyTypeList.contains(dataItem.productType)) &&
                    (capacitySizeList.isEmpty() || capacitySizeList.contains(dataItem.size)) &&
                    (energyRatingList.isEmpty() || energyRatingList.contains(dataItem.energyRating))
        }


        adapter.filterData(filterDataList as ArrayList)


    }

}