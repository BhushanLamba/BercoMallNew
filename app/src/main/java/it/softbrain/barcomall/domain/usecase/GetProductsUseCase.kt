package it.softbrain.barcomall.domain.usecase

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.repository.Repository
import retrofit2.Response

class GetProductsUseCase(private val repository: Repository) {

    suspend fun execute(categoryId:String,apiKey:String):Resource<JsonObject>
    {
        return repository.getProducts(categoryId, apiKey)
    }


}