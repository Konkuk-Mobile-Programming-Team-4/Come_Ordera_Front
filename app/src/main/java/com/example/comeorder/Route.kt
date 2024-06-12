package com.example.comeorder

sealed class RestaurantRoutes(val route:String) {
    object ConsumerScreen:RestaurantRoutes("Consumer")
    object RestaurantScreen:RestaurantRoutes("Main")
    object RestaurantMenu:RestaurantRoutes("Menu")
    object ReviewScreen:RestaurantRoutes("Review")
    object PostReview:RestaurantRoutes("PostReview")
}