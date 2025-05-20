package com.alfacomics.presentation.ui.screens.community

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alfacomics.R
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
    var showFullScreenImage by remember { mutableStateOf(false) }

    // Mock user ID for the current user (in a real app, this would come from auth)
    val currentUserId by remember { mutableStateOf("user_${System.currentTimeMillis()}") }

    // Access the context for sharing
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
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
                    onImageClick = { showFullScreenImage = true },
                    modifier = Modifier.fillMaxWidth()
                )

                // Reactions Row (Heart, WhatsApp, Facebook)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Heart Reaction Button with Count
                    ReactionButton(
                        reactionType = "heart",
                        icon = Icons.Default.Favorite,
                        reactionCount = post.reactions["heart"]?.size ?: 0,
                        hasReacted = post.reactions["heart"]?.contains(currentUserId) ?: false,
                        onClick = {
                            DummyData.addReactionToPost(post.id, "heart", currentUserId)
                        }
                    )

                    // WhatsApp Share Button
                    IconButton(
                        onClick = {
                            try {
                                val whatsappIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    setPackage("com.whatsapp") // Target WhatsApp specifically
                                    putExtra(Intent.EXTRA_TEXT, post.content)
                                }
                                context.startActivity(whatsappIntent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.padding(top = 4.dp) // Adjust padding to align vertically with Heart button
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_whatsapp),
                            contentDescription = "Share on WhatsApp",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Facebook Share Button
                    IconButton(
                        onClick = {
                            try {
                                val facebookIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    setPackage("com.facebook.katana") // Target Facebook specifically
                                    putExtra(Intent.EXTRA_TEXT, post.content)
                                }
                                context.startActivity(facebookIntent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Facebook is not installed", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.padding(top = 4.dp) // Adjust padding to align vertically with Heart button
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_facebook),
                            contentDescription = "Share on Facebook",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Comments
                CommunityComments(
                    post = post,
                    isCommentingEnabled = isCommentingEnabled,
                    onAddComment = onAddComment,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (showEditDialog) {
            EditPostDialog(
                post = post,
                onDismiss = { showEditDialog = false },
                onSave = { updatedPost ->
                    val posts = DummyData.getCommunityPosts().toMutableList()
                    val index = posts.indexOfFirst { it.id == post.id }
                    if (index != -1) {
                        posts[index] = updatedPost
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

        if (showFullScreenImage && post.imageUrl != null) {
            FullScreenImageViewer(
                imageUrl = post.imageUrl!!,
                onDismiss = { showFullScreenImage = false }
            )
        }
    }
}

@Composable
fun ReactionButton(
    reactionType: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    reactionCount: Int,
    hasReacted: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = reactionType,
            tint = if (hasReacted) Color(0xFFBB86FC) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = reactionCount.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = if (hasReacted) Color(0xFFBB86FC) else Color.Gray
        )
    }
}