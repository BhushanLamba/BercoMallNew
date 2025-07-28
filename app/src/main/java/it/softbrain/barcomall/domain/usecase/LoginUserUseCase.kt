package it.softbrain.barcomall.domain.usecase

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import it.softbrain.barcomall.domain.repository.Repository

class LoginUserUseCase(private val loginRepository: Repository) {

    suspend fun execute(userName: String, password: String, apiKey: String): Resource<JsonObject> {
        return loginRepository.loginUser(userName, password, apiKey)
    }
}