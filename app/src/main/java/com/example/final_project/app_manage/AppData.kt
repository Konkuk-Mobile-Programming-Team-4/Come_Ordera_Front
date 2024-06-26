package com.example.final_project.app_manage

data class StoreData(var user_id: Long, var name: String, var location: String, var table: Int, var latitude: Double, var longitude: Double)
data class MenuData(var store_id: Long, var name: String, var price: Int, var introduction: String)
data class ReviewData(val review_id: Long, val user_name: String, val content: String, val star: Int)
data class OrderData(val order_id: Long, val table_number: Int, val user_id: Long, val menu: List<MenuItem>)

data class StoreRequest(val user_id: Long, val name: String, val location: String, val table: Int, val latitude: Double, val longitude: Double)
data class StoreResponse(val code: Int, val message: String, val store_id: Int)

data class MenuRequest(val store_id: Long, val name: String, val price: Int, val introduction: String)
data class MenuResponse(val code: Int, val message: String)
data class MenuItem(val name: String, val price: Int)

data class OrderResponse(val code: Int, val message: String, val orders: List<OrderData>)

data class ReviewResponse(val code: Int, val message: String, val reviews: List<ReviewData>)
data class DeleteReviewResponse(val code: Int, val message: String)

data class PointResponse(val code: Int, val message: String, val point : Long)
