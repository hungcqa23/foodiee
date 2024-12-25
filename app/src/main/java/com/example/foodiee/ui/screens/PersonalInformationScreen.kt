package com.example.foodiee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R
import com.example.foodiee.data.models.User.UserAPI.User
import com.example.foodiee.data.models.User.UserAPI.UserAPIViewModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.BackButton
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun PersonalInformationScreen(navController: NavController, userViewModel: UserViewModel, userAPIViewModel: UserAPIViewModel) {
    var newEmail by remember { mutableStateOf( userAPIViewModel.currentUser.value?.email ?: "") }
    var newPhoneNumber by remember { mutableStateOf(userAPIViewModel.currentUser.value?.phoneNumber ?: "") }
    var newAddress by remember { mutableStateOf(userAPIViewModel.currentUser.value?.address ?: "") }
    var newImage by remember { mutableStateOf(userAPIViewModel.currentUser.value?.profileImage ?: "") }
    Scaffold(
        topBar = { BackButton(navController)},
        bottomBar = {
            Footer(navController, userAPIViewModel)
        }
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfileImage(
                    imageUrl = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {  }
                )
                Text(
                    text = userAPIViewModel.currentUser.value?.fullName ?: "Guest",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = userAPIViewModel.currentUser.value?.role.toString() ?: "User",
                    color = FoodieeeColors.slate500,
                    fontSize = 16.sp
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProfileItem(
                        title = "Email",
                        icon = R.drawable.mail,
                        displayText = newEmail,
                        onChange = { newEmail = it }
                    )
                    ProfileItem(
                        title = "Phone",
                        icon = R.drawable.phone,
                        displayText = "+$newPhoneNumber",
                        onChange = { newPhoneNumber = it }
                    )
                    ProfileItem(
                        title = "Address",
                        icon = R.drawable.map_pin,
                        displayText = newAddress,
                        onChange = { newAddress = it }
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val newUser = User(
                            id = userAPIViewModel.currentUser.value!!.id,
                            fullName = userAPIViewModel.currentUser.value!!.fullName,
                            phoneNumber = newPhoneNumber,
                            address = newAddress,
                            email = newEmail,
                            password = userAPIViewModel.currentUser.value!!.password,
                            role = userAPIViewModel.currentUser.value!!.role,
                            profileImage = userAPIViewModel.currentUser.value?.profileImage
                        )
                        userAPIViewModel.updateUser(newUser)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FoodieeeColors.green500),
                ){
                    Text(
                        text = "Save",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileItem(title: String, icon: Int, displayText: String, onChange: (String) -> Unit) {
    var input by remember { mutableStateOf(displayText) }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = (-0.2).sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)) // Add border radius
                .background(FoodieeeColors.slate200)
                .padding(vertical = 12.dp, horizontal = 14.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "$title icon",
                modifier = Modifier.size(20.dp)
            )
            BasicTextField(
                value = input,
                onValueChange = {input = it},
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 18.sp
                )
            )
        }
    }
}