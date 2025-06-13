package com.alfacomics.presentation.ui.screens.community

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.Poll
import com.alfacomics.data.repository.PollOption

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CommunityScreen(
    navController: NavHostController,
    viewModel: CommunityViewModel
) {
    var newPostContent by remember { mutableStateOf(TextFieldValue("")) }
    var wordCount by remember { mutableStateOf(0) }
    var showWordLimitError by remember { mutableStateOf(false) }
    var showUserDropdown by remember { mutableStateOf(false) }
    var tagStartIndex by remember { mutableStateOf(-1) }
    var currentTagQuery by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var showPollDialog by remember { mutableStateOf(false) }
    var pollQuestion by remember { mutableStateOf("") }
    var pollOptions by remember { mutableStateOf(mutableListOf("", "")) }
    val posts = DummyData.getCommunityPosts()
    val maxWordLimit = 512
    val maxPollOptions = 6
    val mockUsernames = DummyData.getMockUsernames()

    LaunchedEffect(newPostContent) {
        val words = newPostContent.text.trim().split("\\s+".toRegex())
        wordCount = if (newPostContent.text.isBlank()) 0 else words.size
        showWordLimitError = wordCount > maxWordLimit

        val cursorPosition = newPostContent.selection.start
        val lastHashIndex = newPostContent.text.lastIndexOf('#', cursorPosition - 1)
        if (lastHashIndex >= 0 && (cursorPosition == lastHashIndex + 1 || !newPostContent.text.substring(lastHashIndex + 1, cursorPosition).contains(" "))) {
            tagStartIndex = lastHashIndex
            currentTagQuery = newPostContent.text.substring(lastHashIndex + 1, cursorPosition)
            showUserDropdown = true
        } else {
            tagStartIndex = -1
            currentTagQuery = ""
            showUserDropdown = false
            searchQuery = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            Box {
                OutlinedTextField(
                    value = newPostContent,
                    onValueChange = { newText ->
                        val words = newText.text.trim().split("\\s+".toRegex())
                        val newWordCount = if (newText.text.isBlank()) 0 else words.size
                        if (newWordCount <= maxWordLimit) {
                            newPostContent = newText
                            showWordLimitError = false
                        } else {
                            showWordLimitError = true
                        }
                    },
                    label = { Text("What's on your mind?", color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (newPostContent.text.isNotBlank() && !showWordLimitError) {
                                    DummyData.addCommunityPost(newPostContent.text, viewModel.selectedImageUrl, null)
                                    newPostContent = TextFieldValue("")
                                    viewModel.setSelectedImage(null)
                                }
                            },
                            enabled = newPostContent.text.isNotBlank() && !showWordLimitError
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Post",
                                tint = if (newPostContent.text.isNotBlank() && !showWordLimitError) Color(0xFF00BE1A) else Color.Gray
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFBB86FC),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                DropdownMenu(
                    expanded = showUserDropdown,
                    onDismissRequest = {
                        showUserDropdown = false
                        searchQuery = ""
                    },
                    modifier = Modifier
                        .background(Color(0xFF1E1E1E))
                        .width(200.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search users...", color = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFBB86FC),
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color(0xFF2A2A2A),
                            unfocusedContainerColor = Color(0xFF2A2A2A)
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    val filteredUsernames = if (currentTagQuery.isEmpty() && searchQuery.isEmpty()) {
                        mockUsernames
                    } else if (searchQuery.isNotEmpty()) {
                        mockUsernames.filter { it.lowercase().contains(searchQuery.lowercase()) }
                    } else {
                        mockUsernames.filter { it.lowercase().contains(currentTagQuery.lowercase()) }
                    }

                    if (filteredUsernames.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No users found", color = Color.White) },
                            onClick = { showUserDropdown = false }
                        )
                    } else {
                        filteredUsernames.forEach { username ->
                            DropdownMenuItem(
                                text = { Text("#$username", color = Color.White) },
                                onClick = {
                                    val newText = newPostContent.text.substring(0, tagStartIndex + 1) + username + " "
                                    newPostContent = TextFieldValue(
                                        text = newText,
                                        selection = TextRange(newText.length)
                                    )
                                    showUserDropdown = false
                                    searchQuery = ""
                                }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.launchImagePicker()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Add Image",
                        tint = if (viewModel.selectedImageUrl == null) Color.White else Color(0xFFBB86FC)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                TextButton(
                    onClick = {
                        val newText = newPostContent.text + "#"
                        newPostContent = TextFieldValue(
                            text = newText,
                            selection = TextRange(newText.length)
                        )
                        showUserDropdown = true
                    }
                ) {
                    Text(
                        text = "#",
                        color = Color.White,
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                IconButton(
                    onClick = {
                        showPollDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Add Poll",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Words: $wordCount/$maxWordLimit",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (showWordLimitError) Color.Red else Color.Gray
                    )

                    if (showWordLimitError) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Word limit exceeded!",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Red
                        )
                    }
                }
            }
        }

        if (showPollDialog) {
            AlertDialog(
                onDismissRequest = {
                    showPollDialog = false
                    pollQuestion = ""
                    pollOptions = mutableListOf("", "")
                },
                title = { Text("Create a Poll", color = Color.White) },
                text = {
                    Column {
                        OutlinedTextField(
                            value = pollQuestion,
                            onValueChange = { pollQuestion = it },
                            label = { Text("Question", color = Color.White) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFBB86FC),
                                unfocusedBorderColor = Color.Gray,
                                focusedContainerColor = Color(0xFF1E1E1E),
                                unfocusedContainerColor = Color(0xFF1E1E1E)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        pollOptions.forEachIndexed { index, option ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = option,
                                    onValueChange = { newValue ->
                                        val updatedOptions = pollOptions.toMutableList()
                                        updatedOptions[index] = newValue
                                        pollOptions = updatedOptions
                                    },
                                    label = { Text("Option ${index + 1}", color = Color.White) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 4.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        focusedBorderColor = Color(0xFFBB86FC),
                                        unfocusedBorderColor = Color.Gray,
                                        focusedContainerColor = Color(0xFF1E1E1E),
                                        unfocusedContainerColor = Color(0xFF1E1E1E)
                                    )
                                )
                                if (index >= 2) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(
                                        onClick = {
                                            val updatedOptions = pollOptions.toMutableList()
                                            updatedOptions.removeAt(index)
                                            pollOptions = updatedOptions
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove Option",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }

                        if (pollOptions.size < maxPollOptions) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    val updatedOptions = pollOptions.toMutableList()
                                    updatedOptions.add("")
                                    pollOptions = updatedOptions
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFBB86FC),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Add Option (${pollOptions.size}/$maxPollOptions)")
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (pollQuestion.isNotBlank() && pollOptions.size >= 2 && pollOptions.all { it.isNotBlank() }) {
                                val poll = Poll(
                                    question = pollQuestion,
                                    options = pollOptions.map { PollOption(it, 0) }
                                )
                                DummyData.addCommunityPost(newPostContent.text, viewModel.selectedImageUrl, poll)
                                newPostContent = TextFieldValue("")
                                viewModel.setSelectedImage(null)
                                showPollDialog = false
                                pollQuestion = ""
                                pollOptions = mutableListOf("", "")
                            }
                        },
                        enabled = pollQuestion.isNotBlank() && pollOptions.size >= 2 && pollOptions.all { it.isNotBlank() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBB86FC),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Create")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showPollDialog = false
                            pollQuestion = ""
                            pollOptions = mutableListOf("", "")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancel")
                    }
                },
                containerColor = Color(0xFF1E1E1E)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(posts) { post ->
                CommunityPostItem(
                    post = post,
                    onAddComment = { postId: Int, comment: String ->
                        DummyData.addCommentToPost(postId, comment)
                    },
                    onFollowClick = { username: String ->
                        DummyData.followUser(DummyData.getUserProfile().username, username)
                    },
                    onUsernameClick = { username: String ->
                        navController.navigate("user_profile/$username")
                    }
                )
            }

            if (posts.isEmpty()) {
                item {
                    Text(
                        text = "No posts yet! Be the first to share something.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}