package com.alfacomics.presentation.ui.screens.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
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
    // Redirect to login if not logged in
    LaunchedEffect(Unit) {
        if (!DummyData.isLoggedIn) {
            navController.navigate("login") {
                popUpTo("profile") { inclusive = true }
            }
        }
    }

    val userProfile = DummyData.getUserProfile()
    val favoriteComicsCount by derivedStateOf { DummyData.getFavoriteComicsCount() }
    val communityPostsCount by derivedStateOf { DummyData.getCommunityPostsCount() }
    val context = LocalContext.current

    // State for profile picture selection dialog
    var showPictureDialog by remember { mutableStateOf(false) }
    var selectedPictureResourceId by remember { mutableStateOf(userProfile.profilePictureResourceId) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp)
            .padding(bottom = 56.dp), // Added padding to ensure bottom navigation is visible
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Picture with Edit Option
                    Box(
                        modifier = Modifier.size(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = selectedPictureResourceId),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                        IconButton(
                            onClick = { showPictureDialog = true },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(32.dp)
                                .background(Color(0xFFBB86FC), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile Picture",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Username and Email with Edit Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = userProfile.username,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = userProfile.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { navController.navigate("edit_profile") },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = Color(0xFFBB86FC)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // About Me Section
                    AboutMeSection(
                        aboutMe = userProfile.aboutMe,
                        onUpdateAboutMe = { newAboutMe ->
                            DummyData.updateAboutMe(newAboutMe)
                        }
                    )
                }
            }
        }

        // Options Cards
        item {
            ProfileOptionCard(title = "Alfa Coins: ${userProfile.alfaCoins}") {
                Toast.makeText(context, "Alfa Coins: ${userProfile.alfaCoins}", Toast.LENGTH_SHORT).show()
            }
        }
        item {
            ProfileOptionCard(title = "Buy Alfa Coins") {
                navController.navigate("coin_purchase")
            }
        }
        item {
            ProfileOptionCard(title = "Choose Language") {
                navController.navigate("language_selection")
            }
        }
        item {
            ProfileOptionCard(title = "Support") {
                navController.navigate("support")
            }
        }
        item {
            ProfileOptionCard(title = "Share And Reward") {
                navController.navigate("share_and_reward")
            }
        }
        item {
            ProfileOptionCard(title = "Upload Your Comic") {
                navController.navigate("upload_comic")
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

                    // Simulate picture options (using dummy resources)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Picture Option 1",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable {
                                    selectedPictureResourceId = R.drawable.ic_launcher_background
                                    DummyData.updateProfilePicture(R.drawable.ic_launcher_background)
                                    showPictureDialog = false
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Picture Option 2",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable {
                                    selectedPictureResourceId = R.drawable.ic_launcher_background
                                    DummyData.updateProfilePicture(R.drawable.ic_launcher_background)
                                    showPictureDialog = false
                                }
                        )
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
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Option Icon",
                tint = Color(0xFFBB86FC),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}