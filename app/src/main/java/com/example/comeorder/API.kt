package com.example.comeorder

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
}