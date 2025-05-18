package com.alfacomics.presentation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.alfacomics.data.repository.Episode // Corrected import for Episode

@Composable
fun ComicDetailScreen(
    navController: NavHostController,
    comicId: Int,
    onEpisodeClick: (Int) -> Unit
) {
    val comic = DummyData.getComicById(comicId)
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var isSubscribed by remember { mutableStateOf(DummyData.isUserSubscribed()) }
    val episodes by remember { derivedStateOf { DummyData.getEpisodesWithSubscription(comicId) } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Placeholder for cover image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cover\nPlaceholder",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        item {
            // Subscribe Button (if not subscribed)
            if (!isSubscribed) {
                Button(
                    onClick = {
                        DummyData.setUserSubscribed(true)
                        isSubscribed = true
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

        items(episodes) { episode ->
            EpisodeItem(
                episode = episode,
                isLocked = !episode.isFree && !isSubscribed,
                onClick = {
                    if (episode.isFree || isSubscribed) {
                        onEpisodeClick(episode.id)
                    }
                }
            )
        }
    }
}

@Composable
fun EpisodeItem(
    episode: Episode, // Now resolves correctly with the proper import
    isLocked: Boolean,
    onClick: () -> Unit
) {
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
            verticalAlignment = Alignment.CenterVertically
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
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = episode.title, // Now resolves correctly
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                if (isLocked) {
                    Text(
                        text = "Locked - Subscribe to Unlock",
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
}