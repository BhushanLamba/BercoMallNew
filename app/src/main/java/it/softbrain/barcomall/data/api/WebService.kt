package it.softbrain.barcomall.data.api

import com.google.gson.JsonObject
import it.softbrain.barcomall.data.util.Resource
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WebService {

    @POST("SignUp")
    @FormUrlEncoded
    suspend fun signUp(
        @Field("CustName") name: String,
        @Field("EmailId") emailId: String,
        @Field("Password") password: String,
        @Field("MobileNo") mobileNo: String,
        @Field("ApiKey") apiKey: String
    )

    @POST("LoginUser")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("UserName") userName: String,
        @Field("Password") password: String,
        @Field("ApiKey") apiKey: String
    ): Response<JsonObject>

    @POST("GetAllcategory")
    @FormUrlEncoded
    suspend fun getAllCategory(
        @Field("ApiKey") apiKey: String
    ): Response<JsonObject>

    @POST("GetAllBrand")
    @FormUrlEncoded
    suspend fun getBrands(
        @Field("ApiKey") apiKey: String
    ): Response<JsonObject>

    @POST("GetProductlist")
    @FormUrlEncoded
    suspend fun getProductList(
        @Field("CategoryId") categoryId: String,
        @Field("ApiKey") apiKey: String
    ): Response<JsonObject>

    @POST("ProductDetails")
    @FormUrlEncoded
    suspend fun getProductDetails(
        @Field("ProductId") productId: String,
        @Field("ApiKey") apiKey: String
    ): Response<JsonObject>

    @POST("GetHomePage")
    @FormUrlEncoded
    suspend fun getHomePage(
        @Field("ApiKey") apiKey: String
    ): Response<JsonObject>

    @POST("SendSignUpOTP")
    @FormUrlEncoded
    suspend fun signUpOtp(
        @Field("EmailId") emailId: String,
        @Field("MobileNo") mobileNo: String,
        @Field("ApiKey") apiKey: String
    )


    @POST("AddtoCart")
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("ApiKey") ApiKey: String,
        @Field("UserId") UserId: String,
        @Field("ProductId") ProductId: String,
        @Field("Qnt") Qnt: String
    ): Response<JsonObject>
}