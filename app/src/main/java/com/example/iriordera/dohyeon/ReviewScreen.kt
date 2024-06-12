package com.example.iriordera.dohyeon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.iriordera.dohyeon.RestaurantViewModel

@Composable
fun ReviewScreen(navController: NavController, restaurantViewModel: RestaurantViewModel = viewModel()){
    val state = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = state
        ) {
            itemsIndexed(
                items = restaurantViewModel.reviews,
                key = { _, reviews -> reviews.id }) { index: Int, item ->
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(text = item.name)
                    Text(text = item.content)
                    Text(text = item.star.toString())
                }
            }
        }
    }
}