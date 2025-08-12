package it.softbrain.barcomall.presentation.ui.products

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import it.softbrain.barcomall.R
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.databinding.FragmentProductDetailsBinding
import it.softbrain.barcomall.presentation.ui.dasboard.DashboardActivity
import it.softbrain.barcomall.presentation.utils.CustomDialogs
import it.softbrain.barcomall.presentation.utils.ImageSliderUtils
import it.softbrain.barcomall.presentation.utils.ImageZoomFragment
import it.softbrain.barcomall.presentation.utils.ShareUtils
import it.softbrain.barcomall.presentation.viewModel.products.ProductsViewModel
import it.softbrain.barcomall.presentation.viewModel.products.ProductsViewModelFactory
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var productsViewModel: ProductsViewModel

    @Inject
    lateinit var productsViewModelFactory: ProductsViewModelFactory

    private lateinit var productId: String
    private lateinit var context: Context
    private lateinit var activity: Activity
    private lateinit var picture: String
    private var quantity: Int = 1
    private var quantityData= MutableLiveData<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        context = requireContext()
        activity = requireActivity()
        productsViewModel =
            ViewModelProvider(this, productsViewModelFactory)[ProductsViewModel::class]

        productId = arguments?.getString("productId").toString()
        quantityData.value = quantity

        productsViewModel.getProductDetails(productId, "Berco001")
        setUpObserver()
        handleClicks()

        return binding.root
    }

    private fun handleClicks() {
        binding.apply {
            imgWhatsApp.setOnClickListener {
                ShareUtils.shareImageWithLink(
                    context,
                    "https://bercomall.com$picture",
                    "https://bercomall.com/Details.aspx?id=$productId"
                )
            }

            btnAddToCart.setOnClickListener {
                productsViewModel.addToCart("Berco001", "1", productId, quantity.toString())
            }

            incCard.setOnClickListener {
                if (quantity < 10) {
                    quantity++
                    quantityData.value = quantity
                }
            }

            decCard.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    quantityData.value = quantity
                }
            }

            quantityData.observe(viewLifecycleOwner)
            {
                val quantityStr = quantity.toString()
                etQuantity.setText(quantityStr)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpObserver() {
        productsViewModel.productDetailsData.observe(viewLifecycleOwner)
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
                        val dataObject = dataArray.getJSONObject(0)

                        val productName = dataObject.getString("ProductName")
                        val mrp = dataObject.getString("MRP")
                        val discountPercentage = dataObject.getString("Discount")
                        val discountAmount = dataObject.getString("DisAmount")
                        val price = dataObject.getString("Price")
                        val warranty = dataObject.getString("Warrenty")
                        val deliveryIn = dataObject.getString("DeliveryIn")
                        val color = dataObject.getString("Color")
                        val specialTag = dataObject.getString("SpecialTag")
                        val subCategory = dataObject.getString("SubCategoryName")
                        val description = dataObject.getString("Decription")
                        val additionalInfo = dataObject.getString("AdditionalInfo")
                        picture = dataObject.getString("Picture")
                        picture = picture.replaceFirst(".", "")

                        var picture2 = dataObject.getString("Picture2")
                        picture2 = picture2.replaceFirst(".", "")


                        var picture3 = dataObject.getString("Picture3")
                        picture3 = picture3.replaceFirst(".", "")


                        var picture4 = dataObject.getString("Picture4")
                        picture4 = picture4.replaceFirst(".", "")


                        val imageList = ArrayList<String>()


                        imageList.add("https://bercomall.com$picture")
                        imageList.add("https://bercomall.com$picture2")
                        imageList.add("https://bercomall.com$picture3")
                        imageList.add("https://bercomall.com$picture4")



                        binding.apply {
                            //imageSlider.setImageList(imageList)
                            ImageSliderUtils.setUpBanner(imageList, imageSlider)
                            {
                                val productImage = it

                                val bundle = Bundle()
                                bundle.putString("productImage", productImage)

                                (activity as DashboardActivity).replaceFragment(
                                    ImageZoomFragment(),
                                    bundle
                                )
                            }

                            tvName.text = productName
                            tvMrp.text = "₹$mrp"
                            tvDiscount.text = "$discountPercentage% (₹ $discountAmount/- Off)"
                            tvPrice.text = "₹$price"
                            tvWarranty.text = warranty
                            tvDeliveryIn.text = deliveryIn
                            tvColor.text = color
                            tvSpeacialTag.text = specialTag
                            tvName.text = productName
                            tvSubCategory.text = subCategory

                            setUpDetailWebView(description)

                            tvDescription.setOnClickListener {
                                tvDescription.setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.red
                                    )
                                )
                                tvAdditionalInfo.setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.gray
                                    )
                                )

                                setUpDetailWebView(description)

                            }

                            tvAdditionalInfo.setOnClickListener {
                                tvDescription.setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.gray
                                    )
                                )
                                tvAdditionalInfo.setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.red
                                    )
                                )

                                setUpDetailWebView(additionalInfo)

                            }

                            //setHtmlToTextView(tvDetailHtml,description)
                        }

                    }
                }
            }
        }

        productsViewModel.addToCartData.observe(viewLifecycleOwner)
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

                    val message = responseObject.getString("Message").toString()
                    CustomDialogs.getMessageDialog(activity, message, false)

                }
            }
        }
    }

    private fun setUpDetailWebView(description: String) {
        val htmlStringFromApi =
            """<html><body>$description</body></html>"""  // wrap it in full HTML

        binding.apply {
            webViewDetailsHtml.settings.javaScriptEnabled = false
            webViewDetailsHtml.loadDataWithBaseURL(
                null,
                htmlStringFromApi,
                "text/html",
                "utf-8",
                null
            )

        }
    }


}