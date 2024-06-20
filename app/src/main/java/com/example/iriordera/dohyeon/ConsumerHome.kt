package com.example.iriordera.dohyeon

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.BottomNavigationView
import com.example.iriordera.R
import com.example.iriordera.minhyeok.OrderViewModel
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConsumerScreen(
    navController:NavHostController,
    userid: Long?
){
    val orderViewModel: OrderViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val restaurantViewModel: RestaurantViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    val state = rememberLazyListState()

    var count by remember {
        mutableIntStateOf(0)
    }

    if (userid != null) {
        orderViewModel.setUserId(userid)
        appViewModel.loadPoints(userid)
    }

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = count) {
    }

    Scaffold (
        topBar = {
            TopAppBar(title = {
                var tableNum:String = orderViewModel.getTableNumber().toString() + "번 테이블"
                if(orderViewModel.getTableNumber()==0){
                    tableNum = ""
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = tableNum,
                        fontFamily = FontFamily(Font(R.font.hssantokki)),
                        fontSize = 30.sp
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
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .height(45.dp)
                    .border(
                        3.dp,
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(234, 32, 90),
                                Color(245, 102, 36)
                            )
                        ),
                        shape = RoundedCornerShape(15.dp)
                    ),
                containerColor = Color.White,
                onClick = {
                    if(orderViewModel.getOrderId() == 0L){
                        navController.navigate(Routes.QRScan.route)
                    }
                    else{
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("이미 생성한 주문이 있습니다.")
                        }
                    }
                }) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 15.dp,
                            end = 15.dp
                        ),
                    text = "QR 스캔하기",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
        }},
        bottomBar = {
            BottomNavigationView(navController = navController)
        }
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            SearchBar(
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(234, 32, 90), Color(245, 102, 36)
                            )
                        )
                    ),
                query = text,
                onQueryChange = {
                    text = it
                },
                colors = SearchBarDefaults.colors(
                    Color(0,0,0,0),
                    Color(255,255,255,255),
                    SearchBarDefaults.inputFieldColors(
                        focusedTextColor = Color(255, 255, 255, 255),
                        unfocusedPlaceholderColor = Color(255, 255, 255, 255),
                        unfocusedLeadingIconColor = Color(255, 255, 255, 255),
                        focusedTrailingIconColor = Color(255, 255, 255, 255)
                    )
                ),
                onSearch = {
                    count += restaurantViewModel.searchRequest(text)
                },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = {
                    Text(text = "가게 검색하기")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                text = ""
                                active = false
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                }
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = state
                ) {
                    itemsIndexed(
                        items = restaurantViewModel.restaurantList,
                        key = { _, restaurant -> restaurant.id }) { index: Int, item ->
                        if (text == item.name) {
                            Button(onClick = {
                                restaurantViewModel.detailRequest(item.id, navController)
                                appViewModel.orderingTableNum(item.id)
                            },
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                colors = ButtonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black,
                                    Color.White,Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 20.dp,
                                    vertical = 15.dp)
                                ) {
                                Row (modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start){
                                    Image(
                                        modifier = Modifier.padding(end = 23.dp),
                                        painter = painterResource(id = R.drawable.shop),
                                        contentDescription = null)
                                    Column(
                                        modifier = Modifier.padding(top = 10.dp)
                                    ) {
                                        Text(text = item.name + " ",
                                            fontSize = 30.sp,
                                            textAlign = TextAlign.Start)
                                        Text(text = item.location,
                                            fontSize = 15.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color(234, 32, 90),
                                    Color(245, 102, 36)
                                )
                            )
                        )
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(25.dp)
                    ){
                        Text(text = "나의 포인트",
                            color = Color.White)
                        var point:Long = appViewModel.point
                        var pointString = ""
                        var check = 0
                        while(point>0){
                            val temp = (point % 10).toString()
                            pointString="$temp$pointString"
                            point /= 10
                            check++
                            if(check%3 == 0 && point != 0L){
                                pointString =",$pointString"
                            }
                        }
                        if (appViewModel.point == 0L){
                            pointString = "0"
                        }
                        Text(text = pointString+"P"
                        , fontSize = 60.sp,
                            color = Color.White)
                    }
                }
            }
        }
    }
}

