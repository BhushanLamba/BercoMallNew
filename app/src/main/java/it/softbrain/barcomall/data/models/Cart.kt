package it.softbrain.barcomall.data.models

data class Cart(val productId: String,val productName:String,val quantity:String,val mrp:String,val discount:String,val price:String,
    val total:String,val picture:String)