package com.example.final_project.app_manage

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// 가게 API 인터페이스
interface StoreApi {
    @POST("/store")
    fun createStore(@Body request: StoreRequest): Call<StoreResponse>
}

// 주문 현황 API 인터페이스
interface OrderApi {
    @GET("/order")
    fun getOrderStatus(@Query("storeId") storeId: Long): Call<OrderResponse>
}

// 메뉴 생성 API 인터페이스
interface MenuApi {
    @POST("/menu")
    fun createMenu(@Body request: MenuRequest): Call<MenuResponse>
}

// 리뷰 조회 API 인터페이스
interface ReviewsGetApi {
    @GET("store/review")
    fun getReviews(@Query("storeId") storeId: Long): Call<ReviewResponse>
}

// 리뷰 삭제 API 인터페이스
interface ReviewsDeleteApi{
    @DELETE("store/review")
    fun deleteReviews(@Query("reviewId") reviewId: Long): Call<DeleteReviewResponse>
}

// 포인트 획득 API 인터페이스
interface PointApi {
    @GET("user/point")
    fun loadPoints(@Query("userId") userId: Long): Call<PointResponse>
}

// Retrofit 인스턴스
object RetrofitInstance {
    private const val BASE_URL = "https://zipkok.shop"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 라이브러리 설정
            .build()
    }

    val storeApi: StoreApi by lazy {
        retrofit.create(StoreApi::class.java)
    }
    val menuApi: MenuApi by lazy {
        retrofit.create(MenuApi::class.java)
    }
    val orderApi: OrderApi by lazy {
        retrofit.create(OrderApi::class.java)
    }
    val reviewsGetApi: ReviewsGetApi by lazy {
        retrofit.create(ReviewsGetApi::class.java)
    }
    val reviewsDeleteApi: ReviewsDeleteApi by lazy {
        retrofit.create(ReviewsDeleteApi::class.java)
    }
    val pointApi: PointApi by lazy {
        retrofit.create(PointApi::class.java)
    }
}