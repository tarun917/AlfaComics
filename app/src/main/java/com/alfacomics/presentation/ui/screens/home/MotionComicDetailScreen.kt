package com.alfacomics.presentation.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.MotionDummyData
import com.alfacomics.data.repository.MotionEpisode

@Composable
fun MotionComicDetailScreen(
    navController: NavHostController,
    motionComicId: Int,
    isLandscape: Boolean,
    onOrientationChange: (Boolean) -> Unit
) {
    val motionComic = MotionDummyData.getMotionComicById(motionComicId)
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(DummyData.isFavoriteMotionComic(motionComicId)) }
    var isSubscribed by remember { mutableStateOf(DummyData.isUserSubscribed()) }
    val episodes by remember { derivedStateOf { MotionDummyData.getMotionEpisodesWithSubscription(motionComicId) } }

    // State to trigger recomposition after unlocking an episode
    var episodeListState by remember { mutableStateOf(episodes) }
    // State for showing insufficient coins dialog
    var showInsufficientCoinsDialog by remember { mutableStateOf(false) }

    // Debug logs for initial state
    Log.d("MotionComicDetailScreen", "isLoggedIn: ${DummyData.isLoggedIn}")
    Log.d("MotionComicDetailScreen", "isSubscribed: $isSubscribed")
    Log.d("MotionComicDetailScreen", "User alfaCoins: ${DummyData.getUserProfile().alfaCoins}")

    if (motionComic == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Motion Comic not found",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Cover Image with Favourite Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            ) {
                // Placeholder for cover image
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cover\nPlaceholder",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // Favourite Button at Top-Right Corner
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            Color(0xFF1E1E1E).copy(alpha = 0.8f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Favourite",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
                        tint = Color(0xFFBB86FC),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                DummyData.toggleFavoriteMotionComic(motionComicId)
                                isFavorite = DummyData.isFavoriteMotionComic(motionComicId)
                            }
                    )
                }
            }
        }

        item {
            // Subscribe Button (if not subscribed)
            if (!isSubscribed) {
                Button(
                    onClick = {
                        DummyData.setUserSubscribed(true)
                        isSubscribed = true
                        episodeListState = MotionDummyData.getMotionEpisodesWithSubscription(motionComicId)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBB86FC),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Subscribe to Unlock All Episodes",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        item {
            // Description (Expandable)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // Count words in description
                val wordCount = motionComic.description.split(" ").size
                val isLongDescription = wordCount > 50
                val truncatedDescription = if (isLongDescription) {
                    motionComic.description.split(" ").take(50).joinToString(" ")
                } else {
                    motionComic.description
                }

                Text(
                    text = if (isDescriptionExpanded) motionComic.description else truncatedDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )

                if (isLongDescription) {
                    Text(
                        text = if (isDescriptionExpanded) "Show Less" else "More...",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFBB86FC),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isDescriptionExpanded = !isDescriptionExpanded }
                    )
                }
            }
        }

        // Episodes List
        item {
            Text(
                text = "Episodes",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        items(episodeListState) { episode ->
            EpisodeItem(
                episode = episode,
                isLocked = !episode.isUnlocked && !isSubscribed,
                onPlayClick = {
                    if (episode.isUnlocked || isSubscribed) {
                        navController.navigate("episode_player/$motionComicId/${episode.id}")
                    }
                },
                onUnlockClick = {
                    Log.d("MotionComicDetailScreen", "Unlock button clicked for episode ${episode.id}")
                    if (!isSubscribed && !episode.isUnlocked) {
                        val success = MotionDummyData.unlockMotionEpisodeWithCoins(motionComicId, episode.id, 50)
                        Log.d("MotionComicDetailScreen", "Unlock success: $success")
                        if (success) {
                            episodeListState = MotionDummyData.getMotionEpisodesWithSubscription(motionComicId)
                            Log.d("MotionComicDetailScreen", "Episode list updated after unlock")
                        } else {
                            showInsufficientCoinsDialog = true
                            Log.d("MotionComicDetailScreen", "Showing insufficient coins dialog")
                        }
                    }
                }
            )
        }
    }

    // Insufficient Coins Dialog
    if (showInsufficientCoinsDialog) {
        Log.d("MotionComicDetailScreen", "Insufficient coins dialog shown")
        AlertDialog(
            onDismissRequest = { showInsufficientCoinsDialog = false },
            title = {
                Text(
                    text = "Insufficient Coins",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            },
            text = {
                Text(
                    text = "You don't have enough coins to unlock this episode. You need 50 coins, but you have ${DummyData.getUserProfile().alfaCoins} coins.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showInsufficientCoinsDialog = false
                        Log.d("MotionComicDetailScreen", "Dialog dismissed")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBB86FC),
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            },
            dismissButton = {},
            containerColor = Color(0xFF1E1E1E),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun EpisodeItem(
    episode: MotionEpisode,
    isLocked: Boolean,
    onPlayClick: () -> Unit,
    onUnlockClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isLocked, onClick = onPlayClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) Color.Gray else Color(0xFF1E1E1E)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = episode.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                if (isLocked) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(50 Coins)",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Red
                    )
                }
            }

            if (isLocked) {
                Button(
                    onClick = onUnlockClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBB86FC),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(32.dp)
                        .padding(horizontal = 8.dp)
                        .wrapContentWidth()
                ) {
                    Text(
                        text = "Unlock",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            } else {
                Button(
                    onClick = onPlayClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBB86FC),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(32.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Play",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}