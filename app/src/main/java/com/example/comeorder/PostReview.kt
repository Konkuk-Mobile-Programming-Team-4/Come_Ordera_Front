package com.example.comeorder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun PostReview(navController: NavHostController, restaurantViewModel: RestaurantViewModel = viewModel()){
    var review by remember{
        mutableStateOf("")
    }
    var star by remember{
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = review,
            onValueChange = {
                review = it
            },
            placeholder = { Text(text = "리뷰를 작성해주세요!") }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = star,
            onValueChange = {star = it},
            placeholder = { Text(text = "별") }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                restaurantViewModel.postReview(review, star) }
        ) {
            Text(text = "작성 완료")
        }
    }
}