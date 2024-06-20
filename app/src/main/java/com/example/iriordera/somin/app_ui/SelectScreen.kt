package com.example.iriordera.somin.app_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.Routes
import kotlinx.coroutines.delay

@Composable
fun SelectScreen(navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()

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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_storefront_24),
            contentDescription = "Store Icon",
            tint = Color.White,
            modifier = Modifier
                .padding(top =270.dp)
                .size(100.dp) // 아이콘 위치를 조정할 수 있습니다
        )

        Text(
            text = "음식을 더 손쉽고 편하게",
            fontFamily = FontFamily(Font(R.font.jalnan)),
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 0.dp)
        )

        Text(
            text = "이리오더라",
            fontFamily = FontFamily(Font(R.font.jalnan)),
            fontSize = 50.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 0.dp)
        )
    }
    LaunchedEffect(key1 = Unit) {
        delay(1500)
        navController.navigate(Routes.Login.route){
            popUpTo(Routes.Select.route){
                inclusive = true
            }
            launchSingleTop = true
        }
    }

}