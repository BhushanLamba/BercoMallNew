package it.softbrain.barcomall.domain.usecase

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.repository.Repository

class GetCartUseCase(private val repository: Repository)
{
    suspend fun execute(apiKey:String,userId:String):Resource<JsonObject>
    {
        return repository.getCart(apiKey, userId)
    }
}