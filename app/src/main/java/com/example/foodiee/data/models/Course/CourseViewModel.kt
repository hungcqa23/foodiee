package com.example.foodiee.data.models.Course

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiee.data.models.Order
import com.example.foodiee.data.models.RetrofitInstance
import com.example.foodiee.data.models.Role
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class CourseViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _courseDetail = MutableStateFlow<Course?>(null)
    val courseDetail: StateFlow<Course?> = _courseDetail

    private val _cart = MutableStateFlow<CartInfo?>(null)
    val cart: StateFlow<CartInfo?> = _cart

    private val _orders = MutableStateFlow<List<OrderRespond>>(emptyList())
    val orders: StateFlow<List<OrderRespond>> = _orders

    fun createCourse(course: Course, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitInstance.CourseApi.createCourse(course)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllCourses() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.CourseApi.getAllCourses()
            //    val response = getMockCourseResponse()
                _courses.value = response.data
                Log.d("CourseViewModel", "lay dc course roi:\n ${response.status} \n ${response.data}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CourseViewModel", "bug get all roi: ${e.localizedMessage}")
            }
        }
    }

    fun getCourseById(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.CourseApi.getCourseById(id)
            //    val response = getMockCourseResponseDetail()
                Log.d("CourseViewModel", "lay dc course roi:${response.status} \n ${response.data}")
                _courseDetail.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CourseViewModel", "bug ID roi: ${e.localizedMessage}")

            }
        }
    }

    fun updateCourse(id: Int, updatedCourse: Course, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("CourseViewModel", "course moi: $updatedCourse")
                RetrofitInstance.CourseApi.updateCourse(id, updatedCourse)
                Log.d("CourseViewModel", "update anh nay: ${updatedCourse.image}")
                onSuccess()
            } catch (e: Exception) {
                Log.e("CourseViewModel", "course moi: $updatedCourse")
                Log.e("CourseViewModel", "bug update roi: ${e.localizedMessage}")
                Log.e("CourseViewModel", "update anh nay: ${updatedCourse.image}")

                e.printStackTrace()
            }
        }
    }

    fun flushCourseDetail() {
        _courseDetail.value = null
    }

    fun uploadFile(file: File, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Prepare the file part for uploading
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

                // Make the API call
                val response = RetrofitInstance.CourseApi.uploadFile(multipartBody)

                // Pass the URL to the onSuccess callback
                onSuccess(response.url)
            } catch (e: Exception) {
                e.printStackTrace()
                onError("Exception: ${e.localizedMessage}")
            }
        }
    }

    fun postReview(courseId: Int, text: String, rating: Int, token: String) {
        viewModelScope.launch {
            if (token.isEmpty()) {
                Log.e("review", "token empty")
            }

            try {
                val reviewRequest = ReviewRequest(text = text, rating = rating, courseId = courseId)
                RetrofitInstance.CourseApi.postReview(
                    token = "Bearer $token",
                    id = courseId,
                    review = reviewRequest
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getReviewsByID(id:Int): List<Review>{
        return try{
            val respond = RetrofitInstance.CourseApi.getReviews(id)
            if(respond.status == "success"){
                respond.data
            }
            else{
                throw Exception("No Success")
            }
        } catch(e: Exception){
            throw Exception("server error")
        }
    }
    suspend fun getCardNumber(token:String): Int{
        return try{
            val respond = RetrofitInstance.CourseApi.getCartNumber("Bearer $token")
            respond.second
        }catch(e:Exception){
            0
        }
    }

    fun addToCart(courses: List<Pair<Int, Int>>, token: String) {
        viewModelScope.launch {
            try {
                val cartItems = courses.map { (courseId, quantity) ->
                    CartItm(courseId, quantity)
                }
                val cartRequest = CartRequest(items = cartItems)

                RetrofitInstance.CourseApi.addToCart("Bearer $token", cartRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCartInfo(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.CourseApi.getCartInfo("Bearer $token")
                _cart.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getOrders(token: String, param: String) {
        viewModelScope.launch {
            try {
                val standard = param.lowercase()
                val response = RetrofitInstance.CourseApi.getOrders("Bearer $token", standard)

                Log.d("orderfatal", "API Response: ${response.data}")

                // Filter out invalid orders

                // Log the filtered result for debugging

                _orders.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Helper function to validate orders
    private fun isValidOrder(order: Order): Boolean {
        return !(
                order.orderId.isNullOrEmpty() ||
                        order.customerName.isNullOrEmpty() ||
                        order.orderStatus == null || // Assuming this is an enum or non-nullable type
                        order.orderDetails.isNullOrEmpty() ||
                        order.price.isNullOrEmpty() ||
                        order.time.isNullOrEmpty()
                )
    }

    fun createOrder(token: String, cartID: Int, onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                Log.d("create", "creating order")
                val request = CreateOrderRequest(cartID)
                val response = RetrofitInstance.CourseApi.createOrder("Bearer $token",request)
                Log.d("create", "created order")
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}