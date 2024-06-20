package com.example.iriordera.minhyeok

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iriordera.dohyeon.PointResponse
import com.example.iriordera.dohyeon.PostReview
import com.example.iriordera.dohyeon.RetrofitInstance
import com.example.iriordera.dohyeon.ReviewRespone
import com.example.iriordera.minhyeok.QRScan.CreateOrderResponse
import com.example.iriordera.minhyeok.QRScan.QRMenu
import com.example.iriordera.minhyeok.QRScan.QRStoreDetailResponse
import com.example.iriordera.minhyeok.orderCheck.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

data class MenuItem(val id : Long, val name:String, val price :Int, val introduction: String, var isChecked :Boolean)

data class StoreInfo(var name : String, var location : String)

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    //주문 현황 확인을 위한 필드, 메서드
    private val _orderResponse = MutableStateFlow<NetworkResult<Order?>?>(null)
    val orderResponse :StateFlow<NetworkResult<Order?>?> = _orderResponse

    fun getOrderResponse(){
        viewModelScope.launch {
            val result = repository.getOrderResponse(order_id, user_id)
            _orderResponse.value = result
        }
    }

    //qr 인식을 위한 필드, 메서드
    private val _qrStoreDetailResponse = MutableStateFlow<NetworkResult<QRStoreDetailResponse?>?>(null)
    val qrStoreDetailResponse : StateFlow<NetworkResult<QRStoreDetailResponse?>?> = _qrStoreDetailResponse

    var storeInfo = mutableStateOf(
        StoreInfo("로딩중", "로딩중")
    )
    var menuItemList = mutableStateListOf<MenuItem>()
    private var size :Int = 0
    private var table : Int = 0
    private var store_id : Long = 0

    private var sum:Long = 0
    private var isUpdated =0;

    fun flushMenuList(){
        menuItemList.clear()
    }

    fun qrStoreDetailResponse(store_id:Long, table:Int){
        viewModelScope.launch {
            val result = repository.qrStoreDetailResponse(store_id, table)
            _qrStoreDetailResponse.value = result
        }
    }

    fun insertStoreInfo(name:String, location :String){
        storeInfo.component1().name = name
        storeInfo.component1().location = location
        size = 0
    }

    fun insertMenuItem(menu: QRMenu){
        menuItemList.add(MenuItem(menu.menu_id, menu.name, menu.price, menu.introduction, false))
        size++
    }

    fun getMenuItem(index: Int): MenuItem {
        return menuItemList.get(index)
    }

    fun getSize(): Int {
        return size
    }

    fun changeChecked(index:Int){
        menuItemList[index] = menuItemList.get(index).copy(isChecked = !menuItemList.get(index).isChecked)
    }

    fun setTableNumber(table :Int){
        this.table = table
    }

    fun setStoreId(store_id: Long){
        this.store_id = store_id
    }

    fun setUserId(userId: Long){
        this.user_id = userId
    }

    fun getTableNumber() : Int{
        return table
    }

    fun getUserId():Long{
        return this.user_id
    }

    fun getStoreId(): Long{
        return this.store_id
    }

    fun getOrderId(): Long{
        return this.order_id
    }

    //주문 생성을 위한 필드, 메서드
    private val _createOrderResponse = MutableStateFlow<NetworkResult<CreateOrderResponse?>?>(null)
    val createOrderResponse : StateFlow<NetworkResult<CreateOrderResponse?>?> = _createOrderResponse
    private var user_id = 3L
    private var order_id = 0L

    fun emptyOrder(): Boolean {
        for(menu in menuItemList){
            if(menu.isChecked){
                return false
            }
        }
        return true
    }

    fun createOrder() {
        val menu_id = mutableListOf<Long>()
        for (menu in menuItemList) {
            if (menu.isChecked) {
                sum += menu.price
                menu_id.add(menu.id)
            }
        }
        viewModelScope.launch {
            val result = repository.createOrderResponse(menu_id, table, user_id, store_id)
            _createOrderResponse.value = result

            if (result is NetworkResult.Success && result.data != null) {
                order_id = result.data.order_id
            }
        }
    }

    fun print(msg: String){
        Log.d(msg , order_id.toString())
    }

    fun getPoint(){

        sum /= 100
        RetrofitInstance.apiService.getPoint(user_id,sum).enqueue(object :
            Callback<PointResponse> {
            override fun onResponse(call: Call<PointResponse>, response: Response<PointResponse>) {
                Log.d("Connection", response.body().toString())
            }

            override fun onFailure(call: Call<PointResponse>, t: Throwable) {
                Log.d("Connection", "fail")
            }
        })


        RetrofitInstance.apiService.delOrder(order_id).enqueue(object :
            Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("Connection", response.body().toString())
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("Connection", "fail")
            }
        })

        sum = 0

        order_id = 0L

        flushMenuList()

        setTableNumber(0)
    }
}