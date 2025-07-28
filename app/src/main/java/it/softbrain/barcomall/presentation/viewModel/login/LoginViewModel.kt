package it.softbrain.barcomall.presentation.viewModel.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val loginUserLiveData = MutableLiveData<Resource<JsonObject>>()
    val loginUserData: LiveData<Resource<JsonObject>>
        get() = loginUserLiveData


    fun loginUser(userName: String, password: String, apiKey: String) {
        loginUserLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult=loginUserUseCase.execute(userName, password, apiKey)
            loginUserLiveData.postValue(apiResult)

        }
    }

}