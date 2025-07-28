package it.softbrain.barcomall.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.softbrain.barcomall.domain.repository.Repository
import it.softbrain.barcomall.domain.usecase.AddToCartUseCase
import it.softbrain.barcomall.domain.usecase.GetBrandsUseCase
import it.softbrain.barcomall.domain.usecase.GetCategoryUseCase
import it.softbrain.barcomall.domain.usecase.GetHomePageUseCase
import it.softbrain.barcomall.domain.usecase.GetProductDetailsUseCase
import it.softbrain.barcomall.domain.usecase.GetProductsUseCase
import it.softbrain.barcomall.domain.usecase.LoginUserUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {


    @Singleton
    @Provides
    fun providesLoginUserUseCase(loginRepository: Repository): LoginUserUseCase {
        return LoginUserUseCase(loginRepository)
    }

    @Singleton
    @Provides
    fun providesGetCategoryUserUseCase(repository: Repository): GetCategoryUseCase {
        return GetCategoryUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetBrandsUseCase(repository: Repository): GetBrandsUseCase {
        return GetBrandsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetProductsUseCase(repository: Repository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetProductDetailsUseCase(repository: Repository): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(repository)
    }

    @Singleton
    @Provides
    fun getHomePageUseCase(repository: Repository): GetHomePageUseCase {
        return GetHomePageUseCase(repository)
    }

    @Singleton
    @Provides
    fun addToCart(repository: Repository): AddToCartUseCase {
        return AddToCartUseCase(repository)
    }

}