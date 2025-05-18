package com.alfacomics.presentation.ui.screens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alfacomics.data.repository.CommunityPost
import com.alfacomics.presentation.ui.components.CommentDialog

@Composable
fun CommunityPostItem(
    post: CommunityPost,
    onAddComment: (Int, String) -> Unit
) {
    var showCommentDialog by remember { mutableStateOf(false) }

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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Post Content
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            // Add Comment Button
            TextButton(
                onClick = { showCommentDialog = true },
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFBB86FC))
            ) {
                Text("Comment")
            }

            // Display Comments
            if (post.comments.isNotEmpty()) {
                Text(
                    text = "Comments",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
                post.comments.forEach { comment ->
                    Text(
                        text = comment,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    )
                }
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