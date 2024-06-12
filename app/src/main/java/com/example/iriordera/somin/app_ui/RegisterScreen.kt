package com.example.iriordera.somin.app_ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController) {
    val appViewModel: AppViewModel = viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
    val context = LocalContext.current

    var userID by remember { mutableStateOf("") }
    var userPasswd by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isConsumerChecked by remember { mutableStateOf(true) }
    var isSellerChecked by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf("customer") }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.d("RegisterViewModel", appViewModel.toString())
            Text(
                text = "회원가입",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("이름") }
            )

            OutlinedTextField(
                value = userID,
                onValueChange = { userID = it },
                label = { Text("아이디") }
            )

            OutlinedTextField(
                value = userPasswd,
                onValueChange = { userPasswd = it },
                label = { Text("비밀번호") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isConsumerChecked,
                    onCheckedChange = {
                        isConsumerChecked = it
                        if (it) {
                            isSellerChecked = false
                            role = "customer"
                        } else {
                            role = ""
                        }
                    }
                )
                Text(text = "소비자")

                Checkbox(
                    checked = isSellerChecked,
                    onCheckedChange = {
                        isSellerChecked = it
                        if (it) {
                            isConsumerChecked = false
                            role = "manager"
                        } else {
                            role = ""
                        }
                    }
                )
                Text(text = "판매자")
            }

            Button(onClick = {
                when {
                    name.isEmpty() -> {
                        coroutineScope.launch {
                            Toast.makeText(context, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    userID.isEmpty() -> {
                        coroutineScope.launch {
                            Toast.makeText(context, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    userPasswd.isEmpty() -> {
                        coroutineScope.launch {
                            Toast.makeText(context, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    !(isConsumerChecked xor isSellerChecked) -> {
                        coroutineScope.launch {
                            Toast.makeText(context, "고객 유형을 선택해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    else -> {
                        coroutineScope.launch {
                            appViewModel.register(userID, name, userPasswd, role, navController)
                            navController.navigate(Routes.Login.route)
                        }
                    }
                }
            }) {
                Text(text = "회원가입")
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
