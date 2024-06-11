package com.example.final_project.app_ui_sell

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.final_project.app_manage.AppViewModel
import com.example.final_project.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.final_project.app_manage.Routes
import kotlinx.coroutines.launch

@Composable
fun StoreMenuRegisterScreen(navController: NavHostController) {
    val appViewModel: AppViewModel = viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val context = LocalContext.current

    var store_id by remember { mutableLongStateOf(0) }
    var menuName by remember { mutableStateOf("") }
    var menuPrice by remember { mutableStateOf("") }
    var menuIntroduction by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "메뉴 등록",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            OutlinedTextField(
                value = menuName,
                onValueChange = { menuName = it },
                label = { Text("메뉴 이름") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = menuPrice,
                onValueChange = { menuPrice = it },
                label = { Text("메뉴 가격") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = menuIntroduction,
                onValueChange = { menuIntroduction = it },
                label = { Text("소개말") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Button(
                onClick = {
                    if (menuPrice.isNotEmpty()) {
                        val price = menuPrice.toIntOrNull()

                        if (price == null) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("가격은 숫자여야 합니다.")
                            }
                        } else {
                            val storeId = appViewModel.store_id
                            if (appViewModel.checkMenuExists(storeId!!, menuName, menuPrice.toInt(), menuIntroduction)) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("이미 존재하는 메뉴입니다.")
                                }
                            } else {
                                appViewModel.createMenu(
                                    storeId = storeId,
                                    name = menuName,
                                    price = menuPrice.toInt(),
                                    introduction = menuIntroduction
                                )
                                coroutineScope.launch {
                                    val result = snackbarHostState.showSnackbar("메뉴 등록 요청이 전송되었습니다.")
                                    if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                                        Toast.makeText(
                                            context,
                                            "Store ID: $storeId, 메뉴: $menuName",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigate(Routes.StoreHome.route)
                                    }
                                }
                            }
                        }
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("모든 필드를 채워주세요.")
                        }
                    }
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = "등록")
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
