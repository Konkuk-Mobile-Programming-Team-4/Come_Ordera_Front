package com.example.final_project.app_consumer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.final_project.app_manage.AppViewModel
import com.example.final_project.app_manage.LocalNavGraphViewModelStoreOwner
import kotlinx.coroutines.launch

@Composable
fun PointScreen(navController: NavController) {
    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val coroutineScope = rememberCoroutineScope()

    // 유저 ID는 실제 구현에서는 로그인된 유저의 ID를 받아와야 합니다.
    val userId: Long = appViewModel.user_id

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            appViewModel.loadPoints(userId)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "포인트 현황",
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 80.dp, bottom = 100.dp)
        )
        Text(
            text = "사용 가능 : ${appViewModel.point}p",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {
            coroutineScope.launch {
                appViewModel.user_id.let{
                    appViewModel.loadPoints(userId)
                }
            }
        }) {
            Text("새로고침")
        }
    }
    Text(
        text = "${userId}p",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}