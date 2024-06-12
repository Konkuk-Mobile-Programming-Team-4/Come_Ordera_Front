package com.example.comeorder

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController, restaurantViewModel: RestaurantViewModel = viewModel()){
    NavHost(navController = navController, startDestination = RestaurantRoutes.ConsumerScreen.route ){
        composable(route = RestaurantRoutes.RestaurantScreen.route) {
            RestaurantScreen(navController, restaurantViewModel)
        }

        composable(
            route = RestaurantRoutes.RestaurantMenu.route
        ) {
            RestaurantMenu(navController, restaurantViewModel)
        }

        composable(route = RestaurantRoutes.ReviewScreen.route) {
            ReviewScreen(navController, restaurantViewModel)
        }

        composable(route = RestaurantRoutes.PostReview.route) {
            PostReview(navController, restaurantViewModel)
        }

        composable(route = RestaurantRoutes.ConsumerScreen.route) {
            ConsumerScreen(navController, restaurantViewModel)
        }
    }
}