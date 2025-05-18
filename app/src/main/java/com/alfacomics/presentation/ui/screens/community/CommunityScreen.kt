package com.alfacomics.presentation.ui.screens.community

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.alfacomics.data.repository.CommunityPost
import com.alfacomics.data.repository.DummyData

@SuppressLint("UnrememberedMutableState")
@Composable
fun CommunityScreen() {
    var postContent by remember { mutableStateOf("") }
    val posts: List<CommunityPost> by derivedStateOf { DummyData.getCommunityPosts() }  // Explicitly specify type

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        // Post Input Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = postContent,
                onValueChange = { postContent = it },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                placeholder = { Text("Share your thoughts...", color = Color.White.copy(alpha = 0.7f)) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    if (postContent.isNotBlank()) {
                        DummyData.addCommunityPost(postContent)
                        postContent = ""
                    }
                }),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFBB86FC),
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                )
            )
            IconButton(
                onClick = {
                    if (postContent.isNotBlank()) {
                        DummyData.addCommunityPost(postContent)
                        postContent = ""
                    }
                },
                modifier = Modifier.size(48.dp),
                enabled = postContent.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Post",
                    tint = if (postContent.isNotBlank()) Color(0xFFBB86FC) else Color.Gray
                )
            }
        }

        // Community Feed
        if (posts.isEmpty()) {
            Text(
                text = "No posts yet. Be the first to share!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(posts) { post ->
                    CommunityPostItem(
                        post = post,
                        onAddComment = { postId: Int, comment: String ->
                            DummyData.addCommentToPost(postId, comment)
                        }
                    )
                }
            }
        }
    }
}