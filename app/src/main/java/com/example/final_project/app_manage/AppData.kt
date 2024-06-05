package com.example.final_project.app_manage

data class StoreData(var user_id: Long, var name: String, var location: String, var table: Int, var latitude: Double, var longitude: Double)
data class MenuData(var store_id: Long, var name: String, var price: Int, var introduction: String)
// 가게 생성 요청 데이터 클래스
data class StoreRequest(val user_id: Long, val name: String, val location: String, val table: Int, val latitude: Double, val longitude: Double)

// 가게 생성 응답 데이터 클래스
data class StoreResponse(val code: Int, val message: String, val store_id: Long)

// 주문 현황 데이터 클래스
data class OrderData(val order_id: Long, val table_number: Int, val user_id: Long, val menu: List<MenuItem>)

// 메뉴 데이터 클래스
data class MenuItem(val name: String, val price: Int)

// 주문 현황 응답 데이터 클래스
data class OrderResponse(val code: Int, val message: String, val orders: List<OrderData>)

// 메뉴 생성 요청 데이터 클래스
data class MenuRequest(val store_id: Long, val name: String, val price: Int, val introduction: String)

// 메뉴 생성 응답 데이터 클래스
data class MenuResponse(val code: Int, val message: String)

// 리뷰 조회 응답 데이터 클래스
data class ReviewResponse(val code: Int, val message: String, val reviews: List<ReviewData>)

data class ReviewData(val review_id: Long, val user_name: String, val content: String, val star: Int)
// 리뷰 삭제 응답 데이터 클래스
data class DeleteReviewResponse(val code: Int, val message: String)