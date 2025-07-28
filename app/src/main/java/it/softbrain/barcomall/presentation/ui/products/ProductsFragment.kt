package it.softbrain.barcomall.presentation.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.softbrain.barcomall.R
import it.softbrain.barcomall.data.models.Categories
import it.softbrain.barcomall.data.models.Products
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.databinding.FragmentProductsBinding
import it.softbrain.barcomall.presentation.adapter.ProductsAdapter
import it.softbrain.barcomall.presentation.ui.MainActivity
import it.softbrain.barcomall.presentation.ui.dasboard.DashboardActivity
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
        return binding.root;
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

                    val dataList = ArrayList<Products>()

                    if (statusCode.equals("1", true)) {
                        val dataArray = responseObject.getJSONArray("Data")


                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val id = dataObject.getString("ProductId")
                            var name = dataObject.getString("CategoryName")
                            var picture = dataObject.getString("Picture")
                            picture = picture.replaceFirst(".", "")
                            val status = dataObject.getString("Status")
                            val productName = dataObject.getString("ProductName")
                            val mrp = dataObject.getString("MRP")
                            val discount = dataObject.getString("DisAmount")
                            val discountPercentage = dataObject.getString("Discountper")
                            val price = dataObject.getString("Price")

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
                                    discountPercentage
                                )
                            )
                        }

                        val adapter = ProductsAdapter(dataList)
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
                        }


                    }


                }
            }
        }
    }

}