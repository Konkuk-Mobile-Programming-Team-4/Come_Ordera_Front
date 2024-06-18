package com.example.iriordera.somin.app_ui_sell

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.iriordera.R
import com.example.iriordera.somin.app_manage.AppViewModel
import com.example.iriordera.somin.app_manage.LocalNavGraphViewModelStoreOwner
import com.example.iriordera.somin.app_manage.Routes
import com.example.iriordera.somin.storeHomePreview.OrderStatusPreview
import com.example.iriordera.somin.storeHomePreview.ReviewPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreHomeScreen(navController: NavHostController) {
    val appViewModel: AppViewModel =
        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val context = LocalContext.current

    BackHandler {
        (context as? Activity)?.finish()
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(234, 32, 90), Color(245, 102, 36))
                    )
                )
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "이리오더라",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily(Font(R.font.jalnan)),
                        modifier = Modifier.padding(bottom = 0.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(450.dp)
                .clickable {
                    navController.navigate(Routes.OrderStatus.route) {
                        popUpTo(Routes.StoreHome.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                }
        ) {
            OrderStatusPreview()
        }
        Divider()
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight()
                .clickable {
                    navController.navigate(Routes.Review.route){
                        popUpTo(Routes.StoreHome.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
        ) {
            ReviewPreview()
        }
    }
}