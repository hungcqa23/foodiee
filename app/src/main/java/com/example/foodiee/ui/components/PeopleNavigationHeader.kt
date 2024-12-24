package com.example.foodiee.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodiee.data.models.Role


@Composable
fun PeopleNavigationHeader(currentPeopleSubPage: Role, onClick: (Role) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Text("Customer",
            fontSize = 18.sp,
            color = Color.Black,
            textDecoration = if (currentPeopleSubPage == Role.USER) TextDecoration.Underline else TextDecoration.None,
            fontWeight = if (currentPeopleSubPage == Role.USER) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.clickable { onClick(Role.USER) }
        )
        Text("Employee",
            fontSize = 18.sp,
            color = Color.Black,
            textDecoration = if (currentPeopleSubPage == Role.STAFF) TextDecoration.Underline else TextDecoration.None,
            fontWeight = if (currentPeopleSubPage == Role.STAFF) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.clickable { onClick(Role.STAFF) }
        )

    }
}