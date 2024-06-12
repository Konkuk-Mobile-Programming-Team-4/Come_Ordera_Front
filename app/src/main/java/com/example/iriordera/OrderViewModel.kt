package com.example.iriordera

import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.iriordera.QRScan.CreateOrderResponse
import com.example.iriordera.QRScan.QRMenu
import com.example.iriordera.QRScan.QRStoreDetailResponse
import com.example.iriordera.orderCheck.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    //주문 생성을 위한 필드, 메서드
    private val _createOrderResponse = MutableStateFlow<NetworkResult<CreateOrderResponse?>?>(null)
    val createOrderResponse : StateFlow<NetworkResult<CreateOrderResponse?>?> = _createOrderResponse
    private var user_id = 3L
    private var order_id = 3L

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
}