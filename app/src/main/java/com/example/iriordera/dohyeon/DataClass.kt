package com.example.iriordera.dohyeon

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message:String,
    @SerializedName("stores") var stores: List<Store>

)

data class Store(
    @SerializedName("store_id") var id:Long,
    @SerializedName("name") var name:String,
    @SerializedName("location") var location:String,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
)

data class Detail(
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message:String,
    @SerializedName("name") var name:String,
    @SerializedName("location") var location:String,
    @SerializedName("table") var table:Int,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("menus") var menus:List<Menu>
)

data class Menu(
    @SerializedName("name") var name: String,
    @SerializedName("price") var price: Int,
    @SerializedName("introduction") var introduction: String
)

data class Reviews(
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message:String,
    @SerializedName("reviews") var reviews:List<Review>
)

data class Review(
    @SerializedName("review_id") var id: Long,
    @SerializedName("user_name") var name:String,
    @SerializedName("content") var content:String,
    @SerializedName("star") var star:Int
)

data class ReviewRespone(
    @SerializedName("code") var code:Int,
    @SerializedName("message") var message:String,
    @SerializedName("review_id") var id: Long
)

data class PostReview(
    @SerializedName("user_id") var u_id: Long,
    @SerializedName("store_id") var s_id: Long,
    @SerializedName("content") var content: String,
    @SerializedName("star") var star: Int
)