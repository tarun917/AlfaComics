package com.alfacomics.presentation.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.Episode

@Composable
fun ComicDetailScreen(
    navController: NavHostController,
    comicId: Int,
    onEpisodeClick: (Int) -> Unit
) {
    val comic = DummyData.getComicById(comicId)
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var isSubscribed by remember { mutableStateOf(DummyData.isUserSubscribed()) }
    var isFavorite by remember { mutableStateOf(DummyData.isFavoriteComic(comicId)) }
    val episodes by remember { derivedStateOf { DummyData.getEpisodesWithSubscription(comicId) } }

    // State to trigger recomposition after unlocking an episode
    var episodeListState by remember { mutableStateOf(episodes) }
    // State for showing insufficient coins dialog
    var showInsufficientCoinsDialog by remember { mutableStateOf(false) }

    // Debug logs for initial state
    Log.d("ComicDetailScreen", "isLoggedIn: ${DummyData.isLoggedIn}")
    Log.d("ComicDetailScreen", "isSubscribed: $isSubscribed")
    Log.d("ComicDetailScreen", "User alfaCoins: ${DummyData.getUserProfile().alfaCoins}")

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
            ) {
                // Display the cover image
                if (comic != null) {
                    Image(
                        painter = painterResource(id = comic.coverImageResId),
                        contentDescription = "Comic Cover",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    // Fallback placeholder if comic is null
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cover\nNot Available",
                            color = Color.White,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
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
                                DummyData.toggleFavoriteComic(comicId)
                                isFavorite = DummyData.isFavoriteComic(comicId)
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
                        navController.navigate("premium")
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
                Text(
                    text = comic?.description ?: "Description not available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDescriptionExpanded = !isDescriptionExpanded }
                )
                Text(
                    text = if (isDescriptionExpanded) "Show Less" else "Show More",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFBB86FC),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDescriptionExpanded = !isDescriptionExpanded }
                )
            }
        }

        // Episode List
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
                isLocked = !episode.isFree && !isSubscribed,
                onClick = {
                    if (episode.isFree || isSubscribed) {
                        onEpisodeClick(episode.id)
                    }
                },
                onUnlockClick = {
                    Log.d("ComicDetailScreen", "Unlock button clicked for episode ${episode.id}")
                    if (!isSubscribed && !episode.isFree) {
                        val success = DummyData.unlockEpisodeWithCoins(comicId, episode.id, 10)
                        Log.d("ComicDetailScreen", "Unlock success: $success")
                        if (success) {
                            episodeListState = DummyData.getEpisodesWithSubscription(comicId)
                            Log.d("ComicDetailScreen", "Episode list updated after unlock")
                        } else {
                            showInsufficientCoinsDialog = true
                            Log.d("ComicDetailScreen", "Showing insufficient coins dialog")
                        }
                    }
                }
            )
        }
    }

    // Insufficient Coins Dialog
    if (showInsufficientCoinsDialog) {
        Log.d("ComicDetailScreen", "Insufficient coins dialog shown")
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
                    text = "You don't have enough coins to unlock this episode. You need 10 coins, but you have ${DummyData.getUserProfile().alfaCoins} coins.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showInsufficientCoinsDialog = false
                        Log.d("ComicDetailScreen", "Dialog dismissed")
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
    episode: Episode,
    isLocked: Boolean,
    onClick: () -> Unit,
    onUnlockClick: () -> Unit
) {
    // Debug log for isLocked state
    Log.d("EpisodeItem", "Episode ${episode.id}: isLocked = $isLocked, isFree = ${episode.isFree}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isLocked, onClick = onClick),
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
                modifier = Modifier.weight(1f) // Ensure the left content takes available space
            ) {
                // Placeholder for episode thumbnail
                Box(
                    modifier = Modifier
                        .size(80.dp, 50.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Thumb",
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Episode Title and Lock Status
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp) // Add padding to avoid overlap with button
                ) {
                    Text(
                        text = episode.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isLocked) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = Color.Red,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Locked (10 Coins)",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Red
                            )
                        } else {
                            Text(
                                text = "Free",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Green
                            )
                        }
                    }
                }
            }

            // Unlock Button (if locked)
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
                        .wrapContentWidth() // Ensure button takes only necessary width
                ) {
                    Text(
                        text = "Unlock",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}