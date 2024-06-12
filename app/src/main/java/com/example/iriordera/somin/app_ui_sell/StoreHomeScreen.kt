package com.example.iriordera.somin.app_ui_sell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes

@Composable
fun StoreHomeScreen(navController: NavHostController) {

    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                navController.navigate(Routes.OrderStatus.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(300.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
            shape = CutCornerShape(0.dp)
        ) {
            Text(
                text = "주문 현황 확인",
                modifier = Modifier,
                color = Color.Black,
                fontSize = 50.sp,
            )
        }

        Button(
            onClick = {
                navController.navigate(Routes.StoreMenuRegister.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(200.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
            shape = CutCornerShape(0.dp)
        ) {
            Text(
                text = "메뉴 등록",
                modifier = Modifier,
                color = Color.Black,
                fontSize = 50.sp,
            )
        }

        Button(
            onClick = {
                navController.navigate(Routes.Review.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(200.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
            shape = CutCornerShape(0.dp)
        ) {
            Text(
                text = "리뷰 관리",
                modifier = Modifier,
                color = Color.Black,
                fontSize = 50.sp,
            )
        }

    }
}
