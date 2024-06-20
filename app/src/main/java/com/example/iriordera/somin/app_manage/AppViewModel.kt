package com.example.iriordera.somin.app_manage

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel : ViewModel() {

    var storeList = mutableStateListOf<StoreData>()
    var menuList = mutableStateListOf<MenuData>()

    var orderList = mutableStateListOf<OrderData>() //
    var reviewList = mutableStateListOf<ReviewData>()

    var producerUserId: Long? = null // 판매자 아이디
    var consumerUserId: Long? = null // 소비자 아이디
    var storeId: Long? = null // 가게 아이디
    var reviewIds = mutableStateListOf<Long?>(null)
    var point: Long = 0 //포인트
    var usingTableNum = 0

    var isOrderStatusLoaded by mutableStateOf(false)
    var isLoggedIn by mutableStateOf(false)

    //뷰모델 초기화 함수
    fun resetViewModel() {
        storeList.clear()
        menuList.clear()
        //orderList.clear()
        //reviewList.clear()
    }

    //뷰모델 초기화
    init {
        resetViewModel()
    }

    //로그인 함수
    fun login(
        id: String,
        password: String,
        navController: NavHostController,
        snackBarHostState: SnackbarHostState,
    ) {
        val loginRequest = LoginRequest(id, password)

        val call = RetrofitInstance.loginApi.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse?.code == 502) {//판매자 화면
                        Log.d("AppViewModel", "${loginResponse.code}")

                        loginResponse.store_id?.let { store ->
                            storeId = store  // storeId 저장
                            Log.d("AppViewModel", "$storeId")
                        }
                        loginResponse.user_id?.let { user ->
                            producerUserId = user  // producerUserId 저장
                            Log.d("AppViewModel", "$producerUserId")
                        }

                        if (loginResponse.store_id == 0.toLong()) {
                            navController.navigate(Routes.SellWelcome.route)
                        } else {
                            isLoggedIn = true
                            navController.navigate(Routes.StoreHome.route) {
                                popUpTo(Routes.Login.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    } else if (loginResponse?.code == 503) {//소비자 화면
                        Log.d("AppViewModel", "consume${loginResponse.code}")

                        loginResponse.user_id?.let { consumer ->
                            consumerUserId = consumer
                            Log.d("AppViewModel", "$consumerUserId")
                        }
                        navController.navigate(BottomNavItem.ConsumerScreen.route + "?userid=${consumerUserId}")
                    } else if (loginResponse?.code == 504) {//회원정보 없음 알림
                        Log.d("AppViewModel", "go register: ${loginResponse.code}")
                        viewModelScope.launch {
                            snackBarHostState.showSnackbar("아이디가 없습니다. 회원가입을 진행해주세요.")
                        }
                    } else {
                        Log.d("AppViewModel", "fail")
                    }

                } else {
                    Log.d(
                        "AppViewModel", "Response unsuccessful: ${response.errorBody()?.string()}"
                    )
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to Login", t)
            }
        })
    }

    //회원 가입 함수
    fun register(
        name: String,
        id: String,
        password: String,
        role: String,
        navController: NavHostController,
    ) {
        val registerRequest = RegisterRequest(id, name, password, role)

        val call = RetrofitInstance.registerApi.register(registerRequest)

        call.enqueue(object : Callback<RegisterResponse> {

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {

                if (response.isSuccessful) {
                    val registerResponse = response.body()

                    when (registerResponse?.code) {
                        500 -> {
                            navController.navigate(Routes.Login.route)
                        }
                        501 -> {
                            Log.d("AppViewModel", "Response : ${registerResponse.code}")
                        }
                        else -> {
                            Log.d("AppViewModel", "Response : ${registerResponse?.code}")
                        }
                    }

                } else {
                    Log.d(
                        "AppViewModel",
                        "Response unsuccessful: ${response.errorBody()?.string()}"
                    )
                }

            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to Register", t)
            }


        })
    }

    // 가게 생성 함수
    fun createStore(
        userId: Long,
        name: String,
        location: String,
        table: Int,
        latitude: Double,
        longitude: Double,
        navController: NavHostController,
        snackBarHostState: SnackbarHostState,
    ) {
        val storeRequest = StoreRequest(userId, name, location, table, latitude, longitude)
        val call = RetrofitInstance.storeApi.createStore(storeRequest)

        call.enqueue(object : Callback<StoreResponse> {

            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                if (response.isSuccessful) {
                    val storeResponse = response.body()

                    if (storeResponse?.code == 600) {
                        storeResponse.store_id.let {
                            storeId = it  // store_id 저장
                            viewModelScope.launch {
                                snackBarHostState.showSnackbar("가게가 정상적으로 등록되었습니다.")
                            }
                            navController.navigate(Routes.StoreHome.route)

                        }
                    } else {
                        viewModelScope.launch {
                            snackBarHostState.showSnackbar("가게를 다시 등록해주세요.")
                        }
                    }
                } else {
                    viewModelScope.launch {
                        snackBarHostState.showSnackbar("서버와의 연결이 끊겼습니다.")
                    }
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                viewModelScope.launch {
                    snackBarHostState.showSnackbar("서버와의 연결에 실패했습니다.")
                }
            }

        })
    }

    // 주문 현황 함수
    fun loadOrderStatus(storeId: Long, snackBarHostState: SnackbarHostState) {

        val call = RetrofitInstance.orderApi.getOrderStatus(storeId)

        call.enqueue(object : Callback<OrderResponse> {

            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 601) {
                            orderList.clear()
                            orderList.addAll(it.orders)
                            viewModelScope.launch {
                                snackBarHostState.showSnackbar("주문 현황 로드 성공!", duration = SnackbarDuration.Short)
                            }
                        } else {
                            viewModelScope.launch {
                                snackBarHostState.showSnackbar("주문 현황 로드 실패" ,duration = SnackbarDuration.Short)
                            }
                        }
                    }
                } else {
                    viewModelScope.launch {
                        snackBarHostState.showSnackbar("주문 현황 로드 실패", duration = SnackbarDuration.Short)
                    }
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                viewModelScope.launch {
                    snackBarHostState.showSnackbar("서버 연결 실패", duration = SnackbarDuration.Short)
                }
            }

        })
    }

    // 메뉴 생성 함수
    fun createMenu(
        storeId: Long,
        name: String,
        price: Int,
        introduction: String,
        snackBarHostState: SnackbarHostState,
    ) {
        val menuRequest = MenuRequest(storeId, name, price, introduction)

        val call = RetrofitInstance.menuApi.createMenu(menuRequest)

        call.enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {

                if (response.isSuccessful) {
                    val menuResponse = response.body()

                    if (menuResponse?.code == 602) {
                        viewModelScope.launch {
                            snackBarHostState.showSnackbar("메뉴가 성공적으로 등록되었어요!", duration = SnackbarDuration.Short)
                        }
                    } else {
                        viewModelScope.launch {
                            snackBarHostState.showSnackbar("다시 시도해 주세요", duration = SnackbarDuration.Short)
                        }
                    }
                } else {
                    viewModelScope.launch {
                        snackBarHostState.showSnackbar("서버와의 연결에 실패했습니다.", duration = SnackbarDuration.Short)
                    }

                }
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                viewModelScope.launch {
                    snackBarHostState.showSnackbar("서버와의 연결에 실패했습니다.", duration = SnackbarDuration.Short)
                }
            }

        })
    }

    // 리뷰 로드 함수
    fun loadReviews(storeId: Long, snackBarHostState: SnackbarHostState) {

        val call = RetrofitInstance.reviewsGetApi.getReviews(storeId)

        call.enqueue(object : Callback<ReviewResponse> {

            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 603) {
                            reviewList.clear()
                            reviewIds.clear() // 리뷰 로드 시 기존 리뷰 ID 리스트 초기화
                            reviewList.addAll(it.reviews)
                            it.reviews.forEach { review -> reviewIds.add(review.review_id) } // 리뷰 ID 리스트 업데이트
                            viewModelScope.launch {
                                snackBarHostState.showSnackbar("리뷰 로드 성공!", duration = SnackbarDuration.Short)
                            }
                        } else {
                            viewModelScope.launch {
                                snackBarHostState.showSnackbar("리뷰 로드 실패", duration = SnackbarDuration.Short)
                            }
                        }
                    }
                } else {
                    viewModelScope.launch {
                        snackBarHostState.showSnackbar("리뷰 로드 실패" ,duration = SnackbarDuration.Short)
                    }
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                viewModelScope.launch {
                    snackBarHostState.showSnackbar("서버 연결 실패", duration = SnackbarDuration.Short)
                }
            }
        })
    }

    // 리뷰 삭제 함수
    fun deleteReview(reviewId: Long, snackBarHostState: SnackbarHostState) {

        val call = RetrofitInstance.reviewsDeleteApi.deleteReviews(reviewId)

        call.enqueue(object : Callback<DeleteReviewResponse> {

            override fun onResponse(call: Call<DeleteReviewResponse>, response: Response<DeleteReviewResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 604) {
                            reviewList.removeAll { review -> review.review_id == reviewId }
                            reviewIds.remove(reviewId) // 리뷰 ID 리스트 에서 제거
                            viewModelScope.launch {
                                snackBarHostState.showSnackbar("리뷰 삭제 성공!", duration = SnackbarDuration.Short)
                            }
                        } else {
                            viewModelScope.launch {
                                snackBarHostState.showSnackbar("리뷰 삭제 실패", duration = SnackbarDuration.Short)
                            }
                        }
                    }
                } else {
                    viewModelScope.launch {
                        snackBarHostState.showSnackbar("리뷰 삭제 실패", duration = SnackbarDuration.Short)
                    }
                }
            }

            override fun onFailure(call: Call<DeleteReviewResponse>, t: Throwable) {
                viewModelScope.launch {
                    snackBarHostState.showSnackbar("서버 연결 실패" ,duration = SnackbarDuration.Short)
                }
            }
        })
    }

    // 포인트 로드 함수
    fun loadPoints(userId: Long) {
        val call = RetrofitInstance.pointApi.loadPoints(userId)
        call.enqueue(object : Callback<PointResponse> {
            override fun onResponse(call: Call<PointResponse>, response: Response<PointResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 607) {
                            point = it.point
                            Log.d("AppViewModel", "Points loaded successfully: ${it.point}")
                        } else {
                            Log.d("AppViewModel", "Unexpected response code: ${it.code}")
                        }
                    }
                } else {
                    Log.d(
                        "AppViewModel",
                        "Failed to load points: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<PointResponse>, t: Throwable) {
                Log.e("AppViewModel", "Failed to load points", t)
            }
        })
    }

    fun orderingTableNum(storeId: Long) {

        val call = RetrofitInstance.orderApi.getOrderStatus(storeId)

        call.enqueue(object : Callback<OrderResponse> {

            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.code == 601) {
                            usingTableNum = it.orders.size
                        }
                    }
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Log.d("log", "연결 실패")
            }

        })
    }
}