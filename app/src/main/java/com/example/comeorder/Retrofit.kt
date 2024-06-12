package com.example.comeorder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL:String = "https://zipkok.shop"

    private val retrofit:Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: API by lazy{
        retrofit.build().create(API::class.java)
    }
}