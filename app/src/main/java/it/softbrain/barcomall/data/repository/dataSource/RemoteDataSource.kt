package it.softbrain.barcomall.data.repository.dataSource

import com.google.gson.JsonObject
import retrofit2.Response

interface RemoteDataSource {
    suspend fun loginUser(userName: String, password: String, apiKey: String): Response<JsonObject>

    suspend fun getCategory(apiKey: String): Response<JsonObject>

    suspend fun getBrands(apiKey: String): Response<JsonObject>

    suspend fun getProducts(categoryId: String, apiKey: String): Response<JsonObject>

    suspend fun getProductDetails(productId: String, apiKey: String): Response<JsonObject>

    suspend fun getHomePage(apiKey: String): Response<JsonObject>

    suspend fun addToCart(
        apiKey: String,
        userId: String,
        productId: String,
        quantity: String
    ): Response<JsonObject>

    suspend fun getCart(apiKey: String,userId: String):Response<JsonObject>
}