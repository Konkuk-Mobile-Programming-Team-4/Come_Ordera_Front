package com.example.iriordera.somin.storeHomePreview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_ui_sell.StarRating
import kotlinx.coroutines.launch

@Composable
fun ReviewPreview() {

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
        ReviewListPreview(
            appViewModel = appViewModel,
            listState = listState
        )
    }
}

@Composable
fun ReviewListPreview(
    appViewModel: AppViewModel,
    listState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(335.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .background(Color.White),
        state = listState
    ) {
        itemsIndexed(
            appViewModel.reviewList,
            key = { _, review -> review.review_id }) { _, review ->
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
    }
}