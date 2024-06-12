package com.example.iriordera

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.iriordera.QRScan.QRCodeScannerExample
import com.example.iriordera.orderCheck.OrderScreen

sealed class Routes(val route:String){
    object QRCodeScanner:Routes("QRCodeScanner")
    object OrderScreen:Routes("OrderScreen")
}

@SuppressLint("RestrictedApi", "StateFlowValueCalledInComposition")
@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController=navController, startDestination = Routes.QRCodeScanner.route){

        composable(route=Routes.QRCodeScanner.route){
            QRCodeScannerExample(navController)
        }

        composable(route=Routes.OrderScreen.route){
            OrderScreen()
        }
    }
}