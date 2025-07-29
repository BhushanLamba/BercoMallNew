package it.softbrain.barcomall.presentation.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.softbrain.barcomall.R
import it.softbrain.barcomall.data.models.Cart
import it.softbrain.barcomall.data.models.Products
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.databinding.FragmentCartBinding
import it.softbrain.barcomall.presentation.adapter.CartAdapter
import it.softbrain.barcomall.presentation.adapter.ProductsAdapter
import it.softbrain.barcomall.presentation.viewModel.cart.CartViewModel
import it.softbrain.barcomall.presentation.viewModel.cart.CartViewModelFactory
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private lateinit var cartViewModel: CartViewModel

    @Inject
    lateinit var cartViewModelFactory: CartViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)


        cartViewModel = ViewModelProvider(this, cartViewModelFactory)[CartViewModel::class]

        cartViewModel.getCart("Berco001", "1")
        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        cartViewModel.cartData.observe(viewLifecycleOwner)
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

                    val dataList = ArrayList<Cart>()

                    if (statusCode.equals("1", true)) {
                        val dataArray = responseObject.getJSONArray("Data")
                        for (i in 0 until dataArray.length()) {
                            val dataObject = dataArray.getJSONObject(i)
                            val id = dataObject.getString("ProductId")
                            val name = dataObject.getString("ProductName")
                            val quantity = dataObject.getString("Qnt")
                            val mrp = dataObject.getString("MRP")
                            val discount = dataObject.getString("Discount")
                            val price = dataObject.getString("Price")
                            val total = dataObject.getString("Total")
                            var picture = dataObject.getString("Picture")
                            picture = picture.replaceFirst(".", "")


                            val cart =
                                Cart(id, name, quantity, mrp, discount, price, total, picture)
                            dataList.add(cart)
                        }
                        val adapter = CartAdapter(dataList)

                        val gridLayoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                        binding.apply {
                            cartRecycler.adapter = adapter
                            cartRecycler.layoutManager = gridLayoutManager

                            noDataFoundLy.visibility = View.GONE
                            btnProceed.visibility = View.VISIBLE
                            cartRecycler.visibility = View.VISIBLE
                        }

                    }
                }
            }
        }
    }

}