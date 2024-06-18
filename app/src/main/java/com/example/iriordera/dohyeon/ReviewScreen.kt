package com.example.iriordera.dohyeon

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.BottomNavigationView
import com.example.iriordera.BottomNavigationViewRestaurant
import com.example.iriordera.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReviewScreen(navController: NavHostController, restaurantViewModel: RestaurantViewModel = viewModel()){
    val state = rememberLazyListState()

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
                        text = "리뷰",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(   //리뷰 화면
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 0.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(234, 32, 90),
                                Color(245, 102, 36)
                            )
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .wrapContentSize(Alignment.Center)
            ){
                Text(
                    text = "고객님의 한마디",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = Color.White,
                        fontSize = 25.sp
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
            ) {
//            Image(
//                painter = backgroundImage,
//                contentDescription = "Background Image",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(shape = RoundedCornerShape(0.dp)),
//                contentScale = ContentScale.FillBounds
//            )

                LazyColumn(
                    contentPadding = PaddingValues(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    state = state
                ) {
                    itemsIndexed(
                        items = restaurantViewModel.reviews,
                        key = { _, reviews -> reviews.id }
                    ) {
                            index: Int, item ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                        ) {
                            Box(    //이름
                                modifier = Modifier
                                    .background(Color.Transparent, shape = RoundedCornerShape(24.dp))
                                    .padding(8.dp)
                                    .wrapContentWidth(Alignment.Start)
                                    .wrapContentHeight(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = item.name + " 고객님",
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            Box(    //별점
                                modifier = Modifier
                                    .background(Color(0xFF19E5E6), shape = RoundedCornerShape(24.dp))
                                    .padding(8.dp)
                                    .wrapContentWidth(Alignment.End)
                                    .wrapContentHeight(Alignment.CenterVertically)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "★",
                                        color = Color.Yellow
                                    )
                                    Text(text = item.star.toString() + "점!")
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ) {
                            Box(    //리뷰 내용
                                modifier = Modifier
                                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                                    .wrapContentWidth(Alignment.Start)
                                    .wrapContentHeight(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = item.content,
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                }

            }

        }
    }
}