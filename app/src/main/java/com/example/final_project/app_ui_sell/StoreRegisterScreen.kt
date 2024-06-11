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
fun StoreRegisterScreen(navController: NavHostController) {
    val appViewModel: AppViewModel = viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val context = LocalContext.current

    var user_id by remember { mutableLongStateOf(0) }
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var table by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    val snackBarHostState = remember { SnackbarHostState() }
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
                text = "가게 등록",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("가게 이름") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("지점") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = table,
                onValueChange = { table = it },
                label = { Text("테이블 번호") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = latitude,
                onValueChange = { latitude = it },
                label = { Text("가게 위도") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = longitude,
                onValueChange = { longitude = it },
                label = { Text("가게 경도") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            )

            Button(
                onClick = {
                    if (name.isNotEmpty() && location.isNotEmpty() && table.isNotEmpty() && latitude.isNotEmpty() && longitude.isNotEmpty()) {
                        val tableNumber = table.toIntOrNull()
                        val latitude = latitude.toDoubleOrNull()
                        val longitude = longitude.toDoubleOrNull()

                        if (tableNumber == null) {
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar("테이블 번호는 숫자여야 합니다.")
                            }
                        } else if (latitude == null || longitude == null) {
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar("위도와 경도는 숫자여야 합니다.")
                            }
                        } else {
                            if (appViewModel.checkStoreExists(user_id, name, location, table.toInt(), latitude, longitude)) {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar("이미 존재하는 가게입니다.")
                                }
                            } else {
                                appViewModel.createStore(
                                    user_id = 2,
                                    name = name,
                                    location = location,
                                    table = table.toInt(),
                                    latitude = latitude,
                                    longitude = longitude
                                )
                                coroutineScope.launch {
                                    val result = snackBarHostState.showSnackbar("가게 등록 요청이 전송되었습니다.")
                                    if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                                        appViewModel.store_id?.let { store_id ->
                                            Toast.makeText(context, "Store ID: $store_id", Toast.LENGTH_LONG).show()
                                        }
                                        navController.navigate(Routes.StoreHome.route)
                                    }
                                }
                            }
                        }
                    } else {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("모든 필드를 채워주세요.")
                        }
                    }
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = "등록")
            }
        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
