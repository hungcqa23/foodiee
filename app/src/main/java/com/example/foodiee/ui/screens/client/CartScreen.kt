package com.example.foodiee.ui.screens.client

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R
import com.example.foodiee.ui.components.BackButton
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.screens.admin.InventoryItem
import com.example.foodiee.ui.screens.admin.MockInventoryItems
import com.example.foodiee.ui.theme.FoodieeeColors

@Composable
fun CartScreen(navController: NavController, cartId: String) {

    val radioOptions = listOf("Eat at Restaurant", "Delivery")
    val (selectedOrderOption, onOrderOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val options = listOf("Cash", "Credit Card", "E-Wallet")
    var selectedPaymentOption by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { Footer(navController = navController) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding),
        ) {
            item {
                BackButton(navController = navController)
            }
            item {
                Text(
                    "Your Cart",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(MockInventoryItems()) { item ->
                CartItemCard(
                    item = item,
                    onQuantityClick = { },
                    onRemoveClick = { }
                )
            }
            item {
                var expanded by remember { mutableStateOf(false) }
                Column {
                    Text(
                        "Payment Type",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(48.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(4.dp)
                            ),
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
                                text = selectedPaymentOption.ifEmpty { "Select an option" },
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
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(4.dp))

                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = Color.Black) },
                                onClick = {
                                    selectedPaymentOption = option
                                    expanded = false
                                },
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    "Order Type",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (text == selectedOrderOption),
                                    onClick = { onOrderOptionSelected(text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOrderOption),
                                onClick = null
                            )
                            Text(
                                text = text,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
            if(selectedOrderOption == "Delivery"){
                item {
                    Text(
                        "Delivery Address",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            verticalAlignment = Alignment.Top
                        ) {
                            BasicTextField(
                                value = address,
                                onValueChange = { address = it },
                                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                                cursorBrush = SolidColor(Color.Black),
                                singleLine = false,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                                    .background(Color.Transparent)
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        "Total: ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "$12.99",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            item {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF18181B),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Proceed to Payment",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: InventoryItem,
    onQuantityClick: (Int) -> Unit,
    onRemoveClick: () -> Unit,
) {
    Surface(
        shadowElevation = 4.dp,
        color = Color(0xFFF4F4F5),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
        ) {
            Column {
                Text(item.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(item.price, fontSize = 14.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clickable { onQuantityClick(item.quantity - 1) }
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
                Text(item.quantity.toString(), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Box(
                    modifier = Modifier
                        .clickable { onQuantityClick(item.quantity + 1) }
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
                Box(
                    modifier = Modifier
                        .clickable { onRemoveClick() }
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .border(1.dp, FoodieeeColors.slate300, RoundedCornerShape(4.dp))
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = "delete icon",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(navController = NavController(LocalContext.current), "325")
}