package com.example.foodiee.data.models.Course

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CourseViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _courseDetail = MutableStateFlow<Course?>(null)
    val courseDetail: StateFlow<Course?> = _courseDetail

    fun createCourse(course: Course, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.createCourse(course)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllCourses() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllCourses()
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
                val response = RetrofitInstance.api.getCourseById(id)
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
                RetrofitInstance.api.updateCourse(id, updatedCourse)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}