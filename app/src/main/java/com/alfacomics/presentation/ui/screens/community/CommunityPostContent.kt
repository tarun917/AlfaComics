package com.alfacomics.presentation.ui.screens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

@Composable
fun CommunityPostContent(
    post: CommunityPost,
    onImageClick: () -> Unit, // Added callback for image click
    modifier: Modifier = Modifier
) {
    var pollVotes by remember(post.id) {
        mutableStateOf(DummyData.getPollVotes(post.id).ifEmpty { MutableList(post.poll?.options?.size ?: 0) { 0 } })
    }
    var votedOptionIndex by remember(post.id) { mutableStateOf(DummyData.getVotedOptionIndex(post.id)) }

    // Sync local state with global state when pollVotesMap updates
    LaunchedEffect(post.id) {
        val globalVotes = DummyData.getPollVotes(post.id)
        if (globalVotes.isNotEmpty()) {
            pollVotes = globalVotes
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Post Content
        Text(
            text = post.content,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

        // Post Image (if available)
        ImageHandler(
            imageUrl = post.imageUrl,
            onImageClick = onImageClick, // Pass the click callback
            modifier = Modifier.fillMaxWidth()
        )

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
    }
}