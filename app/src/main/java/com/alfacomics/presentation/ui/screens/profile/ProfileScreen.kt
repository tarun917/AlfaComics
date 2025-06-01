package com.alfacomics.presentation.ui.screens.profile

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.alfacomics.R
import com.alfacomics.data.repository.DummyData

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    // State to hold the logged-in status
    var isLoggedIn by remember { mutableStateOf(DummyData.isLoggedIn) }

    // Redirect to login if not logged in
    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate("login") {
                popUpTo("profile") { inclusive = true }
            }
        }
    }

    // State to hold the user profile and refresh it when needed
    var userProfile by remember { mutableStateOf(DummyData.getUserProfile()) }
    val favoriteComicsCount by derivedStateOf { DummyData.getCommunityPostsCount() }
    val followersCount by derivedStateOf { userProfile.followers.size }
    val context = LocalContext.current

    // State for profile picture selection dialog
    var showPictureDialog by remember { mutableStateOf(false) }
    var selectedPictureResourceId by remember { mutableStateOf(userProfile.profilePictureResourceId) }

    // Launcher for picking an image from gallery
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                bitmap?.let { bmp ->
                    DummyData.updateProfilePicture(bmp.asImageBitmap())
                    // Refresh userProfile to reflect the updated image
                    userProfile = DummyData.getUserProfile()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
        showPictureDialog = false
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp)
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Settings Icon at Top-Left
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("settings")
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color(0xFFBB86FC)
                    )
                }
            }
        }

        // Profile Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C2526))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Side: Profile Picture, Name, Email
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Profile Picture with Edit Option
                        Box(
                            modifier = Modifier.size(70.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val defaultBitmap = BitmapFactory.decodeResource(context.resources, selectedPictureResourceId)
                            if (userProfile.profilePictureBitmap != null) {
                                Image(
                                    bitmap = userProfile.profilePictureBitmap!!,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(CircleShape)
                                        .border(
                                            width = 2.dp,
                                            brush = Brush.linearGradient(
                                                colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                            ),
                                            shape = CircleShape
                                        )
                                        .background(Color.Gray)
                                )
                            } else if (defaultBitmap != null) {
                                Image(
                                    bitmap = defaultBitmap.asImageBitmap(),
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(CircleShape)
                                        .border(
                                            width = 2.dp,
                                            brush = Brush.linearGradient(
                                                colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                            ),
                                            shape = CircleShape
                                        )
                                        .background(Color.Gray)
                                )
                            } else {
                                // Fallback placeholder if both bitmaps are null
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(CircleShape)
                                        .border(
                                            width = 2.dp,
                                            brush = Brush.linearGradient(
                                                colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                            ),
                                            shape = CircleShape
                                        )
                                        .background(Color.Gray)
                                )
                            }
                            IconButton(
                                onClick = { showPictureDialog = true },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(16.dp)
                                    .background(Color(0xFFBB86FC), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Profile Picture",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Username, User ID, Email, and Edit Button
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = userProfile.username,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "(ID: ${userProfile.userId})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                IconButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(ClipboardManager::class.java)
                                        val clip = ClipData.newPlainText("User ID", userProfile.userId.toString())
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "User ID copied to clipboard", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(16.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy User ID",
                                        tint = Color(0xFFBB86FC)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                IconButton(
                                    onClick = { navController.navigate("edit_profile") },
                                    modifier = Modifier.size(16.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Profile",
                                        tint = Color(0xFFBB86FC)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = userProfile.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Right Side: Coin Image and Available Coins
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val coinBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_coin)
                        if (coinBitmap != null) {
                            Image(
                                bitmap = coinBitmap.asImageBitmap(),
                                contentDescription = "Coin Image",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            // Fallback placeholder for coin image
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${userProfile.alfaCoins}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFBB86FC),
                            fontSize = 16.sp
                        )
                    }
                }

                // Followers and Your Posts Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            navController.navigate("follow_screen")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBB86FC),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Followers: $followersCount",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            navController.navigate("user_posts")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBB86FC),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Your Posts",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // About Me Section
                AboutMeSection(
                    aboutMe = userProfile.aboutMe,
                    onUpdateAboutMe = { newAboutMe ->
                        DummyData.updateAboutMe(newAboutMe)
                    }
                )
            }
        }

        // Options in Two Columns
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Column: Buy Alfa Coins, Choose Language
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Buy Alfa Coins
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable {
                                navController.navigate("coin_purchase")
                            }
                            .border(
                                width = 1.dp,
                                color = Color(0xFFBB86FC),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFBB86FC),
                                            Color(0xFF4361EE)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Buy Alfa Coins",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    // Choose Language
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable {
                                navController.navigate("language_selection")
                            }
                            .border(
                                width = 1.dp,
                                color = Color(0xFFBB86FC),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFBB86FC),
                                            Color(0xFF4361EE)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Choose Language",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

                // Right Column: Support, Share And Reward, Upload Your Comic
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Support
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable {
                                navController.navigate("support")
                            }
                            .border(
                                width = 1.dp,
                                color = Color(0xFFBB86FC),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFBB86FC),
                                            Color(0xFF4361EE)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Support",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    // Share And Reward
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable {
                                navController.navigate("share_and_reward")
                            }
                            .border(
                                width = 1.dp,
                                color = Color(0xFFBB86FC),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFBB86FC),
                                            Color(0xFF4361EE)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Share And Reward",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    // Upload Your Comic
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable {
                                navController.navigate("upload_comic")
                            }
                            .border(
                                width = 1.dp,
                                color = Color(0xFFBB86FC),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFBB86FC),
                                            Color(0xFF4361EE)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Upload Your Comic",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Spacer to push content up if needed
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Profile Picture Selection Dialog
    if (showPictureDialog) {
        Dialog(onDismissRequest = { showPictureDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF121212))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Choose Profile Picture",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    // Option to pick image from gallery
                    Button(
                        onClick = {
                            pickImageLauncher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBB86FC),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Pick from Gallery", fontSize = 16.sp)
                    }

                    TextButton(onClick = { showPictureDialog = false }) {
                        Text("Cancel", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun AboutMeSection(
    aboutMe: String,
    onUpdateAboutMe: (String) -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var newAboutMe by remember { mutableStateOf(aboutMe) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // About Me Title and Edit Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "About Me",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
            IconButton(
                onClick = { showEditDialog = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit About Me",
                    tint = Color(0xFFBB86FC)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // About Me Text
        Text(
            text = aboutMe,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Edit About Me Dialog
        if (showEditDialog) {
            Dialog(onDismissRequest = { showEditDialog = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF121212))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Edit About Me",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        OutlinedTextField(
                            value = newAboutMe,
                            onValueChange = { newAboutMe = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("About Me", color = Color.White) },
                            textStyle = LocalTextStyle.current.copy(color = Color.White),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                onUpdateAboutMe(newAboutMe)
                                showEditDialog = false
                            }),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color(0xFFBB86FC),
                                unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(onClick = { showEditDialog = false }) {
                                Text("Cancel", color = Color.White)
                            }
                            Button(
                                onClick = {
                                    onUpdateAboutMe(newAboutMe)
                                    showEditDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFBB86FC),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Save")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileOptionCard(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C2526))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            val iconBitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.ic_launcher_background)
            if (iconBitmap != null) {
                Icon(
                    bitmap = iconBitmap.asImageBitmap(),
                    contentDescription = "Option Icon",
                    tint = Color(0xFFBB86FC),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                // Fallback placeholder for icon
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Gray)
                )
            }
        }
    }
}