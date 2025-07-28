package it.softbrain.barcomall.presentation.viewModel.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.usecase.AddToCartUseCase
import it.softbrain.barcomall.domain.usecase.GetProductDetailsUseCase
import it.softbrain.barcomall.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel(
    application: Application,
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : AndroidViewModel(application) {


    private val productsLiveData = MutableLiveData<Resource<JsonObject>>()
    val productsData: LiveData<Resource<JsonObject>>
        get() = productsLiveData

    private val productDetailsLiveData = MutableLiveData<Resource<JsonObject>>()
    val productDetailsData: LiveData<Resource<JsonObject>>
        get() = productDetailsLiveData

    private val addToCartLiveData = MutableLiveData<Resource<JsonObject>>()
    val addToCartData: LiveData<Resource<JsonObject>>
        get() = addToCartLiveData


    fun getProducts(categoryId: String, apiKey: String) {
        productsLiveData.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {

            val apiResult = getProductsUseCase.execute(categoryId, apiKey)
            productsLiveData.postValue(apiResult)
        }
    }


    fun getProductDetails(productId: String, apiKey: String) {
        productDetailsLiveData.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = getProductDetailsUseCase.execute(productId, apiKey)
            productDetailsLiveData.postValue(apiResult)
        }
    }

    fun addToCart(apiKey: String, userId: String, productId: String, quantity: String) {
        addToCartLiveData.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = addToCartUseCase.execute(apiKey, userId, productId, quantity)
            addToCartLiveData.postValue(apiResult)
        }
    }
}