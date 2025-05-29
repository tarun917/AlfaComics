package com.alfacomics.presentation.ui.screens.community

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfacomics.R
import com.alfacomics.data.repository.CommunityPost
import com.alfacomics.data.repository.DummyData

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CommunityPostItem(
    post: CommunityPost,
    onAddComment: (Int, String) -> Unit,
    onFollowClick: (String) -> Unit,
    onUsernameClick: (String) -> Unit
) {
    var isCommentingEnabled by remember(post.id) { mutableStateOf(true) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showFullScreenImage by remember { mutableStateOf(false) }

    // Mock user ID for the current user (in a real app, this would come from auth)
    val currentUserId by remember { mutableStateOf("user_${System.currentTimeMillis()}") }

    // Access the context for sharing
    val context = LocalContext.current

    // Follow button state
    val currentUser = DummyData.getUserProfile().username
    var isFollowing by remember { mutableStateOf(DummyData.isUserFollowed(currentUser, post.username)) }
    var showCheckMark by remember { mutableStateOf(false) }

    // Glow animation for the check mark
    val glowAlpha by animateFloatAsState(
        targetValue = if (showCheckMark) 0.6f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "glowAnimation"
    )

    // Reset check mark visibility after 2 seconds
    LaunchedEffect(showCheckMark) {
        if (showCheckMark) {
            kotlinx.coroutines.delay(2000)
            showCheckMark = false
        }
    }

    // Fetch user profile to get badges
    val userProfile = DummyData.getUserProfileByUsername(post.username)
    // Show only the highest-tier badge (if multiple badges exist)
    val highestBadge = userProfile?.badges?.maxByOrNull { badge ->
        when (badge.name) {
            "Platinum Star" -> 5
            "Diamond Star" -> 4
            "Gold Star" -> 3
            "Silver Star" -> 2
            "Copper Star" -> 1
            else -> 0
        }
    }

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
                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .clickable { onUsernameClick(post.username) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = post.username.first().toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Username, Timestamp, and Badge
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onUsernameClick(post.username) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = post.username,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            // Show the highest-tier badge if it exists
                            highestBadge?.let { badge ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star, // Temporary star icon
                                        contentDescription = badge.name,
                                        tint = badge.color,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = badge.name,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = badge.color,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                        Text(
                            text = post.timestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }

                    // Follow Button or Check Mark
                    if (currentUser != post.username) { // Don't show follow button for the user's own posts
                        if (showCheckMark) {
                            // Show glowing check mark
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = RoundedCornerShape(8.dp),
                                        spotColor = Color(0xFFBB86FC).copy(alpha = glowAlpha),
                                        ambientColor = Color(0xFFFFD700).copy(alpha = glowAlpha)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Followed",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else if (!isFollowing) {
                            // Show Follow button
                            Button(
                                onClick = {
                                    onFollowClick(post.username)
                                    isFollowing = true
                                    showCheckMark = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Follow",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

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
                                    setPackage("com.whatsapp")
                                    putExtra(Intent.EXTRA_TEXT, post.content)
                                }
                                context.startActivity(whatsappIntent)
                            } catch (_: Exception) {
                                Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.padding(top = 4.dp)
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
                                    setPackage("com.facebook.katana")
                                    putExtra(Intent.EXTRA_TEXT, post.content)
                                }
                                context.startActivity(facebookIntent)
                            } catch (_: Exception) {
                                Toast.makeText(context, "Facebook is not installed", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.padding(top = 4.dp)
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