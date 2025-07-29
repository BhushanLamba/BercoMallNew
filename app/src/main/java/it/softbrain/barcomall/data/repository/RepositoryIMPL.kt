package it.softbrain.barcomall.data.repository

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.repository.dataSource.RemoteDataSource
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.repository.Repository
import retrofit2.Response

class RepositoryIMPL(private val remoteDataSource: RemoteDataSource) :
    Repository {
    override suspend fun loginUser(
        userName: String,
        password: String,
        apiKey: String
    ): Resource<JsonObject> {


        return responseToResource(remoteDataSource.loginUser(userName, password, apiKey))
    }

    override suspend fun getCategory(apiKey: String): Resource<JsonObject> {
        return responseToResource(remoteDataSource.getCategory(apiKey))
    }

    override suspend fun getBrands(apiKey: String): Resource<JsonObject> {
        return responseToResource(remoteDataSource.getBrands(apiKey))
    }

    override suspend fun getProducts(categoryId: String, apiKey: String): Resource<JsonObject> {
        return responseToResource(remoteDataSource.getProducts(categoryId, apiKey))
    }

    override suspend fun getProductsDetails(
        productId: String,
        apiKey: String
    ): Resource<JsonObject> {
        return responseToResource(remoteDataSource.getProductDetails(productId, apiKey))
    }

    override suspend fun getHomePage(apiKey: String): Resource<JsonObject> {
        return responseToResource(remoteDataSource.getHomePage(apiKey))
    }

    override suspend fun addToCart(
        apiKey: String,
        userId: String,
        productId: String,
        quantity: String
    ): Resource<JsonObject> {
        return responseToResource(remoteDataSource.addToCart(apiKey, userId, productId, quantity))
    }

    override suspend fun getCart(apiKey: String, userId: String): Resource<JsonObject> {
        return responseToResource(remoteDataSource.getCart(apiKey, userId))
    }

    private fun responseToResource(response: Response<JsonObject>): Resource<JsonObject> {

        if (response.isSuccessful) {

            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}