package com.example.foodiee.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodiee.R
import com.example.foodiee.ui.theme.Orange500
import com.example.foodiee.ui.theme.Slate100

@Composable
fun ActionButton(
    text: String,
    subText: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
){
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if(enabled) Orange500 else Slate100,
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(16.dp)
            .clickable { if(enabled) onClick() }
            .height(50.dp)
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = text, fontSize = 16.sp, color = Color.White, textAlign = TextAlign.Center )

            Spacer(modifier = Modifier.weight(1f))

            if(subText != null){
                Text(text = subText, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White,textAlign = TextAlign.Center)
            }
            Icon(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}