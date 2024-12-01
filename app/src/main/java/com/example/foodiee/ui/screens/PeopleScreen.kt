package com.example.foodiee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.data.models.*
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.components.people_screens.PersonCard

@Composable
fun CustomerListScreen(navController: NavController, viewSelected: Customer) {
    var searchQuery by remember { mutableStateOf("") }
    val tabs = listOf("Customers", "Employee")
    val navigationBarInsets = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val statusBarInsets = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Scaffold(
        topBar = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = statusBarInsets)
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "Customer",
                        fontSize = 16.sp,
                        textDecoration = if (viewSelected.type == CustomerType.CUSTOMER) TextDecoration.Underline else TextDecoration.None,
                        fontWeight = if (viewSelected.type == CustomerType.CUSTOMER) FontWeight.Bold else FontWeight.Normal
                    )
                    Text(
                        text = "Employee",
                        fontSize = 16.sp,
                        textDecoration = if (viewSelected.type == CustomerType.EMPLOYEE) TextDecoration.Underline else TextDecoration.None,
                        fontWeight = if (viewSelected.type == CustomerType.EMPLOYEE) FontWeight.Bold else FontWeight.Normal
                    )
                }
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

                    FloatingActionButton(
                        onClick = { /* Handle add */ },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(56.dp),
                        containerColor = Color(0xFFE37A54),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Footer(navController)
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
                    "Kết quả (5)",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(getSampleCustomers()) { customer ->
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
            type = CustomerType.CUSTOMER
        )
    )
}

@Preview
@Composable
fun CustomerListScreenPreview() {
    CustomerListScreen(navController = NavController(LocalContext.current), getSampleCustomers()[0])
}