package com.example.iriordera.minhyeok.QRScan

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.minhyeok.NetworkResult
import com.example.iriordera.minhyeok.OrderViewModel
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.ui.theme.IriorderaTheme

@Composable
fun QRStoreDetailScreen(qrResponse:String, navController: NavHostController) {

    val orderViewModel: OrderViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val infos = qrResponse.split(",")

    //서버에 정보 전송
    val responseState by orderViewModel.qrStoreDetailResponse.collectAsState()

    orderViewModel.qrStoreDetailResponse(infos[0].toLong(), infos[1].toInt())

    when(responseState){
        is NetworkResult.Success -> {
            val response = (responseState as NetworkResult.Success<QRStoreDetailResponse?>).data
            if (response != null) {

                //viewModel에 response 응답 값 넣기
                orderViewModel.insertStoreInfo(response.name, response.location)
                orderViewModel.setTableNumber(infos[1].toInt())
                orderViewModel.setStoreId(infos[0].toLong())

                for(menu in response.menus){
                    orderViewModel.insertMenuItem(menu)
                }

                Column {
                    StoreDetail(orderViewModel, infos[1], navController)
                }
            }
        }
        is NetworkResult.Error -> {
            val exception = (responseState as NetworkResult.Error).exception
            Log.e("MainScreen", "NetworkError : $exception")
        }

        null -> Log.e("MainScreen", "NetworkError : null")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDetail(viewmodel : OrderViewModel, table_num :String, navController: NavHostController) {

    var isOrderd by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(234, 32, 90),
                            Color(245, 102, 36)
                        )
                    )
                )) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                    title = {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.kukbab),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(top = 5.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                            )
                            Column(modifier = Modifier.padding(start = 10.dp, top = 5.dp)) {
                                Text(
                                    text = viewmodel.storeInfo.component1().name,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = viewmodel.storeInfo.component1().location,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(130.dp))
                            Column(
                                modifier = Modifier.width(300.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "테이블 번호",
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(top = 5.dp),
                                    color = Color.White
                                )
                                Text(text = table_num, fontSize = 25.sp, color = Color.White)
                            }
                        }
                    },
                )
            }
        }
    ) { paddingValues ->
        val scroll = rememberScrollState()
        var clicked by remember {
            mutableStateOf(false)
        }
        var menuText by remember {
            mutableStateOf("대표메뉴")
        }
        Column (modifier = Modifier
            .padding(paddingValues = paddingValues)
            .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp, start = 10.dp)){
                OutlinedButton(
                    onClick = { menuText = "대표메뉴" },
                    modifier = Modifier.padding(end = 10.dp),
                    border = BorderStroke(
                        width = 3.dp, // 테두리 두께 설정
                        color = Color(245, 50, 50)
                    ),
                ) {
                    Text(text = "대표메뉴", color = Color.Black)
                }
                OutlinedButton(
                    onClick = { menuText = "인기메뉴" },
                    modifier = Modifier.padding(end = 10.dp),
                    border = BorderStroke(
                        width = 3.dp, // 테두리 두께 설정
                        color = Color(245, 50, 50)
                    ),
                ) {
                    Text(text = "인기메뉴", color = Color.Black)
                }
                OutlinedButton(
                    onClick = { menuText = "세트메뉴" },
                    modifier = Modifier.padding(end = 7.dp),
                    border = BorderStroke(
                        width = 3.dp, // 테두리 두께 설정
                        color = Color(245, 50, 50)
                    ),
                ) {
                    Text(text = "세트메뉴", color = Color.Black)
                }
                IconButton(onClick = { clicked = !clicked }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "", modifier = Modifier
                        .padding(end = 5.dp), tint = if(clicked) Color.Red else Color.Black
                    )
                }
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "", modifier = Modifier.padding(end = 5.dp))
            }
            Text(text = menuText, fontSize = 30.sp, modifier = Modifier.padding(top = 10.dp, start = 10.dp), fontWeight = FontWeight.Bold)

            for(i in 0..<viewmodel.getSize()){

                val menuItem = viewmodel.getMenuItem(i)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)

                ){
                    Row (modifier = Modifier
                        .padding(10.dp)
                        .width(400.dp), horizontalArrangement = Arrangement.SpaceBetween){
                        Checkbox(checked = menuItem.isChecked, onCheckedChange = {viewmodel.changeChecked(i)}, modifier = Modifier.padding(top = 20.dp))
                        Column (modifier = Modifier.padding(top = 5.dp)) {
                            Text(text = menuItem.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = menuItem.introduction, fontSize = 15.sp, modifier = Modifier
                                    .width(200.dp)
                                    .padding(5.dp)
                            )
                        }
                        Text(text = menuItem.price.toString() + "원", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp, end = 12.dp))
                    }
                }
            }
            if(isOrderd){
                Text(text = "주문이 정상적으로 생성 되었습니다.", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
            else{
                Button(onClick = {
                    if (!viewmodel.emptyOrder()) {
                        viewmodel.createOrder()
                        isOrderd = true
                    }
                }) {
                    Text(text = "주문하기")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainScreen1Field() {
    IriorderaTheme {
//        StoreDetail(response = QRStoreDetailResponse(0,"","소민국밥","건대점", listOf(
//            QRMenu(1,"국밥", 8000, "피자를 왜먹냐? 그럴바엔 만원도 안되는 돈으로 든든한 국밥을 먹고 말지"),
//            QRMenu(1,"깍두기", 2000,"누가 국밥집에서 깍두기를 돈주고 사먹나? 하지만 이 깍두기는 장인이 30년 동안..."),
//            QRMenu(1,"수육", 12000,"국밥집에서 국밥만 먹은게 국룰이라지만 수육도 먹어주면 사장님 얼굴에 웃음꽃이 활짝"),
//            QRMenu(1,"소주", 4000,"소주 없이 국밥 어떻게 먹누!!!@@")
//        )), "5")
    }
}