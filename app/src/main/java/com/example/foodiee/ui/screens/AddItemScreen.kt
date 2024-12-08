package com.example.foodiee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R
import com.example.foodiee.ui.components.ActionButton
import com.example.foodiee.ui.components.BackButton
import com.example.foodiee.ui.components.Footer

@Composable
fun AddItemScreen(
    navController: NavController
){
    Scaffold (
        bottomBar = {
                Footer(navController)
        },
        topBar = {
            BackButton(navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        Column(modifier = Modifier.padding(padding)
            .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Add New Item", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding( top = 16.dp))

            Box(
                modifier = Modifier
                    .size(275.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(32.dp)
                    ,
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Icon(painterResource(R.drawable.add_icon), contentDescription = "Camera")
                    Text("Add image", fontSize = 24.sp, fontWeight = FontWeight.Normal, modifier = Modifier)
                }
            }
            InputField(
                "Name",
                onValueChange = {}
            )
            MyDropdownMenu(
                "Type",
                options = listOf("Food", "Drink", "Dessert"),
                onOptionSelected = {}
            )
            InputField(
                    "Price",
            onValueChange = {}
            )
            InputField(
                "Quantity",
                onValueChange = {}
            )
            Spacer(modifier = Modifier.weight(1f))
            ActionButton(text = "Add new item", onClick = {})
        }

    }
}

@Composable
fun MyDropdownMenu(
    title: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedOption.ifEmpty { "Select an option" },
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Icon",
                    tint = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(4.dp))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = Color.Black) },
                    onClick = {
                        onOptionSelected(option)
                        selectedOption = option
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
fun InputField(
    title: String,
    onValueChange: (String) -> Unit,
) {
    var value by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = { onValueChange(value); value = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    cursorBrush = SolidColor(Color.Black),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp, vertical = 0.dp)
                        .background(Color.Transparent)
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun AddItemScreenPreview(){
    AddItemScreen(navController = NavController(LocalContext.current))
}