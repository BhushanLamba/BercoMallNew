package it.softbrain.barcomall.presentation.viewModel.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.wrapMappedColumns
import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.usecase.GetCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(
    application: Application,
    private val getCartUseCase: GetCartUseCase
) : AndroidViewModel(application) {

    private val cartLiveData = MutableLiveData<Resource<JsonObject>>()
    val cartData: LiveData<Resource<JsonObject>>
        get() = cartLiveData


    fun getCart(apiKey: String, userId: String) {
        cartLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCartUseCase.execute(apiKey, userId)
            cartLiveData.postValue(result)
        }
    }
}