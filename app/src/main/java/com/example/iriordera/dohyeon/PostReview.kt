package com.example.iriordera.dohyeon

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.R
import com.example.iriordera.minhyeok.OrderViewModel
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.BottomNavItem
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostReview(navController: NavHostController){
    val restaurantViewModel: RestaurantViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val orderViewModel: OrderViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    var review by remember{
        mutableStateOf("")
    }
    var rating by remember{
        mutableStateOf(0f)
    }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "리뷰 작성",
                        fontFamily = FontFamily(Font(R.font.hssantokki)),
                        fontSize = 30.sp
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "이리오더라",
                        fontFamily = FontFamily(Font(R.font.hsyeoleum)),
                        fontSize = 20.sp,
                        style = TextStyle(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color(234, 32, 90),
                                    Color(245, 102, 36)
                                )
                            )
                        )
                    )

                }
            })
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "별점등록 (${rating.toInt()}/5)",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp, top = 30.dp, bottom = 5.dp
                    ),
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            StarRatingBar(
                maxStars = 5,
                rating = rating,
                onRatingChanged = {
                    rating = it
                }
            )

            Text(
                text = "리뷰",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp, top = 30.dp
                    ),
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = review,
                onValueChange = { review = it },
                label = { "" },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFEA205A), // 포커스 되었을 때 라벨 색상
                    unfocusedLabelColor = Color.Gray, // 포커스 되지 않았을 때 라벨 색상
                    focusedBorderColor = Color(0xFFEA205A), // 포커스 되었을 때 테두리 색상
                    unfocusedBorderColor = Color.Gray // 포커스 되지 않았을 때 테두리 색상
                ),
                shape = RoundedCornerShape(size = 20.dp),
                modifier = Modifier
                    .width(375.dp)
                    .height(400.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (!it.isFocused) {
                            keyboardController?.hide()
                        }
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(20.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        navController.navigate(BottomNavItem.ConsumerScreen.route + "?userid=${appViewModel.consumerUserId}")
                        restaurantViewModel.postReview(content = review,star = rating.toInt().toString(), user_id = orderViewModel.getUserId(), store_id = orderViewModel.getStoreId())
                        orderViewModel.getPoint()

                              },
                    modifier = Modifier
                        .width(85.dp)
                        .height(50.dp)
                    ,
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonColors(
                        containerColor = Color(245, 102, 36),
                        contentColor = Color.White,
                        Color.White, Color.White
                    )
                ) {
                    Text(
                        text = "등록", fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (25f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(245, 102, 36) else Color.Gray
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}