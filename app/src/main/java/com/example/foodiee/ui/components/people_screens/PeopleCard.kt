package com.example.foodiee.ui.components.people_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodiee.data.models.Customer
import com.example.foodiee.ui.screens.CustomerInfoRow

@Composable
fun PersonCard(person: Customer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Gray,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Gray
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF8F8F8))
                    .fillMaxWidth()
                    .size(45.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color(0xFFF37021),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = person.id,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }

            CustomerInfoRow(
                icon = Icons.Default.Person,
                text = person.name
            )

            CustomerInfoRow(
                icon = Icons.Default.DateRange,
                text = person.cccd,
                textColor = Color(0xFFF37021),
                weight = FontWeight.Medium
            )

            CustomerInfoRow(
                icon = Icons.Default.Phone,
                text = person.phone
            )

            CustomerInfoRow(
                icon = Icons.Default.LocationOn,
                text = person.address
            )
        }
    }
}
