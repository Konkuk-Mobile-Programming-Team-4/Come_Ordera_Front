package com.example.iriordera

import androidx.compose.runtime.MutableLongState
import com.example.iriordera.QRScan.CreateOrderRequest
import com.example.iriordera.QRScan.CreateOrderResponse
import com.example.iriordera.QRScan.QRStoreDetailRequest
import com.example.iriordera.QRScan.QRStoreDetailResponse
import com.example.iriordera.QRScan.QRStoreDetailRetrofitClient
import com.example.iriordera.orderCheck.GetOrderRetrofitClient
import com.example.iriordera.orderCheck.Order
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

sealed class NetworkResult<out T>{
    data class Success<out T>(val data:T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}

@Singleton
class Repository @Inject constructor(){

    suspend fun getOrderResponse(orderId: Long, userId: Long) : NetworkResult<Order?> {

        return try{
            val response = GetOrderRetrofitClient.getOrderAPIService.getOrders(orderId,userId)

            if(response.isSuccessful){
                val orderList: Order? = response.body()?.order
                NetworkResult.Success(data = orderList)
            } else {
                NetworkResult.Error(exception = Exception("Network result failed"))
            }
        }catch(e: Exception){
            NetworkResult.Error(e)
        }

    }

    suspend fun qrStoreDetailResponse(store_id:Long, table: Int) : NetworkResult<QRStoreDetailResponse?> {

        val request = QRStoreDetailRequest(store_id,table)

        return try{
            val response = QRStoreDetailRetrofitClient.qrStoreDetailAPIService.qrStoreDetail(request)

            if(response.isSuccessful){
                val qrStoreDetailresponse: QRStoreDetailResponse? = response.body()
                NetworkResult.Success(data = qrStoreDetailresponse)
            } else {
                NetworkResult.Error(exception = Exception("Network result failed"))
            }
        }catch(e: Exception){
            NetworkResult.Error(e)
        }

    }

    suspend fun createOrderResponse(menu_id : List<Long>, table: Int, user_id : Long, store_id:Long): NetworkResult<CreateOrderResponse?>{
        val request = CreateOrderRequest(menu_id, table, user_id, store_id)

        return try{
            val response = QRStoreDetailRetrofitClient.qrStoreDetailAPIService.createOrder(request)

            if(response.isSuccessful){
                val createOrderResponse: CreateOrderResponse? = response.body()
                NetworkResult.Success(data = createOrderResponse)
            } else {
                NetworkResult.Error(exception = Exception("Network result failed"))
            }
        }catch(e: Exception){
            NetworkResult.Error(e)
        }
    }
}