package com.example.foodiee.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Footer() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn() {
//            stickyHeader {
//                Text("BEHOLD THE STICKY HEADER!")
//            }
//            items(35) { index ->
//                Text(text = "Item: $index", fontSize = 30.sp)
//            }
//        }
//        Column(
//            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(Color.Red),
//        ) {
//            Text("It is I, The Sticky Footer", fontSize = 30.sp)
//        }
//    }
    Text(text = "It is I, The Sticky Footer")
}