package com.example.foodiee.ui.screens.client

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.foodiee.R
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.components.NavigationHeader
import com.example.foodiee.ui.components.configSubPage
import com.example.foodiee.ui.theme.Orange500

@Composable
fun InventoryScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            Column {
                NavigationHeader(configSubPage.INVENTORY, navController)
                TabBar(navController)
            }
        },
        bottomBar = { Footer(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(MockInventoryItems()) { item ->
                InventoryItemCard(
                    name = item.name,
                    type = item.type,
                    price = item.price,
                    quantity = item.quantity,
                    image = item.image
                )
            }
        }
    }
}

@Composable
fun InventoryItemCard(
    name: String,
    type: String,
    price: String,
    quantity: Int,
    image: Int
) {
    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min) // Ensure the height of the Row matches its tallest child
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "item image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight() // Match the Row's height
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f) // Allow the Column to take up remaining space
            ) {
                Text(text = name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = type,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(horizontal = 8.dp)
                )
                Text(text = price, fontSize = 14.sp, color = Color.Gray)
                Text(text = "Quantity: $quantity", fontSize = 10.sp, color = Color.Gray)
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
                        .clickable { }
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

@Composable
fun TabBar(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Surface(
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(8.dp),
            color = Orange500,
            modifier = Modifier
                .padding(8.dp)
                .clickable { }
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_icon),
                    contentDescription = "add icon",
                    tint = Color.White
                )
                Text("Add new Item", fontSize = 18.sp, color = Color.White)
            }
        }
        Surface(
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(8.dp),
            color = Orange500,
            modifier = Modifier
                .padding(8.dp)
                .clickable { }
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.pen),
                    contentDescription = "pen icon",
                    tint = Color.White
                )
                Text("Add new Item", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

fun MockInventoryItems(): List<InventoryItem> {
    return listOf(
        InventoryItem(
            name = "Apple",
            type = "Fruit",
            price = "$1.00",
            quantity = 50,
            image = R.drawable.detailed_course
        ),
        InventoryItem(
            name = "Bread",
            type = "Bakery",
            price = "$2.50",
            quantity = 30,
            image = R.drawable.detailed_course
        ),
        InventoryItem(
            name = "Carrot",
            type = "Vegetable",
            price = "$0.75",
            quantity = 100,
            image = R.drawable.detailed_course
        ),
        InventoryItem(
            name = "Milk",
            type = "Dairy",
            price = "$3.00",
            quantity = 20,
            image = R.drawable.detailed_course
        ),
        InventoryItem(
            name = "Chicken",
            type = "Meat",
            price = "$5.00",
            quantity = 15,
            image = R.drawable.detailed_course
        )
    )
}

data class InventoryItem(
    val name: String,
    val type: String,
    val price: String,
    val quantity: Int,
    val image: Int
)

@Preview(showBackground = true)
@Composable
fun PreviewInventoryScreen() {
    InventoryItemCard(
        name = "Apple",
        type = "Fruit",
        price = "$1.00",
        quantity = 50,
        image = R.drawable.detailed_course
    )
}