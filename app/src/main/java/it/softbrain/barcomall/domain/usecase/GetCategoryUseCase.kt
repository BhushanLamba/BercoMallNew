package it.softbrain.barcomall.domain.usecase

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.repository.Repository

class GetCategoryUseCase(private val repository: Repository) {

    suspend fun execute(apiKey: String): Resource<JsonObject> {
        return repository.getCategory(apiKey)
    }
}