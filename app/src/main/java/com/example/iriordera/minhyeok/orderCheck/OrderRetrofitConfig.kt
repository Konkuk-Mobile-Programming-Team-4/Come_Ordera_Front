package com.example.iriordera.minhyeok.orderCheck

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class OrderResponse(
    val code :Int,
    val message:String,
    val order: Order
)

data class Order(
    val order_id: Long,
    val name :String,
    val table :Int,
    val menus :List<Menu>
)

data class Menu(
    val menu_id :Long,
    val name :String,
    val price :Int
)

interface GetOrdersAPIService {
    @GET("/store/order")
    suspend fun getOrders(@Query("orderId") param1: Long? =null, @Query("userId") param2: Long? = null ) : Response<OrderResponse>
}

object GetOrderRetrofitClient{
    private const val BASE_URL = "https://zipkok.shop"

    private val client = OkHttpClient.Builder().build()

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    val getOrderAPIService : GetOrdersAPIService = retrofit.create(GetOrdersAPIService::class.java)
}