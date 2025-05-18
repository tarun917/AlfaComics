package com.alfacomics.presentation.ui.screens.community

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alfacomics.data.repository.CommunityPost
import com.alfacomics.data.repository.DummyData

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CommunityPostItem(
    post: CommunityPost,
    onAddComment: (Int, String) -> Unit
) {
    var isCommentingEnabled by remember(post.id) { mutableStateOf(true) }
    var showEditDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // User Profile Section with Menu Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Picture Placeholder
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.username.first().toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Username and Timestamp
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.username,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                    Text(
                        text = post.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                // Menu Icon and Options
                PostMenu(
                    post = post,
                    isCommentingEnabled = isCommentingEnabled,
                    onToggleCommenting = { isCommentingEnabled = !isCommentingEnabled },
                    onEdit = { showEditDialog = true }
                )
            }

            // Post Content, Image, and Poll
            CommunityPostContent(
                post = post,
                modifier = Modifier.fillMaxWidth()
            )

            // Comments
            CommunityComments(
                post = post,
                isCommentingEnabled = isCommentingEnabled,
                onAddComment = onAddComment,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // Edit Dialog
    if (showEditDialog) {
        EditPostDialog(
            post = post,
            onDismiss = { showEditDialog = false },
            onSave = { updatedPost ->
                // Replace the old post with the updated post
                val posts = DummyData.getCommunityPosts().toMutableList()
                val index = posts.indexOfFirst { it.id == post.id }
                if (index != -1) {
                    posts[index] = updatedPost
                    // Update the posts in DummyData
                    val communityPostsField = DummyData::class.java.getDeclaredField("communityPosts")
                    communityPostsField.isAccessible = true
                    @Suppress("UNCHECKED_CAST")
                    val communityPosts = communityPostsField.get(DummyData) as MutableList<CommunityPost>
                    communityPosts.clear()
                    communityPosts.addAll(posts)
                }
            }
        )
    }
}