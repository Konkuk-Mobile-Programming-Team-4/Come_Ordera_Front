package com.example.iriordera.somin.app_ui_sell

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberDismissState
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
import com.example.iriordera.somin.app_manage.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(navController: NavHostController) {

    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        appViewModel.storeId?.let {
            coroutineScope.launch {
                appViewModel.loadReviews(appViewModel.storeId!!, snackBarHostState)
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
            .background(Color.White)
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
                            "고객님의 리뷰",
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
            if (appViewModel.reviewList.isEmpty()) {
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
                    appViewModel = appViewModel,
                    listState = listState,
                    coroutineScope = coroutineScope,
                    snackBarHostState = snackBarHostState
                )
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
                        appViewModel.loadReviews(
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
            modifier = Modifier
                .align(Alignment.TopCenter)
                .width(150.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReviewList(
    appViewModel: AppViewModel,
    listState: LazyListState,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 0.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .background(Color.White),
        state = listState
    ) {
        itemsIndexed(
            appViewModel.reviewList,
            key = { _, review -> review.review_id }) { _, review ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        coroutineScope.launch {
                            appViewModel.deleteReview(review.review_id, snackBarHostState)
                            appViewModel.reviewList.remove(review)
                        }
                        true
                    } else {
                        false
                    }
                }
            )
            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val color = when (dismissState.dismissDirection) {
                        DismissDirection.EndToStart -> Color.Red
                        else -> Color.Transparent
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .background(color),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "삭제 아이콘",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                dismissContent = {
                    Column(
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxWidth()
                            .background(Color.White)// 각 리뷰 항목의 배경색
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                            fontFamily = FontFamily(Font(R.font.jalnan)),
                            text = review.user_name,
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
            )
        }
    }
}

@Composable
fun StarRating(rating: Int) {
    Row(
        modifier = Modifier.padding(top = 1.dp, start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(rating) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFEA205A),
                modifier = Modifier.size(20.dp)
            )
        }
        repeat(5 - rating) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}