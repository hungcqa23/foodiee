package com.example.foodiee.ui.screens.admin

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiee.R
import com.example.foodiee.data.models.MockInventoryItems
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.ConfigNavigationHeader
import com.example.foodiee.ui.components.Footer
import com.example.foodiee.ui.components.ConfigSubPage
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun StatisticsScreen(navController: NavController, userViewModel: UserViewModel) {
    val mockData: List<Double> = listOf(245.0, 443.0, 523.0, 314.0, 566.0, 693.0, 482.0)
    var mockDataCustomer by remember {
        mutableStateOf(
            listOf(
                Pie(
                    label = "Returning Customer",
                    data = 72.6,
                    color = Color(0xFFEF4444),
                    selectedColor = Color(0xFFFF4444)
                ),
                Pie(
                    label = "New Customer",
                    data = 27.4,
                    color = Color(0xFFF97315),
                    selectedColor = Color(0xFFFF7315)
                ),
            )
        )
    }

    Scaffold(
        topBar = {
            Column {
                ConfigNavigationHeader(ConfigSubPage.STATISTICS, navController)
            }
        },
        bottomBar = {
            Footer(navController, userViewModel)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header Row with Statistics Tabs
            item {
                Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        StatTab(
                            title = "Today's Orders",
                            value = "123",
                            image = R.drawable.linegraph,
                            modifier = Modifier.weight(1f)
                        )
                        StatTab(
                            title = "Today's Revenue",
                            value = "$${mockData.last()}",
                            image = R.drawable.piggybank,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        StatTab(
                            title = "Low on stock",
                            value = "3",
                            image = R.drawable.cartonbox,
                            modifier = Modifier.weight(1f)
                        )
                        StatTab(
                            title = "Monthly Revenue",
                            value = "$51 223",
                            image = R.drawable.calendar,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Line Chart
            item {
                Surface(
                    shadowElevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        val increased = mockData.last() > mockData.first()
                        Text(
                            "Today's Revenue",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            "$732",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(if (increased) R.drawable.arrowup else R.drawable.arrowdown),
                                contentDescription = "warning icon",
                                tint = if (increased) Color(0xFF36CD1D) else Color.Red,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                "${(mockData.last() - mockData.first()) / 100}% vs last week",
                                fontSize = 14.sp,
                                color = if (increased) Color(0xFF258714) else Color.Red
                            )
                        }
                        LineChart(
                            modifier = Modifier.height(268.dp),
                            data = remember {
                                listOf(
                                    Line(
                                        label = "Revenue",
                                        values = mockData,
                                        color = SolidColor(if (increased) Color(0xFF36CD1D) else Color.Red),
                                        firstGradientFillColor = (if (increased) Color(0xFFBEEFB6).copy(
                                            alpha = .5f
                                        ) else Color.Red.copy(alpha = .5f)),
                                        secondGradientFillColor = Color.Transparent,
                                        strokeAnimationSpec = tween(3000, easing = EaseInOutCubic),
                                        gradientAnimationDelay = 2000,
                                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                                    )
                                )
                            },
                        )
                    }
                }
            }

            //Pie chart
            item {
                Surface(
                    shadowElevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Weekly Customer",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            "732 Customer",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val increased = mockData.last() > mockData.first()
                            Icon(
                                painter = painterResource(if (increased) R.drawable.arrowup else R.drawable.arrowdown),
                                contentDescription = "warning icon",
                                tint = if (increased) Color(0xFF258714) else Color.Red,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                "${(mockData.last() - mockData.first()) / 100}% vs last week",
                                fontSize = 14.sp,
                                color = if (increased) Color(0xFF258714) else Color.Red
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(Color(0xFFEF4444))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Returning Customer:\n${mockDataCustomer.first().data}%",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(Color(0xFFF97315))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "New Customer:\n${mockDataCustomer.last().data}%",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            PieChart(
                                modifier = Modifier
                                    .size(180.dp)
                                    .padding(16.dp)
                                    .weight(1f),
                                data = mockDataCustomer,
                                onPieClick = {
                                    println("${it.label} Clicked")
                                    val pieIndex = mockDataCustomer.indexOf(it)
                                    mockDataCustomer =
                                        mockDataCustomer.mapIndexed { mapIndex, pie ->
                                            pie.copy(selected = pieIndex == mapIndex)
                                        }
                                },
                                selectedScale = 1.1f,
                                scaleAnimEnterSpec = spring<Float>(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                colorAnimEnterSpec = tween(300),
                                colorAnimExitSpec = tween(300),
                                scaleAnimExitSpec = tween(300),
                                spaceDegreeAnimExitSpec = tween(300),
                                spaceDegree = 0f,
                                selectedPaddingDegree = 4f,
                                style = Pie.Style.Stroke(42.dp)
                            )
                        }
                    }
                }
            }

            // Overview Section
            item {
                Surface(
                    shadowElevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Overview",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(8.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.warning),
                                contentDescription = "warning icon",
                                tint = Color(0xFFE9B30B),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text("Low on stock", fontSize = 14.sp, color = Color.Black)
                            Spacer(Modifier.weight(1f))
                            Text(
                                "3",
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.LightGray)
                                    .padding(horizontal = 8.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.cartonbox),
                                contentDescription = "warning icon",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text("Need Restock", fontSize = 14.sp, color = Color.Black)
                            Spacer(Modifier.weight(1f))
                            Text(
                                "3",
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.LightGray)
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }

            // Low Stock Items
            item {
                Surface(
                    shadowElevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Low stock items",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(8.dp)
                        )
                        LazyColumn(
                            userScrollEnabled = true,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.height(220.dp)
                        ) {
                            items(MockInventoryItems()) { item ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column {
                                        Text(
                                            item.name,
                                            fontSize = 14.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            "Only ${item.quantity} left",
                                            fontSize = 10.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Spacer(Modifier.weight(1f))
                                    Text(
                                        "Low Stock",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color.LightGray)
                                            .padding(horizontal = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun StatTab(
    title: String,
    value: String,
    image: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "item image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = value, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

