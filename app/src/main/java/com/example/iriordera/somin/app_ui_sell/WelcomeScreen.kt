package com.example.iriordera.somin.app_ui_sell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.Routes
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(234, 32, 90), Color(245, 102, 36)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_storefront_24),
            contentDescription = "Store Icon",
            tint = Color.White,
            modifier = Modifier
                .padding(top =70.dp)
                .size(200.dp) // 아이콘 위치를 조정할 수 있습니다
        )
        Text(
            text = "새로 오신걸 환영해요!",
            fontFamily = FontFamily(Font(R.font.jalnan)),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 90.dp)
        )
        Text(
            text = "가게를 먼저 등록해주세요!",
            fontFamily = FontFamily(Font(R.font.jalnan)),
            fontSize = 27.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp)
        )

        LaunchedEffect(key1 = Unit) {
            delay(1500)
            navController.navigate(Routes.StoreRegister.route){
                popUpTo(Routes.SellWelcome.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }

    }
}
