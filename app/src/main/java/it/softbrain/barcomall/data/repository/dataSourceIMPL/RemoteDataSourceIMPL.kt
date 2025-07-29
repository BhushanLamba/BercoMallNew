package it.softbrain.barcomall.data.repository.dataSourceIMPL

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.api.WebService
import it.softbrain.barcomall.data.repository.dataSource.RemoteDataSource
import retrofit2.Response

class RemoteDataSourceIMPL(private val webService: WebService) : RemoteDataSource {
    override suspend fun loginUser(
        userName: String,
        password: String,
        apiKey: String
    ): Response<JsonObject> {
        return webService.loginUser(userName, password, apiKey)

    }

    override suspend fun getCategory(apiKey: String): Response<JsonObject> {
        return webService.getAllCategory(apiKey)
    }

    override suspend fun getBrands(apiKey: String): Response<JsonObject> {
        return webService.getBrands(apiKey)
    }

    override suspend fun getProducts(categoryId: String, apiKey: String): Response<JsonObject> {
        return webService.getProductList(categoryId, apiKey)
    }

    override suspend fun getProductDetails(
        productId: String,
        apiKey: String
    ): Response<JsonObject> {
        return webService.getProductDetails(productId, apiKey)
    }

    override suspend fun getHomePage(apiKey: String): Response<JsonObject> {
        return webService.getHomePage(apiKey)
    }

    override suspend fun addToCart(
        apiKey: String,
        userId: String,
        productId: String,
        quantity: String
    ): Response<JsonObject> {
        return webService.addToCart(apiKey, userId, productId, quantity)
    }

    override suspend fun getCart(apiKey: String, userId: String): Response<JsonObject> {
        return webService.getCart(apiKey,userId)
    }


}