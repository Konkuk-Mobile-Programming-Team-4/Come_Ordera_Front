package com.example.iriordera.somin.app_manage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.comeorder.ConsumerScreen
import com.example.iriordera.dohyeon.PostReview
import com.example.iriordera.dohyeon.RestaurantMenu
import com.example.iriordera.dohyeon.RestaurantScreen
import com.example.iriordera.dohyeon.RestaurantViewModel
import com.example.iriordera.minhyeok.QRScan.QRCodeScannerExample
import com.example.iriordera.minhyeok.orderCheck.OrderScreen
import com.example.iriordera.somin.app_ui.LoginScreen
import com.example.iriordera.somin.app_ui.RegisterScreen
import com.example.iriordera.somin.app_ui.SelectScreen
import com.example.iriordera.somin.app_consumer.PointScreen
import com.example.iriordera.somin.app_ui_sell.OrderStatusScreen
import com.example.iriordera.somin.app_ui_sell.ReviewScreen
import com.example.iriordera.somin.app_ui_sell.StoreHomeScreen
import com.example.iriordera.somin.app_ui_sell.StoreMenuRegisterScreen
import com.example.iriordera.somin.app_ui_sell.StoreRegisterScreen
import com.example.iriordera.somin.app_ui_sell.WelcomeScreen

sealed class Routes(val route: String) {
    object Select : Routes("Select")
    object Register : Routes("Register")
    object Login : Routes("Login")
    object SellWelcome : Routes("SellWelcome")
    object StoreHome : Routes("StoreHome")
    object StoreRegister : Routes("StoreRegister")
    object OrderStatus : Routes("OrderStatus")
    object StoreMenuRegister : Routes("StoreMenuRegister")
    object Review : Routes("Review")
    object Point : Routes("Point")
    object ConsumerScreen: Routes("Consumer")
    object RestaurantScreen: Routes("Main")
    object RestaurantMenu: Routes("Menu")
    object ReviewScreen: Routes("ReviewScreen")
    object PostReview: Routes("PostReview")
    object QRScan: Routes("QRScan")
    object OrderCheck: Routes("OrderCheck")
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
fun AppNaviGraph(navController: NavHostController, restaurantViewModel: RestaurantViewModel = viewModel()) {
    val navStoreOwner = rememberViewModelStoreOwner()

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = Routes.Select.route) {

            composable(route = Routes.Select.route) {
                SelectScreen(navController)
            }
            composable(route = Routes.Register.route) {
                RegisterScreen(navController)
            }
            composable(route = Routes.Login.route) {
                LoginScreen(navController)
            }
            composable(route = Routes.SellWelcome.route) {
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
            composable(route = Routes.RestaurantScreen.route) {
                RestaurantScreen(navController, restaurantViewModel)
            }
            composable(route = Routes.RestaurantMenu.route) {
                RestaurantMenu(navController, restaurantViewModel)
            }
            composable(route = Routes.ReviewScreen.route) {
                com.example.iriordera.dohyeon.ReviewScreen(navController, restaurantViewModel)
            }
            composable(route = Routes.ConsumerScreen.route) {
                ConsumerScreen(navController, restaurantViewModel)
            }
            composable(route = Routes.QRScan.route) {
                QRCodeScannerExample(navController)
            }
            composable(route = Routes.OrderCheck.route) {
                OrderScreen()
            }
            composable(route = BottomNavItem.PostReview.route) {
                PostReview(navController, restaurantViewModel)
            }

        }
    }
}

sealed class BottomNavItem(val Icon: Int, val route:String) {
    object PostReview:Routes("PostReview")
}