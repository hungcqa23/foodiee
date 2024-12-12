package com.example.foodiee.ui.screens.client

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.foodiee.R
import com.example.foodiee.ui.components.*
import com.example.foodiee.ui.theme.Orange400
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddItemScreen(
    navController: NavController
) {

    var newItemImage: Uri? by remember { mutableStateOf(null) }
    var newItemName by remember { mutableStateOf("") }
    var newItemPrice by remember { mutableStateOf("") }
    var newItemQuantity by remember { mutableStateOf("") }
    var newItemType by remember { mutableStateOf("") }
    var showAddImageDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Footer(navController)
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
                    .size(275.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .clickable { showAddImageDialog = true }
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                if(newItemImage != null){
                    Image(
                        painter = rememberAsyncImagePainter(newItemImage),
                        contentDescription = "New Item Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else{
                    Row {
                        Icon(painterResource(R.drawable.add_icon), contentDescription = "Camera")
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
                onValueChange = { newItemName = it }
            )
            MyDropdownMenu(
                "Type",
                options = listOf("Food", "Drink", "Dessert"),
                onOptionSelected = { newItemType = it }
            )
            InputField(
                "Price",
                onValueChange = { newItemPrice = it }
            )
            InputField(
                "Quantity",
                onValueChange = { newItemQuantity = it }
            )
            Spacer(modifier = Modifier.weight(1f))
            ActionButton(text = "Add new item", onClick = {})
            if(showAddImageDialog){
                AddImageDialog(
                    onNewImageAdded = { newItemImage = it; showAddImageDialog = false },
                    onDismiss = { showAddImageDialog = false }
                )
            }
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


@Preview(showBackground = true)
@Composable
fun AddItemScreenPreview() {
    AddItemScreen(navController = NavController(LocalContext.current))
}