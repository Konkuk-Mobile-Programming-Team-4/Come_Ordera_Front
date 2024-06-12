package com.example.iriordera.orderCheck

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.iriordera.NetworkResult
import com.example.iriordera.OrderViewModel
import com.example.iriordera.R

@Composable
fun OrderScreen(orderViewModel : OrderViewModel = hiltViewModel()) {
    val responseState by orderViewModel.orderResponse.collectAsState()

    orderViewModel.getOrderResponse()

    Column {
        when(responseState){
            is NetworkResult.Success -> {
                val orders = (responseState as NetworkResult.Success<Order?>).data
                if (orders != null) {
                    MainPage(orders)
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

@Composable
fun MainPage(orders: Order) {
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "나의 주문 현황", fontSize = 25.sp, fontWeight = FontWeight.Bold)
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
                Text(text = orders.table.toString(), fontSize = 25.sp)
            }
        }
    }
    for(menu in orders.menus){
        Row (modifier = Modifier
            .padding(10.dp)
            .width(400.dp), horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column (modifier = Modifier.padding(top = 5.dp)) {
                Text(text = menu.menu_id.toString())
                Text(text = menu.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Text(text = menu.price.toString() + "원", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
        }
    }
}
