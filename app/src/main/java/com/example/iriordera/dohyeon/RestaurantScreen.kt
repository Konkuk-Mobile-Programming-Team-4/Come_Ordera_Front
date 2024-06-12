package com.example.iriordera.dohyeon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.somin.app_manage.Routes
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun RestaurantScreen(navController:NavHostController, restaurantViewModel: RestaurantViewModel = viewModel()){
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 15.0, minZoom = 15.0)
        )
    }
    val latlng = LatLng(restaurantViewModel.restaurant[0].latitude, restaurantViewModel.restaurant[0].longitude)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(latlng, 15.0)
    }

    Column {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = restaurantViewModel.restaurant[0].name + " " + restaurantViewModel.restaurant[0].location)
        }
        Box (
            modifier = Modifier.fillMaxWidth()
        ) {
            NaverMap(
                properties = mapProperties,
                modifier = Modifier.height(300.dp),
                cameraPositionState = cameraPositionState
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(onClick = {
                navController.navigate(Routes.RestaurantMenu.route)
            }){
                Text(text = "가게 메뉴")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(onClick = {navController.navigate(Routes.ReviewScreen.route)}){
                Text(text = "가게 리뷰")
            }
        }
    }
}