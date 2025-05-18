package com.alfacomics.presentation.ui.screens.community

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.alfacomics.data.repository.PollOption
import com.alfacomics.presentation.ui.components.CommentDialog

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CommunityPostItem(
    post: CommunityPost,
    onAddComment: (Int, String) -> Unit
) {
    var showCommentDialog by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    // Initialize local pollVotes state with the correct number of options
    val optionCount = post.poll?.options?.size ?: 0
    var pollVotes by remember(post.id) {
        mutableStateOf(DummyData.getPollVotes(post.id).ifEmpty { MutableList(optionCount) { 0 } })
    }
    var votedOptionIndex by remember(post.id) { mutableStateOf(DummyData.getVotedOptionIndex(post.id)) }

    // Sync local state with global state when pollVotesMap updates
    LaunchedEffect(post.id) {
        val globalVotes = DummyData.getPollVotes(post.id)
        if (globalVotes.isNotEmpty()) {
            pollVotes = globalVotes
        }
    }

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
            // User Profile Section
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
                Column {
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
            }

            // Post Content
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            // Post Image (if available)
            if (post.imageUrl != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Image Placeholder\n(${post.imageUrl})",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // Poll (if available)
            if (post.poll != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .background(Color(0xFF2A2A2A), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = post.poll!!.question,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Calculate total votes for percentage
                    val totalVotes = pollVotes.sum().takeIf { it > 0 } ?: 1 // Avoid division by zero
                    post.poll!!.options.forEachIndexed { index, option ->
                        val voteCount = pollVotes.getOrElse(index) { 0 }
                        val percentage = (voteCount.toFloat() / totalVotes) * 100
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    // If user clicks on the same option, do nothing
                                    if (votedOptionIndex == index) return@clickable

                                    val updatedVotes = pollVotes.toMutableList()
                                    if (votedOptionIndex != -1 && votedOptionIndex != index) {
                                        // Deduct vote from previous option
                                        if (votedOptionIndex < updatedVotes.size) {
                                            updatedVotes[votedOptionIndex] = (updatedVotes[votedOptionIndex]) - 1
                                        }
                                    }
                                    // Add vote to new option
                                    updatedVotes[index] = (updatedVotes[index]) + 1
                                    DummyData.setPollVotes(post.id, updatedVotes)
                                    pollVotes = updatedVotes
                                    votedOptionIndex = index
                                    DummyData.setVotedOptionIndex(post.id, votedOptionIndex)
                                }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = option.text,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (votedOptionIndex == index) Color(0xFFBB86FC) else Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "$voteCount (${percentage.toInt()}%)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { percentage / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = if (votedOptionIndex == index) Color(0xFFBB86FC) else Color(0xFFBB86FC).copy(alpha = 0.5f),
                                trackColor = Color.Gray.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }

            // Comment Toggle
            Text(
                text = if (isExpanded) "Hide Comments" else "Show Comments (${post.comments.size})",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFBB86FC),
                modifier = Modifier
                    .clickable {
                        isExpanded = !isExpanded
                    }
            )

            // Display Comments
            if (isExpanded && post.comments.isNotEmpty()) {
                Text(
                    text = "Comments",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
                post.comments.forEach { comment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Profile Picture Placeholder
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = comment.username.first().toString(),
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                // Username and Timestamp
                                Column {
                                    Text(
                                        text = comment.username,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White
                                    )
                                    Text(
                                        text = comment.timestamp,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Comment Content
                            Text(
                                text = comment.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Add Comment Button
            TextButton(
                onClick = { showCommentDialog = true },
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFBB86FC))
            ) {
                Text("Add Comment")
            }

            // Comment Dialog
            if (showCommentDialog) {
                CommentDialog(
                    onConfirm = { comment ->
                        onAddComment(post.id, comment)
                        showCommentDialog = false
                    },
                    onDismiss = { showCommentDialog = false }
                )
            }
        }
    }
}