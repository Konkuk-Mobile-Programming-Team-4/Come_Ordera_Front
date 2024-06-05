package com.example.final_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.final_project.app_manage.AppNaviGraph
import com.example.final_project.ui.theme.Final_ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Final_ProjectTheme {
                val navController = rememberNavController()
                AppNaviGraph(navController = navController)
            }
        }
    }
}