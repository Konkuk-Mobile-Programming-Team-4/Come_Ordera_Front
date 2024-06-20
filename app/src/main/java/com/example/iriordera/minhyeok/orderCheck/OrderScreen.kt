package com.example.iriordera.minhyeok.orderCheck

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.BottomNavigationView
import com.example.iriordera.R
import com.example.iriordera.minhyeok.NetworkResult
import com.example.iriordera.minhyeok.OrderViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(navController: NavHostController) {

    val orderViewModel: OrderViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val responseState by orderViewModel.orderResponse.collectAsState()

    orderViewModel.getOrderResponse()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "주문 현황",
                        fontFamily = FontFamily(Font(R.font.hssantokki)),
                        fontSize = 35.sp
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "이리오더라",
                        fontFamily = FontFamily(Font(R.font.hsyeoleum)),
                        fontSize = 20.sp,
                        style = TextStyle(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color(234, 32, 90),
                                    Color(245, 102, 36)
                                )
                            )
                        )
                    )

                }
            })
        },
        bottomBar = {
            BottomNavigationView(navController = navController)
        }
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            when(responseState){
                is NetworkResult.Success -> {
                    val orders = (responseState as NetworkResult.Success<Order?>).data
                    if (orders != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ){
                            MainPage(orders, orderViewModel)
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Button(
                                    onClick = {
                                        navController.navigate(Routes.PostReview.route)
                                    },
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(40.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    colors = ButtonColors(
                                        containerColor = Color(245, 102, 36),
                                        contentColor = Color.White,
                                        Color.White, Color.White
                                    )
                                ) {
                                    Text(
                                        text = "결 제 하 기", fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                    val exception = (responseState as NetworkResult.Error).exception
                    Log.e("OrderScreen", "NetworkError : $exception")
                }

                null -> Log.e("OrderScreen", "NetworkError : null")
            }
        }
    }
}

@Composable
fun MainPage(orders: Order, orderViewModel: OrderViewModel) {
    Column {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.kukbab), contentDescription = "", modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                    contentScale = ContentScale.Crop,)
                Column (modifier = Modifier.padding(start = 10.dp)){
                    Text(text = orders.name, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(130.dp))
                Column (modifier = Modifier.width(300.dp), horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "테이블 번호", fontSize = 13.sp, modifier = Modifier.padding(top = 5.dp))
                    Text(text = orderViewModel.getTableNumber().toString(), fontSize = 25.sp)
                }
            }
        }
        for(menu in orders.menus){
            Row (modifier = Modifier
                .padding(10.dp)
                .width(400.dp), horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column (modifier = Modifier.padding(top = 5.dp)) {
                    Text(text = menu.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = menu.price.toString() + "원", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
            }
        }
    }
}
