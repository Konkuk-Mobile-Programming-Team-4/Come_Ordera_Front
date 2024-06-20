package com.example.iriordera

import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.iriordera.dohyeon.RestaurantViewModel
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.BottomNavItem
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner

@Composable
fun BottomNavigationView(navController: NavHostController){
    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val items = listOf(
        BottomNavItem.ConsumerScreen,
        BottomNavItem.OrderCheck
    )
    var selectedColor by remember{
        mutableStateOf(Color(245, 102, 36))
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach{item ->
            BottomNavigationItem(
                icon = {
                    if((currentRoute == item.route + "?userid={consumerUserId}" )|| currentRoute == item.route){
                        selectedColor = Color(245, 102, 36)
                    } else{
                        selectedColor = Color.DarkGray
                    }
                    Icon(
                        imageVector = item.imageVector,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = selectedColor
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route + "?userid=${appViewModel.consumerUserId}"){
                        popUpTo(item.route){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavigationViewRestaurant(navController: NavHostController){
    val items = listOf(
        BottomNavItem.RestaurantScreen,
        BottomNavItem.RestaurantMenu,
        BottomNavItem.ReviewScreen
    )
    var selectedColor by remember{
        mutableStateOf(Color(245, 102, 36))
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach{item ->
            BottomNavigationItem(
                icon = {
                    if(currentRoute == item.route){
                        selectedColor = Color(245, 102, 36)
                    } else{
                        selectedColor = Color.DarkGray
                    }
                    Icon(
                        imageVector = item.imageVector,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = selectedColor
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(item.route){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}