package com.example.iriordera.somin.app_ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes

@Composable
fun SelectScreen(navController: NavHostController) {
    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    Column(
        modifier = Modifier.fillMaxSize().background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "이리오더라",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            color = Color.Red)

        Button(
            onClick = {
                navController.navigate(Routes.Login.route)
            },
            modifier = Modifier
                .padding(10.dp)
                .height(70.dp)
                .width(300.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color.White),
            shape = CutCornerShape(0.dp),
            border = BorderStroke(2.dp, Color.Black),
        ) {
            Text(
                text = "로그인",
                modifier = Modifier,
                color = Color.Black,
                fontSize = 30.sp,
            )
        }
        Button(
            onClick = {
                navController.navigate(Routes.Register.route)
            },
            modifier = Modifier
                .padding(10.dp)
                .height(70.dp)
                .width(300.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color.LightGray),
            shape = CutCornerShape(0.dp),
            border = BorderStroke(2.dp, Color.Black),
        ) {
            Text(
                text = "회원가입",
                modifier = Modifier,
                color = Color.Black,
                fontSize = 30.sp,
            )
        }
    }
}
