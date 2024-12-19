package com.example.foodiee.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R
import com.example.foodiee.data.models.CourseDetails
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.BackButton
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun DetailedCourseScreen(navController: NavController, userViewModel: UserViewModel, course: CourseDetails) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .verticalScroll(rememberScrollState())
                .padding(top = 48.dp, start = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BackButton(navController)

            Box(
                modifier = Modifier
                    .size(350.dp)
                    .border(
                        width = 1.dp,
                        color = FoodieeeColors.slate200,
                        shape = RoundedCornerShape(16.dp) // Rounded corners
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.detailed_course),
                    contentDescription = "Detailed course",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = course.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$360",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.star_half_full),
                        contentDescription = "Star",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = 4.5.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = FoodieeeColors.slate500
                    )
                }
            }

            Text(
                text = "Description",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
            repeat(5) {
                Text(
                    text = course.description,
                    color = FoodieeeColors.slate500
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
                    Text(
                        text = course.ingredients.joinToString(", "),
                        color = FoodieeeColors.slate500
                    )
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
                    Text(
                        text = course.mealType,
                        color = FoodieeeColors.slate500
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}