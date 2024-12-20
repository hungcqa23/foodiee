package com.example.foodiee.ui.screens.client

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodiee.R
import com.example.foodiee.data.models.Course.Course
import com.example.foodiee.data.models.Course.CourseViewModel
import com.example.foodiee.data.models.User.UserViewModel
import com.example.foodiee.ui.components.*
import com.example.foodiee.ui.theme.Orange400
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddItemScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    courseViewModel: CourseViewModel,
    courseID: String? = null,
) {
    val course by courseViewModel.courseDetail.collectAsState()
    var newItemImage: Uri? by remember { mutableStateOf(null) }
    var newItemName by remember { mutableStateOf("") }
    var newItemPrice by remember { mutableStateOf("") }
    var newItemQuantity by remember { mutableStateOf("") }
    var newItemType by remember { mutableStateOf("") }
    var selectedIngredients by remember { mutableStateOf(emptySet<String>()) }
    var showAddImageDialog by remember { mutableStateOf(false) }

    Log.d("AddItemScreen", "course outside: $course")

    LaunchedEffect(courseID) {
        if (courseID != null) {
            courseViewModel.getCourseById(courseID.toInt())
        } else {
            Log.d("AddItemScreen", "Course ID not provided")
        }
    }

    // Update variables whenever the course changes
    LaunchedEffect(course) {
        course?.let {
            newItemImage = it.image?.toUri()
            newItemName = it.title ?: ""
            newItemPrice = it.price.toString()
            newItemQuantity = it.quantity.toString()
            newItemType = it.typeCourse ?: ""
            selectedIngredients = it.ingredients?.toSet() ?: emptySet()

            Log.d("AddItemScreen", "Updated new item variables")
            Log.d("AddItemScreen", "new item image: $newItemImage")
            Log.d("AddItemScreen", "new item name: $newItemName")
            Log.d("AddItemScreen", "new item price: $newItemPrice")
            Log.d("AddItemScreen", "new item quantity: $newItemQuantity")
            Log.d("AddItemScreen", "new item type: $newItemType")
            Log.d("AddItemScreen", "new item ingredients: $selectedIngredients")
        }
    }
    Scaffold(
        bottomBar = {
            Footer(navController, userViewModel)
        },
        topBar = {
            BackButton(navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Add New Item",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Box(
                modifier = Modifier
                    .size(215.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .clickable { showAddImageDialog = true }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (newItemImage != null) {
                    Image(
                        painter = rememberAsyncImagePainter(newItemImage),
                        contentDescription = "New Item Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.add_icon), contentDescription = "Camera", modifier = Modifier.size(22.dp))
                        Text(
                            "Add image",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                        )
                    }
                }
            }
            InputField(
                "Name",
                defaultString = newItemName,
                onValueChange = { newItemName = it }
            )
            MyDropdownMenu(
                "Type",
                options = listOf("main_course", "appetizer", "drink", "dessert"),
                defaultOption = newItemType,
                onOptionSelected = { newItemType = it }
            )
            MultiSelectDropdown(
                title = "Ingredients",
                options = listOf("Tomato", "Cheese", "Lettuce", "Onion", "Chicken"),
                selectedItems = selectedIngredients,
                onSelectionChange = { selectedIngredients = it }
            )
            InputField(
                "Price",
                defaultString = newItemPrice,
                onValueChange = { newItemPrice = it }
            )
            InputField(
                "Quantity",
                defaultString = newItemQuantity,
                onValueChange = { newItemQuantity = it }
            )
            Spacer(modifier = Modifier.weight(1f))
            if (showAddImageDialog) {
                AddImageDialog(
                    onNewImageAdded = { newItemImage = it; showAddImageDialog = false },
                    onDismiss = { showAddImageDialog = false }
                )
            }
            ActionButton(text = "Add new item", onClick = {
                val newCourse = Course(
                    title = newItemName,
                    description = "",
                    typeCourse = newItemType,
                    quantity = newItemQuantity.toInt(),
                    price = newItemPrice.toDouble(),
                    ingredients = selectedIngredients.toList(),
                    image = newItemImage.toString()
                )
                if(courseID != null) {
                    courseViewModel.updateCourse(courseID.toInt(), newCourse) {
                        navController.popBackStack()
                    }
                }else {
                    courseViewModel.createCourse(newCourse) {
                        navController.popBackStack()
                    }
                }
            })
        }

    }
}

@Composable
fun MultiSelectDropdown(
    title: String,
    options: List<String>,
    selectedItems: Set<String>,
    onSelectionChange: (Set<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val maxVisibleItems = 5 // Max number of visible selected items
    val itemHeight = 32.dp

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedItems.isEmpty()) "Select ingredients" else selectedItems.joinToString(", "),
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
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
                .heightIn(
                    max = maxVisibleItems * itemHeight + 16.dp, // Max height for dropdown
                )
                .background(Color.White)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(4.dp))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = Color.Black) },
                    onClick = {
                        val newSelection = if (option in selectedItems) {
                            selectedItems - option
                        } else {
                            selectedItems + option
                        }
                        onSelectionChange(newSelection)
                    }
                )
            }
        }
    }
}

@Composable
fun MyDropdownMenu(
    title: String,
    options: List<String>,
    defaultOption: String = "",
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

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
                    text = if (defaultOption.isEmpty()) "Select an option" else defaultOption,
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
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun InputField(
    title: String,
    defaultString: String,
    onValueChange: (String) -> Unit,
) {
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
                    value = defaultString, // Directly use defaultString as the text
                    onValueChange = { onValueChange(it) }, // Notify parent of changes
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

@Composable
fun AddImageDialog(
    onNewImageAdded: (Uri?) -> Unit,
    onDismiss: () -> Unit // Dismiss the dialog
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Gallery Image Picker Launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onNewImageAdded(uri)
        onDismiss()
    }

    // Camera Image Capture Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            onNewImageAdded(imageUri)
            onDismiss()
        }
    }

    // Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Open camera if permission is granted
            imageUri = createImageUri(context)
            cameraLauncher.launch(imageUri!!)
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add Image",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange400)
                ) {
                    Text(text = "Pick from Gallery", color = Color.White)
                }

                Button(
                    onClick = {
                        // Check and request camera permission
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                // Permission already granted, launch camera
                                imageUri = createImageUri(context)
                                cameraLauncher.launch(imageUri!!)
                            }
                            else -> {
                                // Request camera permission
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange400)
                ) {
                    Text(text = "Capture using Camera", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// Utility function to create a unique image file URI for camera capture
fun createImageUri(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,  /* prefix */
        ".jpg",         /* suffix */
        context.externalCacheDir      /* directory */
    )

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        image
    )
}


