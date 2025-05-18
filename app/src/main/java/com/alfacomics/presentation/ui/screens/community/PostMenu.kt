package com.alfacomics.presentation.ui.screens.community

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.alfacomics.data.repository.CommunityPost
import com.alfacomics.data.repository.DummyData

@Composable
fun PostMenu(
    post: CommunityPost,
    isCommentingEnabled: Boolean,
    onToggleCommenting: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { showMenu = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu",
                tint = Color.White
            )
        }
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier.background(Color(0xFF1E1E1E))
        ) {
            DropdownMenuItem(
                text = { Text("Edit", color = Color.White) },
                onClick = {
                    showMenu = false
                    onEdit()
                }
            )
            DropdownMenuItem(
                text = { Text("Delete", color = Color.White) },
                onClick = {
                    showMenu = false
                    // Remove the post from DummyData
                    val posts = DummyData.getCommunityPosts().toMutableList()
                    posts.removeIf { it.id == post.id }
                    // Update the posts in DummyData
                    val communityPostsField = DummyData::class.java.getDeclaredField("communityPosts")
                    communityPostsField.isAccessible = true
                    @Suppress("UNCHECKED_CAST")
                    val communityPosts = communityPostsField.get(DummyData) as MutableList<CommunityPost>
                    communityPosts.clear()
                    communityPosts.addAll(posts)
                    // Remove votes and voted option
                    DummyData.setPollVotes(post.id, mutableListOf())
                    DummyData.setVotedOptionIndex(post.id, -1)
                }
            )
            DropdownMenuItem(
                text = { Text(if (isCommentingEnabled) "Turn off Commenting" else "Turn on Commenting", color = Color.White) },
                onClick = {
                    showMenu = false
                    onToggleCommenting()
                }
            )
            DropdownMenuItem(
                text = { Text("Copy link", color = Color.White) },
                onClick = {
                    showMenu = false
                    // Mock copying link to clipboard
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Post Link", "https://alfacomics.com/post/${post.id}")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Link copied to clipboard", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}