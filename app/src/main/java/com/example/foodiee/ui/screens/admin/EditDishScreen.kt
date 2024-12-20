package com.example.foodiee.ui.screens.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodiee.Navigation.Routes
import com.example.foodiee.R
import com.example.foodiee.data.models.Course.Course
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.Course.getMockCourses
import com.example.foodiee.data.models.MockInventoryItems
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.*
import com.example.foodiee.ui.theme.Orange500

@Composable
fun EditDishScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    courseViewModel: CourseViewModel
) {
    val courses by courseViewModel.courses.collectAsState()
    LaunchedEffect(Unit) {
        courseViewModel.getAllCourses()
    }
    Scaffold(
        topBar = {
            Column {
                ConfigNavigationHeader(ConfigSubPage.EDIT_DISH, navController)
                TabBar(navController)
            }
        },
        bottomBar = { Footer(navController, userViewModel) }
    ) { padding ->
        LazyColumn(
            
            modifier = Modifier.padding(padding)
        ) {
            items(courses) { item ->
                DishItemCard(
                    item,
                    navController
                )
            }
        }
    }
}

@Composable
fun DishItemCard(
    course: Course,
    navController: NavController
) {
    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min) // Ensure the height of the Row matches its tallest child
        ) {
            AsyncImage(
                model = course.image,
                contentDescription = "item image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f) // Allow the Column to take up remaining space
            ) {
                Text(text = course.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = course.typeCourse,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(horizontal = 8.dp)
                )
                Text(text = "$${course.price}", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Quantity: ${course.quantity}", fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp)) // Add some space between the content and the action icons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly, // Ensure equal spacing
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight() // Match the Row's height
            ) {
                Icon(
                    painter = painterResource(R.drawable.pen),
                    contentDescription = "edit icon",
                    modifier = Modifier
                        .clickable { navController.navigate(Routes.AddItemScreen.createRoute(course.id.toString())) }
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(8.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.trash),
                    contentDescription = "delete icon",
                    modifier = Modifier
                        .clickable { }
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(8.dp)
                )
            }
        }
    }
}
