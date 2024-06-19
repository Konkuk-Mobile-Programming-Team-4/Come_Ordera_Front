package com.example.iriordera.dohyeon

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.BottomNavigationView
import com.example.iriordera.BottomNavigationViewRestaurant
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_ui_sell.ReviewList
import com.example.iriordera.somin.app_ui_sell.StarRating
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReviewScreen(navController: NavHostController){
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }


    val restaurantViewModel:RestaurantViewModel =
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

                if (restaurantViewModel.reviews.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "아직 달린 리뷰가 없어요!",
                            fontFamily = FontFamily(Font(R.font.jalnan)),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                } else {
                    ReviewList(
                        restaurantViewModel = restaurantViewModel,
                        listState = listState,
                        coroutineScope = coroutineScope,
                        snackBarHostState = snackBarHostState
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReviewList(
    restaurantViewModel: RestaurantViewModel,
    listState: LazyListState,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .background(Color.White),
        state = listState
    ) {
        itemsIndexed(
            restaurantViewModel.reviews,
            key = { _, review -> review.id }) { _, review ->
            Column(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .background(Color.White)// 각 리뷰 항목의 배경색
            ) {
                Text(
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                    fontFamily = FontFamily(Font(R.font.jalnan)),
                    text = review.name,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.W400
                )
                StarRating(review.star)
                Text(
                    modifier = Modifier.padding(top = 15.dp, start = 20.dp),
                    text = review.content,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Divider(
                    color = Color.Black, thickness = 1.dp
                )
            }
        }
    }
}