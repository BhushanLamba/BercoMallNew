package it.softbrain.barcomall.presentation.viewModel.cart

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.softbrain.barcomall.domain.usecase.GetCartUseCase

class CartViewModelFactory(private val application: Application,
    private val getCartUseCase: GetCartUseCase) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(application,getCartUseCase) as T
    }

}