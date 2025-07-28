package it.softbrain.barcomall.domain.usecase

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.repository.Repository

class GetProductDetailsUseCase(private val repository: Repository) {


    suspend fun execute(productId: String, apiKey: String): Resource<JsonObject> {
        return repository.getProductsDetails(productId, apiKey)
    }

}