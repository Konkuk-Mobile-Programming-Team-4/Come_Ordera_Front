package com.example.final_project.app_ui_sell

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.final_project.app_manage.AppViewModel
import com.example.final_project.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.final_project.app_manage.OrderData
import kotlinx.coroutines.launch

//@SuppressLint("UnrememberedMutableState")
@Composable
fun OrderStatusScreen(navController: NavHostController) {

    val appViewModel: AppViewModel = viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "주문 현황",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 20.dp)
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        appViewModel.store_id?.let {
                            Log.d("OrderStatusScreen", "Refreshing orders for storeId: $it")
                            appViewModel.loadOrderStatus(it)
                            Log.d("OrderStatusScreen", "Refresh complete")
                        } ?: run {
                            Log.e("OrderStatusScreen", "storeId is null")
                        }
                    }
                },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text("새로고침")
            }

            if (appViewModel.orderList.isEmpty()) {
                Text(
                    text = "주문 내역이 없습니다.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 20.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .background(Color(0xFFADD8E6))
                ) {
                    items(appViewModel.orderList) { order ->
                        OrderItem(order)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: OrderData) {
    Column(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .background(Color(0xFFE0F7FA)) // 각 주문 항목의 배경색
    ) {
        Text(
            text = "테이블 번호: ${order.table_number}, 주문 번호: ${order.order_id}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        order.menu.forEach { menu ->
            Text(
                text = "메뉴: ${menu.name}, 가격: ${menu.price}",
                fontSize = 16.sp
            )
        }
    }
}
// 주문 현황 로드
/*LaunchedEffect(Unit) {
    appViewModel.storeId?.let {
        appViewModel.loadOrderStatus(it)
    }
}*/
