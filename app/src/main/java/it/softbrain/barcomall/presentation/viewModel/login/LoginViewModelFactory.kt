package it.softbrain.barcomall.presentation.viewModel.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.softbrain.barcomall.domain.usecase.LoginUserUseCase

class LoginViewModelFactory(
    private val application: Application,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loginUserUseCase, application) as T
    }

}