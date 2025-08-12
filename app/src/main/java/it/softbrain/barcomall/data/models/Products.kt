package it.softbrain.barcomall.data.models

data class Products(
    val productId: String, val productName: String, val mrp: String, val discountAmount: String,
    val price: String, val image: String, val status: String, val discountPercentage: String,val brand: String="",val productType:String="",
    val size:String="",val energyRating:String=""
)