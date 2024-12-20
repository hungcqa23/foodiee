package com.example.foodiee.data.models.Course

import com.example.foodiee.data.models.User.UserAPI.UserApiService
import retrofit2.Call
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


data class CourseResponse(
    val status: String,
    val data: List<Course>
)
data class CourseResponseDetail(
    val status: String,
    val data: Course
)

data class Course(
    val id: Int? = null,
    val title: String,
    val description: String,
    val typeCourse: String,
    val quantity: Int,
    val price: Double,
    val ingredients: List<String>,
    val image: String? = null
)

interface CourseApiService {
    @POST("courses")
    suspend fun createCourse(@Body course: Course): Course

    @GET("courses")
    suspend fun getAllCourses(): CourseResponse

    @GET("courses/{id}")
    suspend fun getCourseById(@Path("id") id: Int): CourseResponseDetail

    @PATCH("courses/{id}")
    suspend fun updateCourse(@Path("id") id: Int, @Body updatedCourse: Course): Course
}


fun getMockCourses(): List<Course> {
    return listOf(
        Course(
            id = 1,
            title = "Spaghetti Carbonara",
            description = "Classic Italian pasta with creamy sauce and pancetta. Fresh romaine lettuce with Caesar dressing and croutons.",
            typeCourse = "main_course",
            quantity = 10,
            price = 12.99,
            ingredients = listOf("Spaghetti", "Eggs", "Pancetta", "Parmesan Cheese", "Black Pepper"),
            image = "https://www.allrecipes.com/thmb/SZjdgaXhmkrRNLoOvdxuAktwk3E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/228443-authentic-pho-DDMFS-4x3-0523f6531ccf4dbeb4b5bde52e007b1e.jpg"
        ),
        Course(
            id = 2,
            title = "Caesar Salad",
            description = "Fresh romaine lettuce with Caesar dressing and croutons.",
            typeCourse = "Food",
            quantity = 15,
            price = 8.99,
            ingredients = listOf("Romaine Lettuce", "Croutons", "Parmesan Cheese", "Caesar Dressing"),
            image = "https://www.allrecipes.com/thmb/SZjdgaXhmkrRNLoOvdxuAktwk3E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/228443-authentic-pho-DDMFS-4x3-0523f6531ccf4dbeb4b5bde52e007b1e.jpg"
        ),
        Course(
            id = 3,
            title = "Chocolate Cake",
            description = "Rich chocolate cake with layers of creamy frosting.",
            typeCourse = "Dessert",
            quantity = 5,
            price = 6.49,
            ingredients = listOf("Flour", "Sugar", "Cocoa Powder", "Eggs", "Butter"),
            image = "https://www.allrecipes.com/thmb/SZjdgaXhmkrRNLoOvdxuAktwk3E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/228443-authentic-pho-DDMFS-4x3-0523f6531ccf4dbeb4b5bde52e007b1e.jpg"
        ),
        Course(
            id = 4,
            title = "Margarita Pizza",
            description = "Classic pizza with fresh tomato sauce, mozzarella, and basil.",
            typeCourse = "Food",
            quantity = 20,
            price = 9.99,
            ingredients = listOf("Pizza Dough", "Tomato Sauce", "Mozzarella", "Basil"),
            image = "https://www.allrecipes.com/thmb/SZjdgaXhmkrRNLoOvdxuAktwk3E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/228443-authentic-pho-DDMFS-4x3-0523f6531ccf4dbeb4b5bde52e007b1e.jpg"
        ),
        Course(
            id = 5,
            title = "Fresh Orange Juice",
            description = "Refreshing orange juice made with freshly squeezed oranges.",
            typeCourse = "Drink",
            quantity = 25,
            price = 4.99,
            ingredients = listOf("Oranges"),
            image = "https://www.allrecipes.com/thmb/SZjdgaXhmkrRNLoOvdxuAktwk3E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/228443-authentic-pho-DDMFS-4x3-0523f6531ccf4dbeb4b5bde52e007b1e.jpg"
        )
    )
}
fun getMockCourseResponse(): CourseResponse {
    return CourseResponse(
        status = "success",
        data = getMockCourses()
    )
}
fun getMockCourseResponseDetail(): CourseResponseDetail {
    return CourseResponseDetail(
        status = "success",
        data = getMockCourses()[0]
    )
}