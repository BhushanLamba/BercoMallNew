package it.softbrain.barcomall.presentation.viewModel.products

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.softbrain.barcomall.domain.usecase.AddToCartUseCase
import it.softbrain.barcomall.domain.usecase.GetProductDetailsUseCase
import it.softbrain.barcomall.domain.usecase.GetProductsUseCase

class ProductsViewModelFactory(
    private val application: Application,
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductDetailsCase:GetProductDetailsUseCase,
    private val addToCartUseCase:AddToCartUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(application, getProductsUseCase,getProductDetailsCase,addToCartUseCase) as T
    }
}