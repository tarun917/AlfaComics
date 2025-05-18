package com.alfacomics.presentation.ui.screens.community

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
import com.alfacomics.presentation.ui.components.CommentDialog

@Composable
fun CommunityComments(
    post: CommunityPost,
    isCommentingEnabled: Boolean,
    onAddComment: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCommentDialog by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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

        // Add Comment Button (only if commenting is enabled)
        if (isCommentingEnabled) {
            TextButton(
                onClick = { showCommentDialog = true },
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFBB86FC))
            ) {
                Text("Add Comment")
            }
        } else {
            Text(
                text = "Commenting is turned off",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        // Comment Dialog
        if (showCommentDialog && isCommentingEnabled) {
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