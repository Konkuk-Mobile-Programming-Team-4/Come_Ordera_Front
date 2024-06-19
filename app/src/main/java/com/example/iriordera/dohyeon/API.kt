package com.example.iriordera.dohyeon

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
    @GET("/store")
    fun getStore(@Query("search") name: String): Call<Restaurant>

    @GET("/store/detail")
    fun getDetail(@Query("storeId") id: Long): Call<Detail>

    @GET("/store/review")
    fun getReview(@Query("storeId") id: Long): Call<Reviews>

    @POST("/review")
    fun postReview(@Body postReview: PostReview): Call<ReviewRespone>

    @GET("/point")
    fun getPoint(@Query("userId")id :Long, @Query("point")point:Long):Call<PointResponse>

    @GET("/order/delete")
    fun delOrder(@Query("orderId")id :Long):Call<Unit>
}