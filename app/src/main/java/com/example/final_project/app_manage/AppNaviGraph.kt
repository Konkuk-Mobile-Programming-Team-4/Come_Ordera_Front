package com.example.final_project.app_manage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.final_project.app_consumer.PointScreen
import com.example.final_project.app_ui_sell.OrderStatusScreen
import com.example.final_project.app_ui_sell.ReviewScreen
import com.example.final_project.app_ui_sell.StoreHomeScreen
import com.example.final_project.app_ui_sell.StoreMenuRegisterScreen
import com.example.final_project.app_ui_sell.StoreRegisterScreen
import com.example.final_project.app_ui_sell.WelcomeScreen

sealed class Routes(val route: String) {
    object Welcome : Routes("Welcome")
    object StoreHome : Routes("StoreHome")
    object StoreRegister : Routes("StoreRegister")
    object OrderStatus : Routes("OrderStatus")
    object StoreMenuRegister : Routes("StoreMenuRegister")
    object Review : Routes("Review")
    object Point : Routes("Point")
}

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner = staticCompositionLocalOf<ViewModelStoreOwner> {
    error("Undefined ViewModelStoreOwner")
}

@Composable
fun AppNaviGraph(navController: NavHostController) {
    val navStoreOwner = rememberViewModelStoreOwner()

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = Routes.Review.route) {
            composable(route = Routes.Welcome.route) {
                WelcomeScreen(navController)
            }
            composable(route = Routes.StoreRegister.route) {
                StoreRegisterScreen(navController)
            }
            composable(route = Routes.StoreHome.route) {
                StoreHomeScreen(navController)
            }
            composable(route = Routes.OrderStatus.route) {
                OrderStatusScreen(navController)
            }
            composable(route = Routes.StoreMenuRegister.route) {
                StoreMenuRegisterScreen(navController)
            }
            composable(route = Routes.Review.route) {
                ReviewScreen(navController)
            }
            composable(route = Routes.Point.route) {
                PointScreen(navController)
            }
        }
    }
}
