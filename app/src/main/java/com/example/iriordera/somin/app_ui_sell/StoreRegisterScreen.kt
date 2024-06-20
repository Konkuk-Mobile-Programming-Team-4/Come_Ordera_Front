package com.example.iriordera.somin.app_ui_sell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreRegisterScreen(navController: NavHostController) {
    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val focusRequester = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }
    val focusRequester5 = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var name: String by remember { mutableStateOf("") }
    var location: String by remember { mutableStateOf("") }
    var table: String by remember { mutableStateOf("") }
    var latitude: String by remember { mutableStateOf("") }
    var longitude: String by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_storefront_24),
                contentDescription = "Store Icon",
                tint = Color.Black,
                modifier = Modifier
                    .padding(top = 100.dp)
                    .size(100.dp)
            )

            Text(
                text = "어디에 계신가요?",
                fontFamily = FontFamily(Font(R.font.jalnan)),
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 0.dp)
            )

            Text(
                text = "가게 등록",
                fontFamily = FontFamily(Font(R.font.jalnan)),
                fontSize = 50.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 0.dp)
            )

            // 가게 이름 입력
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("가게 이름") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_storefront_24),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFEA205A), // 포커스 되었을 때 라벨 색상
                    unfocusedLabelColor = Color.Gray, // 포커스 되지 않았을 때 라벨 색상
                    focusedBorderColor = Color(0xFFEA205A), // 포커스 되었을 때 테두리 색상
                    unfocusedBorderColor = Color.Gray // 포커스 되지 않았을 때 테두리 색상
                ),
                shape = RoundedCornerShape(size = 50.dp),
                modifier = Modifier
                    .padding(top = 45.dp)
                    .fillMaxWidth(0.7f)
                    .focusRequester(focusRequester3)
                    .onFocusChanged {
                        if (!it.isFocused) {
                            keyboardController?.hide()
                        }
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            // 지점 입력
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("지점") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_map_24),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFEA205A),
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = Color(0xFFEA205A),
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(size = 50.dp),
                modifier = Modifier
                    .padding(top = 0.dp)
                    .fillMaxWidth(0.7f)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (!it.isFocused) {
                            keyboardController?.hide()
                        }
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            // 테이블 수 입력
            OutlinedTextField(
                value = table,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        table = it
                    } else {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("숫자만 입력 가능합니다.")
                        }
                    }
                },
                label = { Text("테이블 수") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .padding(vertical = 0.dp)
                    .fillMaxWidth(0.7f)
                    .focusRequester(focusRequester2)
                    .onFocusChanged {
                        if (!it.isFocused) {
                            keyboardController?.hide()
                        }
                    },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_table_restaurant_24),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFEA205A), // 포커스 되었을 때 라벨 색상
                    unfocusedLabelColor = Color.Gray, // 포커스 되지 않았을 때 라벨 색상
                    focusedBorderColor = Color(0xFFEA205A), // 포커스 되었을 때 테두리 색상
                    unfocusedBorderColor = Color.Gray // 포커스 되지 않았을 때 테두리 색상
                ),
                shape = RoundedCornerShape(size = 50.dp),
            )
            // 가게 위도 정보 입력
            OutlinedTextField(
                value = latitude,
                onValueChange = {
                    if (it.matches(Regex("^[0-9]*\\.?[0-9]*$"))) {
                        latitude = it
                    } else {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("숫자와 소수점만 입력 가능합니다.")
                        }
                    }
                },
                label = { Text("가게 위도") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .padding(vertical = 0.dp)
                    .fillMaxWidth(0.7f)
                    .focusRequester(focusRequester4)
                    .onFocusChanged {
                        if (!it.isFocused) {
                            keyboardController?.hide()
                        }
                    },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_zoom_out_map_24),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFEA205A), // 포커스 되었을 때 라벨 색상
                    unfocusedLabelColor = Color.Gray, // 포커스 되지 않았을 때 라벨 색상
                    focusedBorderColor = Color(0xFFEA205A), // 포커스 되었을 때 테두리 색상
                    unfocusedBorderColor = Color.Gray // 포커스 되지 않았을 때 테두리 색상
                ),
                shape = RoundedCornerShape(size = 50.dp),
            )
            //가게 경도 정보 입력
            OutlinedTextField(
                value = longitude,
                onValueChange = {
                    // 정규 표현식을 사용하여 숫자와 소수점만 허용
                    if (it.matches(Regex("^[0-9]*\\.?[0-9]*$"))) {
                        longitude = it
                    } else {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("숫자만 입력 가능합니다.")
                        }
                    }
                },
                label = { Text("가게 경도") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .padding(vertical = 0.dp)
                    .fillMaxWidth(0.7f)
                    .focusRequester(focusRequester5),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_zoom_out_map_24),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFEA205A),
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = Color(0xFFEA205A),
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(size = 50.dp),
            )
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(234, 32, 90), Color(245, 102, 36)
                            )
                        ),
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        when {
                            name.isEmpty() || location.isEmpty() || table.isEmpty() || latitude.isEmpty() || longitude.isEmpty() -> {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar("빈칸을 입력해주세요.")
                                }
                            }

                            else -> {
                                coroutineScope.launch {
                                    appViewModel.createStore(
                                        appViewModel.producerUserId!!,
                                        name,
                                        location,
                                        table.toInt(),
                                        latitude.toDouble(),
                                        longitude.toDouble(),
                                        navController,
                                        snackBarHostState
                                    )
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "가게 등록", color = Color.White, fontSize = 16.sp)
            }
        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}