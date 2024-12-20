package com.example.foodiee.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodiee.Navigation.Routes
import com.example.foodiee.R
import com.example.foodiee.data.models.Course.Course
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.CourseDetails
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun HomeScreen(navController: NavController, userViewModel: UserViewModel, courseViewModel: CourseViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("main_course") }  // For managing selected category
    val allCourse by courseViewModel.courses.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("CourseViewModel", "dang lay course")
        courseViewModel.getAllCourses()
    }

    Log.d("CourseViewModel", "Lay xong roi ${allCourse}")
    Scaffold(
        bottomBar = {
            Footer(navController, userViewModel)
        },
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                // Header with Delivery info
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column {
                        Row {
                            Text("Delivery to:", modifier = Modifier.padding(end = 4.dp))
                            Text("John Doe", fontWeight = FontWeight.SemiBold)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(painterResource(R.drawable.locationpin), contentDescription = "Location", modifier = Modifier.padding(end = 4.dp))
                            Text("123 Main Street, District 1, HCMC")
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    BadgedBox(
                        badge = {
                            Badge {
                                Text("3")
                            }
                        },
                        modifier = Modifier.clickable { navController.navigate(Routes.CartScreen.route) }
                    ) {
                        Icon(
                            painterResource(R.drawable.cart),
                            contentDescription = "Cart"
                        )
                    }
                }
            }
            item {
                SearchBox({ searchQuery = it })
            }
            item {
                if (searchQuery.isNotEmpty()) {
                    Text("Result for: ${searchQuery}", fontSize = 24.sp, modifier = Modifier.padding(start = 16.dp, top = 16.dp).height(48.dp))
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    ) {
                        MenuCategory(
                            icon = ImageVector.vectorResource(R.drawable.mainmenu),
                            text = "Main",
                            selected = selectedCategory == "main_course",
                            onClick = { selectedCategory = "main_course" }
                        )
                        MenuCategory(
                            icon = ImageVector.vectorResource(R.drawable.appertizer),
                            text = "Appetizer",
                            selected = selectedCategory == "appetizer",
                            onClick = { selectedCategory = "appetizer" }
                        )
                        MenuCategory(
                            icon = ImageVector.vectorResource(R.drawable.dessertmenu),
                            text = "Dessert",
                            selected = selectedCategory == "dessert",
                            onClick = { selectedCategory = "dessert" }
                        )
                        MenuCategory(
                            icon = ImageVector.vectorResource(R.drawable.drinkmenu),
                            text = "Drink",
                            selected = selectedCategory == "drink",
                            onClick = { selectedCategory = "drink" }
                        )
                    }
                }
            }

            val filteredCourses = allCourse.filter {
                it.typeCourse == selectedCategory || selectedCategory == "All"
            }

            val searchedCourses = allCourse.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }

            if(searchedCourses.isEmpty()){
                item{
                    Box(modifier = Modifier.fillMaxSize()){
                        Text("Oops! We don't currently serve that dish :(", fontSize = 64.sp, lineHeight = 64.sp ,modifier = Modifier.fillMaxSize())
                    }
                }
            }
            else{
                items(if (searchQuery.isNotEmpty()) searchedCourses else filteredCourses) { dish ->
                    CourseDetailCard(course = dish, onIncrement = {}, navController)
                }
            }
        }
    }
}

@Composable
fun CourseDetailCard(
    course: Course,
    onIncrement: (Int) -> Unit,
    navController: NavController
) {
    var count by remember { mutableIntStateOf(0) }
    Log.d("CourseViewModel", "CourseID o home: ${course.id}")

    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
            .clickable {
                navController.navigate(Routes.DishDescriptionScreen.createRoute(course.id!!))
            }
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max) // Ensure the height of the Row matches its tallest child
        ) {
            AsyncImage(
                model = course.image,
                contentDescription = "item image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight() // Match the Row's height
                    .width(102.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .fillMaxHeight() // Allow the Column to take up remaining space
            ) {
                Text(text = course.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(
                    text = course.typeCourse,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(horizontal = 8.dp)
                )
                Text(text = course.price.toString(), fontSize = 14.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .clickable { if (count > 0) count--; onIncrement(count) }
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .border(1.dp, FoodieeeColors.slate300, RoundedCornerShape(4.dp))
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "minus icon",
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(count.toString(), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Box(
                    modifier = Modifier
                        .clickable { count++; onIncrement(count) }
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .border(1.dp, FoodieeeColors.slate300, RoundedCornerShape(4.dp))
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.plus),
                        contentDescription = "plus icon",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MenuCategory(
    icon: ImageVector,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = if (selected) Color.Black else Color.Gray
        )
        Text(
            text = text,
            fontSize = 16.sp,
            letterSpacing = (-1).sp,
            color = if (selected) Color.Black else Color.Gray,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
fun SearchBox(
    onValueChange: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // Create a search box with the specified layout and style
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it; onValueChange(searchQuery) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(64.dp)
            .border(1.8.dp, Color.Black, RoundedCornerShape(180.dp))
            .clip(RoundedCornerShape(180.dp)),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.magnify),
                contentDescription = "Search Icon"
            )
        },
        placeholder = {
            Column(verticalArrangement = Arrangement.Center) {
                Text("Craving for something?", fontWeight = FontWeight.Normal, fontSize = 16.sp)
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        )
    )
}

fun getMockCourses(): List<CourseDetails> {
    return listOf(
        CourseDetails(
            title = "Spaghetti Carbonara",
            location = "Rome",
            price = "$12.99",
            rating = 4.5,
            description = "Delicious spaghetti with creamy carbonara sauce.",
            ingredients = listOf("Spaghetti", "Eggs", "Cheese", "Pancetta"),
            mealType = "Main"
        ),
        CourseDetails(
            title = "Sushi Roll",
            location = "Tokyo",
            price = "$18.50",
            rating = 4.8,
            description = "Fresh sushi rolls with premium fish.",
            ingredients = listOf("Sushi rice", "Salmon", "Tuna", "Seaweed"),
            mealType = "Appetizer"
        ),
        CourseDetails(
            title = "Tacos al Pastor",
            location = "Mexico City",
            price = "$10.75",
            rating = 4.7,
            description = "Traditional Mexican tacos with marinated pork.",
            ingredients = listOf("Pork", "Pineapple", "Cilantro", "Tortillas"),
            mealType = "Appetizer"
        ),
        CourseDetails(
            title = "Pad Thai",
            location = "Bangkok",
            price = "$9.99",
            rating = 4.3,
            description = "Classic Thai stir-fried noodles with peanuts and lime.",
            ingredients = listOf("Rice noodles", "Shrimp", "Peanuts", "Tamarind"),
            mealType = "Dessert"
        ),
        CourseDetails(
            title = "Cheese Burger",
            location = "USA",
            price = "$8.99",
            rating = 4.6,
            description = "Juicy cheeseburger with lettuce, tomato, and pickles.",
            ingredients = listOf("Beef patty", "Cheese", "Lettuce", "Tomato", "Pickles"),
            mealType = "Drink"
        )
    )
}