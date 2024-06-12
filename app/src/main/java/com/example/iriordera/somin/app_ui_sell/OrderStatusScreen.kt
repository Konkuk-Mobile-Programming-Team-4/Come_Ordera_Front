package com.example.iriordera.somin.app_ui_sell

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.OrderData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderStatusScreen(navController: NavHostController) {
    val appViewModel: AppViewModel = viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val coroutineScope = rememberCoroutineScope()

    // 주문 현황 로드
    LaunchedEffect(Unit) {
        appViewModel.store_id?.let {
            coroutineScope.launch {
                appViewModel.loadOrderStatus(it)
            }
        }
    }

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
                modifier = Modifier.padding(top = 60.dp)
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        appViewModel.store_id?.let {
                            appViewModel.loadOrderStatus(it)
                            Log.d("OrderStatusScreen", "Refresh complete")
                        }
                    }
                },
                modifier = Modifier.padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)
            ) {
                Text("새로고침")
            }

            if (appViewModel.orderList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "주문 내역이 없습니다.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxSize()
                        .background(Color.White)
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
    Card(
        modifier = Modifier
            .padding(top = 3.dp, bottom = 3.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White), // 카드 배경색
        elevation = CardDefaults.cardElevation(3.dp) // 카드 그림자 높이
    ) {
        Column(
            modifier = Modifier
                .padding(7.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "테이블 : ${order.table_number}, 주문 번호 : ${order.order_id}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            order.menu.forEach { menu ->
                Text(
                    text = "메뉴 : ${menu.name}, 가격 : ${menu.price}",
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.padding(top = 3.dp))
            }
        }
    }
}
