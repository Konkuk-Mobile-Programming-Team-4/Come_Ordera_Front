package com.example.iriordera.somin.app_ui_sell

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.OrderData
import com.example.iriordera.somin.app_manage.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderStatusScreen(navController: NavHostController) {
    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    LaunchedEffect(0) {
        if (!appViewModel.isOrderStatusLoaded) {
            appViewModel.storeId?.let {
                coroutineScope.launch {
                    appViewModel.loadOrderStatus(
                        appViewModel.storeId!!,
                        snackBarHostState
                    )
                }
            }
        }
    }

    BackHandler {
        navController.navigate(Routes.StoreHome.route) {
            popUpTo(Routes.StoreHome.route) {
                inclusive = true
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(234, 32, 90), Color(245, 102, 36))
                        )
                    )
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "주문 요청 현황",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W400,
                            fontFamily = FontFamily(Font(R.font.jalnan)),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.chevron_left),
                            contentDescription = "Menu Icon",
                            modifier = Modifier
                                .padding(end = 5.dp, bottom = 5.dp)
                                .size(35.dp)
                                .clickable {
                                    navController.navigate(Routes.StoreHome.route) {
                                        popUpTo(Routes.StoreHome.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                        )
                    }
                )
            }
            if (appViewModel.orderList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "아직 주문이 안 들어 왔어요!",
                        fontFamily = FontFamily(Font(R.font.jalnan)),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f)
                        .background(Color.White),
                    state = listState
                ) {
                    items(appViewModel.orderList) { order ->
                        Column {
                            OrderItem(order)
                            Divider(color = Color.Gray, thickness = 1.dp)
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 40.dp, end = 30.dp),
            containerColor = Color(0xFFEA205A),
            onClick = {
                appViewModel.storeId?.let {
                    coroutineScope.launch {
                        appViewModel.loadOrderStatus(
                            appViewModel.storeId!!,
                            snackBarHostState
                        )
                    }
                }
            }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_refresh_24),
                tint = Color.White,
                contentDescription = "go to top",
                modifier = Modifier.size(30.dp)
            )
        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun OrderItem(order: OrderData) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White), // 카드 배경색
        shape = RoundedCornerShape(0.dp)// 카드 그림자 높이
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 15.dp, bottom = 5.dp, start = 20.dp),
                text = "테이블 : ${order.table_number}, 주문 번호 : ${order.order_id}",
                fontFamily = FontFamily(Font(R.font.jalnan)),
                fontSize = 25.sp,
                fontWeight = FontWeight.Light
            )
            //Spacer(modifier = Modifier.padding(top = 10.dp))
            order.menu.forEach { menu ->
                Text(
                    modifier = Modifier.padding(top = 5.dp, start = 20.dp),
                    text = "${menu.name}  -  ₩${menu.price}",
                    fontSize = 18.sp
                )
                //Spacer(modifier = Modifier.padding(top = 5.dp))
            }
        }
    }
}