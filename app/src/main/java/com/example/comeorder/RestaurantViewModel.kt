package com.example.comeorder

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantViewModel(private val application: Application): AndroidViewModel(application) {
    var restaurantList = mutableStateListOf<Store>()
        private set

    var restaurant = mutableStateListOf<Detail>()

    var menus = mutableStateListOf<Menu>()

    var reviews = mutableStateListOf<Review>()


    fun searchRequest(text:String):Int {
        val retrofit = RetrofitInstance
        var storeList = mutableStateListOf<Store>()

        retrofit.apiService.getStore(text).enqueue(object : Callback<Restaurant> {
            override fun onResponse(
                call: Call<Restaurant>,
                response: Response<Restaurant>
            ) {
                Log.d("Connection", response.body().toString())
                response.body()?.stores?.forEach{
                    storeList.add(it)
                }

                restaurantList = storeList


            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                Log.d("Connection", "fail")
            }

        })
        return 1
    }

    fun detailRequest(id:Long, navController:NavHostController){
        val retrofit = RetrofitInstance

        retrofit.apiService.getDetail(id).enqueue(object : Callback<Detail> {
            override fun onResponse(
                call: Call<Detail>,
                response: Response<Detail>
            ) {
                Log.d("Connection", response.body().toString())
                response.body()?.let { restaurant.add(it) }
                response.body()?.menus?.forEach {
                    menus.add(it)
                }
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
                Log.d("Connection", "fail")
            }
        })

        retrofit.apiService.getReview(id).enqueue(object : Callback<Reviews> {
            override fun onResponse(
                call: Call<Reviews>,
                response: Response<Reviews>
            ) {
                Log.d("Connection", response.body().toString())
                response.body()?.reviews?.forEach {
                    reviews.add(it)
                }

                navController.navigate(RestaurantRoutes.RestaurantScreen.route)
            }

            override fun onFailure(call: Call<Reviews>, t: Throwable) {
                Log.d("Connection", "fail")
            }
        })
    }

    fun postReview(content:String, star:String){
        var postReview = PostReview(
            u_id = "3".toLong(),
            s_id = "1".toLong(),
            content = content,
            star = star.toInt()
        )

        val retrofit = RetrofitInstance

        retrofit.apiService.postReview(postReview).enqueue(object : Callback<ReviewRespone> {
            override fun onResponse(call: Call<ReviewRespone>, response: Response<ReviewRespone>) {
                Log.d("Connection", response.body().toString())
            }

            override fun onFailure(call: Call<ReviewRespone>, t: Throwable) {
                Log.d("Connection", "fail")
            }
        })
    }
}