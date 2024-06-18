package com.example.iriordera.somin.app_manage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.iriordera.dohyeon.ConsumerScreen
import com.example.iriordera.dohyeon.RestaurantMenu
import com.example.iriordera.dohyeon.RestaurantScreen
import com.example.iriordera.dohyeon.RestaurantViewModel
import com.example.iriordera.minhyeok.QRScan.QRCodeScannerExample
import com.example.iriordera.minhyeok.orderCheck.OrderScreen
import com.example.iriordera.somin.app_consumer.PointScreen
import com.example.iriordera.somin.app_ui.LoginScreen
import com.example.iriordera.somin.app_ui.RegisterScreen
import com.example.iriordera.somin.app_ui.SelectScreen
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
    object Store : Routes("Store")
    object StoreHome : Routes("StoreHome")
    object StoreRegister : Routes("StoreRegister")
    object OrderStatus : Routes("OrderStatus")
    object StoreMenuRegister : Routes("StoreMenuRegister")
    object Review : Routes("Review")
    object Point : Routes("Point")
    object QRScan : Routes("QRScan")
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
fun AppNaviGraph(
    navController: NavHostController,
    restaurantViewModel: RestaurantViewModel = viewModel(),
) {
    val navStoreOwner = rememberViewModelStoreOwner()

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        Scaffold(
            bottomBar = {
                if (navController.currentBackStackEntryAsState().value?.destination?.route in listOf(
                        Routes.StoreHome.route,
                        Routes.OrderStatus.route,
                        Routes.StoreMenuRegister.route,
                        Routes.Review.route
                    )
                ) {
                    BottomNavigationBar(navController)
                }
            }
        ) { contentPadding ->

            Column(
                modifier = Modifier.padding(contentPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Select.route
                ) {

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
                    composable(route = BottomNavItem.RestaurantScreen.route) {
                        RestaurantScreen(navController)
                    }
                    composable(route = BottomNavItem.RestaurantMenu.route) {
                        RestaurantMenu(navController)
                    }
                    composable(route = BottomNavItem.ReviewScreen.route) {
                        com.example.iriordera.dohyeon.ReviewScreen(
                            navController,
                            restaurantViewModel
                        )
                    }
                    composable(route = BottomNavItem.ConsumerScreen.route + "?userid={consumerUserId}",
                        arguments = listOf(
                            navArgument(name = "consumerUserId")
                         {
                        type = NavType.LongType }
                        )
                    ){
                        ConsumerScreen(navController = navController, userid = it.arguments?.getLong("consumerUserId"))
                    }
                    composable(route = Routes.QRScan.route) {
                        QRCodeScannerExample(navController)
                    }
                    composable(route = BottomNavItem.OrderCheck.route) {
                        OrderScreen(navController = navController)
                    }
                }
            }
        }


    }
}

sealed class BottomNavItem(val imageVector: ImageVector, val route:String)
{
    object ConsumerScreen: BottomNavItem(Icons.Default.Home,"Consumer")
    object OrderCheck: BottomNavItem(Icons.Default.ShoppingCart,"OrderCheck")
    object RestaurantMenu: BottomNavItem(Icons.Default.DateRange,"Menu")
    object ReviewScreen: BottomNavItem(Icons.Default.ThumbUp, "ReviewScreen")
    object RestaurantScreen: BottomNavItem(Icons.Default.Home,"Main")
}