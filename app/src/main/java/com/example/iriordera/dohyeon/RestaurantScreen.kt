package com.example.iriordera.dohyeon

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.BottomNavigationView
import com.example.iriordera.BottomNavigationViewRestaurant
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.BottomNavItem
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(navController:NavHostController){
    val restaurantViewModel: RestaurantViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 15.0, minZoom = 15.0)
        )
    }
    val latlng = LatLng(restaurantViewModel.restaurant[0].latitude, restaurantViewModel.restaurant[0].longitude)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(latlng, 15.0)
    }

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
                        text = restaurantViewModel.restaurant[0].name,
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
            modifier = Modifier.fillMaxSize().padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier.width(350.dp)
                    .height(270.dp)
                    .padding(top = 20.dp)
                    .border(
                        3.dp,
                        shape = RoundedCornerShape(15.dp),
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(234, 32, 90),
                                Color(245, 102, 36)
                            )
                        )
                    )
            ) {
                NaverMap(
                    properties = mapProperties,
                    modifier = Modifier.height(300.dp),
                    cameraPositionState = cameraPositionState
                ){
                    Marker(
                        state = MarkerState(position = LatLng(restaurantViewModel.restaurant[0].latitude, restaurantViewModel.restaurant[0].longitude)),
                        captionText = restaurantViewModel.restaurant[0].name
                    )
                }
            }
        }
    }

}