package com.example.foodiee.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodiee.R
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.Course.Review
import com.example.foodiee.data.models.CourseDetails
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.BackButton
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun DishDescriptionScreen(navController: NavController, userViewModel: UserViewModel, courseViewModel: CourseViewModel, courseID: Int) {

    // Observe course details from the ViewModel
    val course by courseViewModel.courseDetail.collectAsState()

    // State to hold reviews
    val reviews = remember { mutableStateOf(emptyList<Review>()) }

    LaunchedEffect(courseID) { // Use courseID as the key for LaunchedEffect
        Log.e("CourseViewModel", "CourseID retrieved: $courseID")
        
        // Fetch course details
        courseViewModel.getCourseById(courseID)
        Log.e("CourseViewModel", "Course details retrieved: ${courseViewModel.courseDetail.value}")
        
        // Fetch reviews and update the state
        try {
            val fetchedReviews = courseViewModel.getReviewsByID(courseID)
            reviews.value = fetchedReviews
        } catch (e: Exception) {
            Log.e("CourseViewModel", "Error fetching reviews: ${e.message}")
        }
    }
    Scaffold(
        topBar = {
            BackButton(navController = navController)
        },
        bottomBar = {
            Footer(navController, userViewModel)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = course?.image,
                        contentDescription = "Dish Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 48.dp, end = 16.dp, bottom = 16.dp)
                            .size(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }

            item {
                course?.let {
                    Text(
                        text = it.title,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "$${ course?.price }",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.star_half_full),
                            contentDescription = "Star Icon",
                            modifier = Modifier
                                .padding(end = 1.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = "4.5",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            item {
                course?.let {
                    Text(
                        text = it.description,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            item{
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Ingredients: ",
                            modifier = Modifier.width(120.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        course?.ingredients?.let {
                            Text(
                                text = it.joinToString(", "),
                                color = FoodieeeColors.slate500
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Type of meal: ",
                            modifier = Modifier.width(120.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,

                            )
                        course?.let {
                            Text(
                                text = it.typeCourse,
                                color = FoodieeeColors.slate500
                            )
                        }
                    }
                }
            }
            item{
                Text("Reviews:", fontWeight = FontWeight.Medium, fontSize = 24.sp, modifier = Modifier.padding(start = 16.dp,top = 24.dp))
            }
            items(reviews.value){ item ->
                CommentCard(item)
            }
        }
    }
}

@Composable
fun CommentCard(review: Review){
    Card(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                AsyncImage(
                    model = review.user.profileImage ?: R.drawable.circle_user_round,
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = review.user.fullName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Icon(
                            painter = painterResource(R.drawable.star_half_full),
                            contentDescription = "Star Icon",
                            modifier = Modifier
                                .padding(end = 1.dp)
                                .size(12.dp)
                        )
                        Text(
                            text = review.rating.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
            Text(
                text = review.text,
                fontSize = 16.sp,
            )
            Text(
                text = "2 days ago",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}