package it.softbrain.barcomall.presentation.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.softbrain.barcomall.domain.usecase.AddToCartUseCase
import it.softbrain.barcomall.domain.usecase.GetBrandsUseCase
import it.softbrain.barcomall.domain.usecase.GetCategoryUseCase
import it.softbrain.barcomall.domain.usecase.GetHomePageUseCase
import it.softbrain.barcomall.domain.usecase.GetProductDetailsUseCase
import it.softbrain.barcomall.domain.usecase.GetProductsUseCase
import it.softbrain.barcomall.domain.usecase.LoginUserUseCase
import it.softbrain.barcomall.presentation.viewModel.dashboard.DashboardViewModelFactory
import it.softbrain.barcomall.presentation.viewModel.login.LoginViewModelFactory
import it.softbrain.barcomall.presentation.viewModel.products.ProductsViewModelFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FactoryModule {

    @Singleton
    @Provides
    fun providesLoginViewModelFactory(
        application: Application,
        loginUserUseCase: LoginUserUseCase
    ): LoginViewModelFactory {
        return LoginViewModelFactory(application, loginUserUseCase)
    }

    @Singleton
    @Provides
    fun providesDashboardViewModelFactory(
        application: Application,
        getCategoryUseCase: GetCategoryUseCase,
        getBrandsUseCase: GetBrandsUseCase,
        getHomePageUseCase: GetHomePageUseCase
    ): DashboardViewModelFactory {
        return DashboardViewModelFactory(
            application,
            getCategoryUseCase,
            getBrandsUseCase,
            getHomePageUseCase
        )
    }

    @Singleton
    @Provides
    fun providesProductsViewModelFactory(
        application: Application,
        getProductsUseCase: GetProductsUseCase,
        getProductDetailsUseCase: GetProductDetailsUseCase,
        addToCartUseCase: AddToCartUseCase
    ): ProductsViewModelFactory {
        return ProductsViewModelFactory(
            application, getProductsUseCase, getProductDetailsUseCase,
            addToCartUseCase
        )
    }

}