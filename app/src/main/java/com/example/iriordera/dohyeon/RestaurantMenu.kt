package com.example.iriordera.dohyeon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.BottomNavigationView
import com.example.iriordera.BottomNavigationViewRestaurant
import com.example.iriordera.R
import com.example.iriordera.minhyeok.OrderViewModel
import com.example.iriordera.minhyeok.orderCheck.Order
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantMenu(navController:NavHostController){
    val restaurantViewModel: RestaurantViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

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
                        text = "메뉴",
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
            BottomNavigationViewRestaurant(navController = navController)
        }
    ){
//        Column(
//            modifier = Modifier.fillMaxSize()
//                .padding(it)
//        ) {
//            Column(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                LazyColumn(
//                    contentPadding = PaddingValues(all = 12.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp),
//                    state = state
//                ) {
//                    itemsIndexed(
//                        items = restaurantViewModel.menus,
//                        key = { _, menus -> menus.name }) { index: Int, item ->
//                        Row (
//                            modifier = Modifier.fillMaxWidth()
//                        ){
//                            Text(text = item.name)
//                            Text(text = item.price.toString())
//                            Text(text = item.introduction)
//                        }
//                    }
//                }
//            }
//        }
        paddingValues ->


        val scroll = rememberScrollState()

        var clicked by remember {
            mutableStateOf(false)
        }
        var menuText by remember {
            mutableStateOf("대표메뉴")
        }

        Box(modifier = Modifier.fillMaxSize()){
            Column (modifier = Modifier
                .padding(paddingValues = paddingValues)
                .verticalScroll(scroll),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
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
                                        text = restaurantViewModel.restaurant[0].name,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = restaurantViewModel.restaurant[0].location,
                                        fontSize = 12.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        },
                    )
                }
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

                for(i in 0..<restaurantViewModel.menus.size){

                    val menuItem = restaurantViewModel.menus[i]

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    ){
                        Row (modifier = Modifier
                            .padding(10.dp)
                            .width(400.dp), horizontalArrangement = Arrangement.SpaceBetween){
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
                Spacer(modifier = Modifier.fillMaxWidth().height(50.dp))
            }
            Box(modifier = Modifier.fillMaxSize()) {
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
                    shape = RoundedCornerShape(10.dp),
                    onClick = { navController.navigate(Routes.QRScan.route) }) {
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
                }
            }
        }
    }
}