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
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun HomeScreen(
    navController: NavController,
    userAPIViewModel: UserAPIViewModel,
    courseViewModel: CourseViewModel,
    userAPIViewModel: UserAPIViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("main_course") } // For managing selected category
    val allCourse = courseViewModel.courses.collectAsState().value
    var cartNumber by remember { mutableStateOf(0) }
    var selectedCourses by remember { mutableStateOf(mutableMapOf<Int, Int>()) } // Map of courseId to quantity
    val user by userAPIViewModel.currentUser.observeAsState()
    val cart by courseViewModel.cart.collectAsState()

    // Fetch courses and cart number on initialization
    LaunchedEffect(Unit) {
        Log.d("CourseViewModel", "Fetching courses")
        courseViewModel.getAllCourses()
        userAPIViewModel.getToken()?.let { userAPIViewModel.getCurrentUser(it) }
        Log.d("user", user.toString())
        cartNumber = if (userAPIViewModel.getToken() != null) {
            courseViewModel.getCardNumber(userAPIViewModel.getToken()!!)
        } else {
            0
        }
    }

    // Update the cart whenever selectedCourses changes
    LaunchedEffect(selectedCourses) {
        Log.d("CourseViewModel", "Updating cart")
        val token = userAPIViewModel.getToken()
        if (token != null) {
            val coursesList = selectedCourses.map { Pair(it.key, it.value) }
            try {
                courseViewModel.addToCart(coursesList, token)

                cartNumber = courseViewModel.getCardNumber(token)
            } catch (e: Exception) {
                Log.e("CourseViewModel", "Failed to update cart: ${e.localizedMessage}")
            }
        }
    }

    Scaffold(
        bottomBar = {
            Footer(navController, userViewModel)
        },
        floatingActionButton = {
            Button(
                onClick = {
                    val token = userAPIViewModel.getToken()!!
                    val coursesList = selectedCourses.map { Pair(it.key, it.value) }
                    courseViewModel.addToCart(coursesList, token)
                },
                modifier = Modifier
                    .fillMaxWidth() // Spans the entire width
                    .clip(RoundedCornerShape(16.dp)) // Rounded corners with 16.dp radius
                    .padding(16.dp) // Add padding around the button
                    .height(64.dp)
            ) {
                Text("Add to Cart", fontSize = 32.sp) // Button text
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            // Header with Delivery info
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column {
                        Row {
                            Text("Delivery to:", modifier = Modifier.padding(end = 4.dp))
                            Text(user?.fullName ?: "Guest", fontWeight = FontWeight.SemiBold)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(painterResource(R.drawable.locationpin), contentDescription = "Location", modifier = Modifier.padding(end = 4.dp))
                            Text(user?.address ?: "Please Add Your Address")
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    BadgedBox(
                        badge = {
                            if (cartNumber != 0) {
                                Badge {
                                    Text(cartNumber.toString())
                                }
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

            // Search box
            item {
                SearchBox({ searchQuery = it })
            }

            // Category selection or search results
            item {
                if (searchQuery.isNotEmpty()) {
                    Text("Result for: $searchQuery", fontSize = 24.sp, modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .height(48.dp))
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

            // Filter and display courses
            val filteredCourses = allCourse.filter {
                it.typeCourse == selectedCategory
            }
            val searchedCourses = allCourse.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }

            if (searchedCourses.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            "Oops! We don't have that dish :(",
                            fontSize = 64.sp,
                            lineHeight = 64.sp,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            } else {
                items(
                    items = allCourse.filter { it.typeCourse == selectedCategory && it.title.contains(searchQuery, ignoreCase = true) },
                    key = { it.id!! } // Use the unique ID of each course as the key
                ) { dish ->
                    CourseDetailCard(
                        course = dish,
                        currentQuantity = selectedCourses[dish.id] ?: 0,
                        onIncrement = { courseId ->
                            selectedCourses[courseId] = (selectedCourses[courseId] ?: 0) + 1
                        },
                        onDecrement = { courseId ->
                            val currentQuantity = selectedCourses[courseId] ?: 0
                            if (currentQuantity > 0) {
                                selectedCourses[courseId] = currentQuantity - 1
                                if (selectedCourses[courseId] == 0) {
                                    selectedCourses.remove(courseId) // Remove if quantity reaches 0
                                }
                            }
                        },
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun CourseDetailCard(
    course: Course,
    currentQuantity: Int,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
    navController: NavController
) {
    var count by remember { mutableIntStateOf(currentQuantity) }
    Log.d("CourseViewModel", "CourseID o home: ${course.id}")

    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
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
                    .clip(RoundedCornerShape(8.dp))
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
                Text(text = "$${course.price}", fontSize = 14.sp, color = Color.Gray)
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
                        .clickable { if (count > 0) count--; onDecrement(course.id!!) }
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
                        .clickable { count++; onIncrement(course.id!!) }
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