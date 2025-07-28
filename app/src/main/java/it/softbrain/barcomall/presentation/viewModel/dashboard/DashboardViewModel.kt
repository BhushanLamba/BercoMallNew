package it.softbrain.barcomall.presentation.viewModel.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.usecase.GetBrandsUseCase
import it.softbrain.barcomall.domain.usecase.GetCategoryUseCase
import it.softbrain.barcomall.domain.usecase.GetHomePageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(
    application: Application,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getHomePageUseCase: GetHomePageUseCase
) : AndroidViewModel(application) {


    private val categoryLiveData = MutableLiveData<Resource<JsonObject>>()
    val categoryData: LiveData<Resource<JsonObject>>
        get() = categoryLiveData


    fun getCategory(apiKey: String) {
        categoryLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = getCategoryUseCase.execute(apiKey)
            categoryLiveData.postValue(apiResult)

        }
    }


    private val brandsLiveData = MutableLiveData<Resource<JsonObject>>()
    val brandsData: LiveData<Resource<JsonObject>>
        get() = brandsLiveData

    fun getBrands(apiKey: String) {
        brandsLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = getBrandsUseCase.execute(apiKey)
            brandsLiveData.postValue(apiResult)

        }
    }


    private val homePageLiveData = MutableLiveData<Resource<JsonObject>>()
    val homePageData: LiveData<Resource<JsonObject>>
        get() = homePageLiveData


    fun getHomePageData(apiKey: String) {
        homePageLiveData.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO)
        {
            val apiResult = getHomePageUseCase.execute(apiKey)
            homePageLiveData.postValue(apiResult)
        }
    }

}