package com.example.foodiee.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.data.models.Customer
import com.example.foodiee.data.models.Role
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.components.PeopleNavigationHeader
import com.example.foodiee.ui.components.people_screens.PersonCard

@Composable
fun PeopleManagementScreen(navController: NavController, userViewModel: UserViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var tabs: Role by remember { mutableStateOf(Role.CUSTOMER) }
    val navigationBarInsets = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val statusBarInsets = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val queryResult = getSampleCustomers()
        .filter {
            it.type == tabs &&
                    it.name.contains(searchQuery, ignoreCase = true)
        }


    Scaffold(
        topBar = {
            Column {
                PeopleNavigationHeader(
                    currentPeopleSubPage = tabs,
                    onClick = {
                        tabs = it
                    })
                // Search Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Search") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                    )
                }
            }
        },
        bottomBar = {
            Footer(navController, userViewModel)
            Spacer(modifier = Modifier.height(navigationBarInsets))
        },
        containerColor = Color(0xFFFDFCFB)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    "Kết quả (${queryResult.size})",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(queryResult) { customer ->
                PersonCard(person = customer)
            }
        }
    }
}

@Composable
fun CustomerInfoRow(
    icon: ImageVector,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    weight: FontWeight = FontWeight.Normal
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            color = textColor,
            fontWeight = weight
        )
    }
}


private fun getSampleCustomers(): List<Customer> {
    return listOf(
        Customer(
            id = "HNH406551",
            name = "Lương Thuỳ Linh",
            cccd = "034886599",
            phone = "098 812 3456",
            address = "Số 10, Phạm Văn Bạch, P. Yên Hoà, Q. Cầu Giấy, Hà Nội",
            type = Role.CUSTOMER
        ),
        Customer(
            id = "HNH406551",
            name = "Lương Thuỳ Loan",
            cccd = "034886599",
            phone = "098 812 3456",
            address = "Số 10, Phạm Văn Bạch, P. Yên Hoà, Q. Cầu Giấy, Hà Nội",
            type = Role.CUSTOMER
        ),
        Customer(
            id = "HNH406551",
            name = "Lương Thuỳ Liên",
            cccd = "034886599",
            phone = "098 812 3456",
            address = "Số 10, Phạm Văn Bạch, P. Yên Hoà, Q. Cầu Giấy, Hà Nội",
            type = Role.EMPLOYEE
        ),
        Customer(
            id = "HNH406551",
            name = "Lương Thuỳ Link",
            cccd = "034886599",
            phone = "098 812 3456",
            address = "Số 10, Phạm Văn Bạch, P. Yên Hoà, Q. Cầu Giấy, Hà Nội",
            type = Role.EMPLOYEE
        ),
        Customer(
            id = "HNH406551",
            name = "Lương Thuỳ Lung",
            cccd = "034886599",
            phone = "098 812 3456",
            address = "Số 10, Phạm Văn Bạch, P. Yên Hoà, Q. Cầu Giấy, Hà Nội",
            type = Role.EMPLOYEE
        )
    )
}

@Preview
@Composable
fun CustomerListScreenPreview() {
//    PeopleManagementScreen(
//        navController = NavController(LocalContext.current),
//    )
}