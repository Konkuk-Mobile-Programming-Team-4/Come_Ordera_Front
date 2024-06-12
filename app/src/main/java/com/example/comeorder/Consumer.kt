package com.example.comeorder

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConsumerScreen(
    navController:NavHostController,
    restaurantViewModel: RestaurantViewModel = viewModel ()){
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

    LaunchedEffect(key1 = count) {
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.07f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "이리오더라",
                fontFamily = FontFamily(Font(R.font.hsyeoleum)),
                fontSize = 20.sp,
                style = TextStyle(
                    brush = Brush.horizontalGradient(listOf(Color(234,32,90), Color(245,102,36)))
                )
            )
        }

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(listOf(Color(234,32,90), Color(245,102,36)))
                ),
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                count += restaurantViewModel.searchRequest(text)
            },
            active = active,
            onActiveChange = { active = it },
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
                        }) {
                            Text(text = item.name + " " + item.location)
                        }
                    }
                }
            }
        }


        Row (
            modifier = Modifier.fillMaxHeight(0.6f)
        ){

        }

        Row (
            modifier = Modifier.fillMaxHeight(0.2f)
        ){
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(RestaurantRoutes.PostReview.route)
                }
            ) {
                Text(
                    text = "리뷰 작성하기"
                )
            }
        }
    }
}