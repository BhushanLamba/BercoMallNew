package it.softbrain.barcomall.domain.repository

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource

interface Repository
{
    suspend fun loginUser(userName:String,password:String,apiKey:String):Resource<JsonObject>

    suspend fun getCategory(apiKey: String): Resource<JsonObject>

    suspend fun getBrands(apiKey: String): Resource<JsonObject>

    suspend fun getProducts(categoryId:String,apiKey: String,):Resource<JsonObject>

    suspend fun getProductsDetails(productId:String,apiKey: String,):Resource<JsonObject>

    suspend fun getHomePage(apiKey:String):Resource<JsonObject>

    suspend fun addToCart(apiKey:String,userId:String,productId: String,quantity:String):Resource<JsonObject>
}