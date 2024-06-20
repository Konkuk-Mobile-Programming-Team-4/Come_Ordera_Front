package com.example.iriordera.minhyeok.QRScan

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


data class QRStoreDetailResponse(
    val code :Int,
    val message:String,
    val name: String,
    val location: String,
    val menus:List<QRMenu>
)

data class QRMenu(
    val menu_id : Long,
    val name:String,
    val price :Int,
    val introduction:String
)

data class QRStoreDetailRequest(
    val store_id:Long,
    val table :Int
)

data class CreateOrderRequest(
    val menu_id: List<Long>,
    val table: Int,
    val user_id: Long,
    val store_id: Long
)

data class CreateOrderResponse(
    val code :Int,
    val message:String,
    val order_id : Long
)

interface QRStoreDetailAPIService {
    @POST("/store/detail")
    suspend fun qrStoreDetail(@Body request : QRStoreDetailRequest) : Response<QRStoreDetailResponse>

    @POST("/store/order")
    suspend fun createOrder(@Body request : CreateOrderRequest) : Response<CreateOrderResponse>
}

object QRStoreDetailRetrofitClient{
    private const val BASE_URL = "https://zipkok.shop"

    private val client = OkHttpClient.Builder().build()

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    val qrStoreDetailAPIService : QRStoreDetailAPIService = retrofit.create(QRStoreDetailAPIService::class.java)
}
