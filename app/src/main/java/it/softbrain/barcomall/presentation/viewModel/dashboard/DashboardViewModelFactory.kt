package it.softbrain.barcomall.presentation.viewModel.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.softbrain.barcomall.domain.usecase.GetBrandsUseCase
import it.softbrain.barcomall.domain.usecase.GetCategoryUseCase
import it.softbrain.barcomall.domain.usecase.GetHomePageUseCase

class DashboardViewModelFactory(
    private val application: Application,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getHomePageUseCase:GetHomePageUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(application, getCategoryUseCase,getBrandsUseCase,getHomePageUseCase) as T
    }
}