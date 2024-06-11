package com.example.final_project.app_ui_sell

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.final_project.app_manage.AppViewModel
import com.example.final_project.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.final_project.app_manage.Routes

@Composable
fun WelcomeScreen(navController: NavHostController) {
    val appViewModel: AppViewModel = viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val userID = "소민 "
    Column(modifier = Modifier.fillMaxSize()
        .clickable {navController.navigate(Routes.StoreRegister.route)},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Text(
            text = "${/*appViewModel.userID*/userID}님 환영합니다.",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Text(
            text = "가게를 먼저 등록해주세요!!",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        /*LaunchedEffect(key1 = Unit) {//n초 후에 이동, 키값 상수 : 변화X, state 주는 것도 가능
            delay(3000)
            //appViewModel.loginStatus.value = true //로그인 완료시
            //navController.navigate(Routes.StoreRegister.route) //가게 등록 화면으로 이동
            navController.navigate(Routes.StoreHome.route)
        }*/
        /*Button(onClick = {
            //appViewModel.loginStatus.value = true
            navController.navigate(Routes.StoreRegister.route)
        }) {
            Text(text = "생성하러가기")
        }*/

        /*if(appViewModel.loginStatus.value){//유저 정보 일치할 시
            navController.navigate(Routes.StoreRegister.route){
                popUpTo(Routes.Login.route){
                    inclusive = true//로그인 포함 제거
                }
                launchSingleTop = true
            }
        }*/
    }
}
