package com.example.final_project.app_manage

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel : ViewModel() {

    var storeList = mutableStateListOf<StoreData>()
    var menuList = mutableStateListOf<MenuData>()
    var orderList = mutableStateListOf<OrderData>()
    var reviewList = mutableStateListOf<ReviewData>()

    var user_id: Long = 2 //유저 아이디
    var store_id: Long? = 1// 가게 아이디
    var review_id: Long? = 2 // 리뷰 아이디
    var point: Long = 2 //포인트
    /*var storeName: String? = null
    var storeLocation: String? = null
    var storeTable: Int? = null
    var storeLatitude: String? = null
    var storeLongitude: String? = null*/

    private val _refresh =mutableStateOf(false)
    val refresh: MutableState<Boolean> = _refresh

    //뷰모델 초기화 함수
    fun resetViewModel() {

        storeList.clear()
        menuList.clear()
        orderList.clear()
        reviewList.clear()

        /*storeName = null
        storeLocation = null
        storeTable = null
        storeLatitude = null
        storeLongitude = null*/
    }

    //뷰모델 초기화
    init {
        resetViewModel()
    }

    //가게 중복 여부 함수
    fun checkStoreExists(
        user_id: Long,
        name: String,
        location: String,
        table: Int,
        latitude: Double,
        longitude: Double
    ): Boolean {
        return storeList.any {
            it.user_id == user_id && it.name == name && it.location == location && it.table == table && it.latitude == latitude && it.longitude == longitude
        }
    }

    //메뉴 중복 여부 함수
    fun checkMenuExists(store_id: Long, name: String, price: Int, introduction: String): Boolean {
        return menuList.any {
            it.store_id == store_id && it.name == name && it.price == price && it.introduction == introduction
        }
    }

    // 가게 생성 로드 함수 추가
    fun createStore(
        user_id: Long,
        name: String,
        location: String,
        table: Int,
        latitude: Double,
        longitude: Double
    ) {
        val storeRequest = StoreRequest(user_id, name, location, table, latitude, longitude)
        Log.d("AppViewModel", "Request: $storeRequest") // 요청 로그
        val call = RetrofitInstance.storeApi.createStore(storeRequest)

        call.enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                Log.d("AppViewModel", "Response received") // 응답 로그
                if (response.isSuccessful) {
                    val storeResponse = response.body()
                    Log.d("AppViewModel", "Response: $storeResponse") // 응답 본문 로그
                    if (storeResponse?.code == 600) {
                        storeResponse.store_id?.let {
                            store_id = it.toLong()  // store_id 저장
                            Log.d("AppViewModel", "Store ID received: $store_id")  // Logcat에 출력
                        }
                    } else {
                        Log.d("AppViewModel", "Unexpected response code: ${storeResponse?.code}")
                    }
                } else {
                    Log.d(
                        "AppViewModel",
                        "Response unsuccessful: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to create store", t)
            }
        })
    }

    // 메뉴 생성 로드 함수 추가
    fun createMenu(storeId: Long, name: String, price: Int, introduction: String) {
        val menuRequest = MenuRequest(storeId, name, price, introduction)
        Log.d("AppViewModel", "Request: $menuRequest") // 요청 로그
        val call = RetrofitInstance.menuApi.createMenu(menuRequest)

        call.enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                Log.d("AppViewModel", "Response received") // 응답 로그
                if (response.isSuccessful) {
                    val menuResponse = response.body()
                    Log.d("AppViewModel", "Response: $menuResponse") // 응답 본문 로그
                    if (menuResponse?.code == 602) {
                        Log.d("AppViewModel", "Menu created successfully")
                    } else {
                        Log.d("AppViewModel", "Unexpected response code: ${menuResponse?.code}")
                    }
                } else {
                    Log.d(
                        "AppViewModel",
                        "Response unsuccessful: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to create menu", t)
            }
        })
    }

    // 주문 현황 로드 함수 추가
    fun loadOrderStatus(storeId: Long) {
        val call = RetrofitInstance.orderApi.getOrderStatus(storeId)
        call.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 601) {
                            orderList.clear()
                            orderList.addAll(it.orders)
                            Log.d("AppViewModel", "Orders loaded successfully: ${it.orders}")
                        } else {
                            Log.d("AppViewModel", "Unexpected response code: ${it.code}")
                        }
                    }
                } else {
                    Log.d("AppViewModel", "Failed to load orders: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to load orders", t)
            }
        })
    }

    // 리뷰 로드 함수 추가
    fun loadReviews(storeId: Long) {
        val call = RetrofitInstance.reviewsGetApi.getReviews(storeId)
        call.enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 603) {
                            reviewList.clear()
                            reviewList.addAll(it.reviews)
                            Log.d("AppViewModel", "Reviews loaded successfully: ${it.reviews}")
                        } else {
                            Log.d("AppViewModel", "Unexpected response code: ${it.code}")
                        }
                    }
                } else {
                    Log.d(
                        "AppViewModel",
                        "Failed to load reviews: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to load reviews", t)
            }
        })
    }

    // 리뷰 삭제 함수 추가
    fun deleteReview(reviewId: Long) {
        val call = RetrofitInstance.reviewsDeleteApi.deleteReviews(reviewId)
        call.enqueue(object : Callback<DeleteReviewResponse> {
            override fun onResponse(call: Call<DeleteReviewResponse>, response: Response<DeleteReviewResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 604) {
                            reviewList.removeAll { review -> review.review_id == reviewId }
                            Log.d("AppViewModel", "Review deleted successfully: $reviewId")
                        } else {
                            Log.d("AppViewModel", "Unexpected response code: ${it.code}")
                        }
                    }
                } else {
                    Log.d("AppViewModel", "Failed to delete review: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<DeleteReviewResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to delete review", t)
            }
        })
    }

    fun loadPoints(userId: Long) {
        val call = RetrofitInstance.pointApi.loadPoints(userId)
        call.enqueue(object : Callback<PointResponse> {
            override fun onResponse(call: Call<PointResponse>, response: Response<PointResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 607) {
                            point = it.point
                            Log.d("AppViewModel", "Points loaded successfully: ${it.point}")
                        } else {
                            Log.d("AppViewModel", "Unexpected response code: ${it.code}")
                        }
                    }
                } else {
                    Log.d("AppViewModel", "Failed to load points: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<PointResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to load points", t)
            }
        })
    }
}