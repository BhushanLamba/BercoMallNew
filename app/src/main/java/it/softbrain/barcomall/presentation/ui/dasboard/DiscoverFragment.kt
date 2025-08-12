package it.softbrain.barcomall.presentation.ui.dasboard

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.models.SlideModel
import it.softbrain.barcomall.R
import it.softbrain.barcomall.data.models.Brands
import it.softbrain.barcomall.data.models.Categories
import it.softbrain.barcomall.data.models.Products
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.data.util.rainbowEffect
import it.softbrain.barcomall.databinding.FragmentDiscoverBinding
import it.softbrain.barcomall.databinding.NavigationDrawerBinding
import it.softbrain.barcomall.presentation.adapter.BrandsAdapter
import it.softbrain.barcomall.presentation.adapter.CategoryAdapter
import it.softbrain.barcomall.presentation.adapter.HotDealsAdapter
import it.softbrain.barcomall.presentation.adapter.ImageSliderAdapter
import it.softbrain.barcomall.presentation.ui.products.ProductDetailsFragment
import it.softbrain.barcomall.presentation.ui.products.ProductsFragment
import it.softbrain.barcomall.presentation.utils.ImageSliderUtils.setUpBanner
import it.softbrain.barcomall.presentation.viewModel.dashboard.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding

    private lateinit var dashboardViewModel: DashboardViewModel

    private lateinit var context: Context

    private lateinit var navDialogBinding: NavigationDrawerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        context = requireContext()
        navDialogBinding = (activity as DashboardActivity).navDialogBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardViewModel = (activity as DashboardActivity).dashboardViewModel

        dashboardViewModel.getCategory("Berco001")
        dashboardViewModel.getHomePageData("Berco001")
        dashboardViewModel.getBrands("Berco001")

        setUpObservers()

    }

    private fun setUpObservers() {
        dashboardViewModel.categoryData.observe(viewLifecycleOwner)
        {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.categoriesLy.visibility = View.GONE
                    binding.categoriesProgress.visibility = View.VISIBLE
                }

                is Resource.Success -> {

                    val response = it.data
                    val responseObject = JSONObject(response.toString())
                    val statusCode = responseObject.getString("Status_Code").toString()

                    val dataList = ArrayList<Categories>()

                    if (statusCode.equals("1", true)) {
                        val dataArray = responseObject.getJSONArray("Data")


                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val id = dataObject.getString("Id")
                            var name = dataObject.getString("CategoryName")
                            var picture = dataObject.getString("Picture")
                            picture = picture.replaceFirst(".", "")
                            val status = dataObject.getString("Status")

                           /* if (name.contains(" ")) {
                                name = name.replaceFirst(" ", "\n")
                            }
                            if (name.length > 15) {
                                name = name.substring(0, 15) + "..."
                            }*/

                            dataList.add(Categories(id, name, picture, status))
                        }

                        val adapter = CategoryAdapter(dataList)
                        { category ->

                            val categoryId = category.id

                            val bundle = Bundle()
                            bundle.putString("categoryId", categoryId)

                            (activity as DashboardActivity).replaceFragment(
                                ProductsFragment(),
                                bundle
                            )
                        }

                        val navMenuAdapter = CategoryAdapter(dataList,"NavMenu")
                        {
                                category ->

                            val categoryId = category.id

                            val bundle = Bundle()
                            bundle.putString("categoryId", categoryId)

                            (activity as DashboardActivity).replaceFragment(
                                ProductsFragment(),
                                bundle
                            )
                        }

                        val gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false)
                        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        gridLayoutManager.initialPrefetchItemCount = 4
                        binding.apply {
                            categoriesRecycler.adapter = adapter
                            categoriesRecycler.layoutManager = gridLayoutManager
                            categoriesRecycler.setHasFixedSize(true)

                        }

                        navDialogBinding.apply {
                            categoriesRecycler.adapter = navMenuAdapter
                            categoriesRecycler.layoutManager = linearLayoutManager
                            categoriesRecycler.setHasFixedSize(true)
                        }


                    }

                    binding.categoriesLy.visibility = View.VISIBLE
                    binding.categoriesProgress.visibility = View.GONE

                }
            }
        }

        dashboardViewModel.brandsData.observe(viewLifecycleOwner)
        {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {

                    val response = it.data
                    val responseObject = JSONObject(response.toString())
                    val statusCode = responseObject.getString("Status_Code").toString()

                    val dataList = ArrayList<Brands>()

                    if (statusCode.equals("1", true)) {
                        val dataArray = responseObject.getJSONArray("Data")


                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val id = dataObject.getString("Id")
                            var name = dataObject.getString("BrandName")
                            var picture = dataObject.getString("Picture")
                            picture = picture.replaceFirst(".", "")
                            val status = dataObject.getString("Status")

                            if (name.contains(" ")) {
                                name = name.replaceFirst(" ", "\n")
                            }
                            if (name.length > 15) {
                                name = name.substring(0, 15) + "..."
                            }

                            dataList.add(Brands(name, id, picture, status))
                        }

                        val adapter = BrandsAdapter(dataList) { brand ->
                            Toast.makeText(context, brand.brandName, Toast.LENGTH_LONG).show()
                        }
                        val gridLayoutManager =
                            GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false)

                        gridLayoutManager.initialPrefetchItemCount = 4
                        binding.apply {
                            brandsRecycler.adapter = adapter
                            brandsRecycler.layoutManager = gridLayoutManager
                            brandsRecycler.setHasFixedSize(true)

                        }


                    }
                    binding.brandsLy.visibility = View.VISIBLE

                }
            }
        }

        dashboardViewModel.homePageData.observe(viewLifecycleOwner)
        {

            when (it) {
                is Resource.Error -> {
                }

                is Resource.Loading -> {
                    binding.hotDealsLy.visibility = View.GONE
                    binding.hotDealsProgress.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    val response = it.data
                    val responseObject = JSONObject(response.toString())
                    val statusCode = responseObject.getString("Status_Code").toString()

                    if (statusCode.equals("1", true)) {
                        val bannerArray = responseObject.getJSONArray("Banner")
                        //val bannerList = ArrayList<SlideModel>()
                        val bannerList = ArrayList<String>()
                        for (position in 0 until bannerArray.length()) {
                            val bannerObject = bannerArray.getJSONObject(position)
                            var banner = bannerObject.getString("Picture")
                            banner = banner.removePrefix(".")
                            banner = "https://bercomall.com${banner}"

                            //bannerList.add(SlideModel(banner))
                            bannerList.add(banner)
                        }
                        setUpBanner(bannerList, binding.imageSlider)
                        //binding.imageSlider.setImageList(bannerList)


                        setUpHotDeals(responseObject)

                    }
                }
            }

        }
    }


    private fun setUpHotDeals(responseObject: JSONObject) {
        val titleList = ArrayList<String>()
        val valueList = ArrayList<JSONArray>()
        val titles = responseObject.keys()

        while (titles.hasNext()) {
            val title = titles.next()
            if (!title.equals("banner", true)) {
                val titleObject = responseObject.get(title)
                if (titleObject is JSONArray) {
                    titleList.add(title)
                    valueList.add(titleObject)

                }
            }
        }


        val hotDealsProductList = ArrayList<ArrayList<Products>>()
        for (i in 0 until valueList.size) {
            val dataArray = valueList[i]
            val productList = ArrayList<Products>()


            for (position in 0 until dataArray.length()) {
                val dataObject = dataArray.getJSONObject(position)
                val id = dataObject.getString("ProductId")
                var name = dataObject.getString("CategoryName")
                var picture = dataObject.getString("Picture")
                picture = picture.replaceFirst(".", "")
                val status = dataObject.getString("Status")
                var productName = dataObject.getString("ProductName")
                val mrp = dataObject.getString("MRP")
                val discount = dataObject.getString("DisAmount")
                val discountPercentage = dataObject.getString("Discountper")
                val price = dataObject.getString("Price")

                /* if (productName.length > 15) {
                     productName = productName.substring(0, 10) + "..."
                 }*/

                productList.add(
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

            hotDealsProductList.add(productList)
        }


        val adapter = HotDealsAdapter(titleList, hotDealsProductList, context)
        { product ->
            val productId = product.productId
            val bundle = Bundle()
            bundle.putString("productId", productId)

            (activity as DashboardActivity).replaceFragment(
                ProductDetailsFragment(),
                bundle
            )
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        layoutManager.initialPrefetchItemCount = 4
        binding.apply {

            hotDealsRecycler.adapter = adapter
            hotDealsRecycler.layoutManager = layoutManager
            hotDealsRecycler.setHasFixedSize(true)



            hotDealsLy.visibility = View.VISIBLE
            hotDealsProgress.visibility = View.GONE
        }

    }


}

