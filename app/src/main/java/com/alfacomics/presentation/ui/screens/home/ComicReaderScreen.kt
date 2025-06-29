package com.alfacomics.pratilipitv.presentation.ui.screens.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.EpisodeSocialData

@Composable
fun ComicReaderScreen(
    comicId: Int,
    episodeId: Int
) {
    val comic = DummyData.getComicById(comicId) ?: return
    val episode = comic.episodes.find { it.id == episodeId } ?: return
    val socialData = DummyData.getEpisodeSocialData(comicId, episodeId)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp)
    ) {
        // Episode Title
        item {
            Text(
                text = "${comic.title} - ${episode.title}",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        // Episode Content (Single PDF Image Placeholder)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display PDF thumbnail or placeholder
                Image(
                    painter = rememberAsyncImagePainter(model = episode.pdf_url),
                    contentDescription = "Episode PDF",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Note about PDF content
                Text(
                    text = "PDF content preview (full PDF viewing requires integration)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Social Features Section
        item {
            SocialFeaturesSection(
                socialData = socialData,
                comicTitle = comic.title,
                episodeTitle = episode.title
            )
        }
    }
}

@Composable
fun SocialFeaturesSection(
    socialData: EpisodeSocialData,
    comicTitle: String,
    episodeTitle: String
) {
    val context = LocalContext.current
    val isLiked = remember { mutableStateOf(socialData.isLiked) }
    val likeCount = remember { mutableIntStateOf(socialData.likeCount) }
    val tags = remember { mutableStateListOf<String>().apply { addAll(socialData.tags) } }
    val comments = remember { mutableStateListOf<String>().apply { addAll(socialData.comments) } }
    val showTagDialog = remember { mutableStateOf(false) }
    val tagInput = remember { mutableStateOf("") }
    val commentInput = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Like, Share, Tag Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Like Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clickable {
                    isLiked.value = !isLiked.value
                    likeCount.intValue += if (isLiked.value) 1 else -1
                    socialData.isLiked = isLiked.value
                    socialData.likeCount = likeCount.intValue
                }
            ) {
                Icon(
                    imageVector = if (isLiked.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked.value) Color.Red else Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "${likeCount.intValue}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }

            // Share Button
            IconButton(onClick = {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Check out this episode: $comicTitle - $episodeTitle")
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share Episode"))
            }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Tag Button
            IconButton(onClick = { showTagDialog.value = true }) {
                Icon(
                    imageVector = Icons.Default.Tag,
                    contentDescription = "Tag",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Display Tags
        if (tags.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach { tag ->
                    Text(
                        text = "#$tag",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFBB86FC),
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Tag Input Dialog
        if (showTagDialog.value) {
            AlertDialog(
                onDismissRequest = { showTagDialog.value = false },
                title = {
                    Text(
                        text = "Add a Tag",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                },
                text = {
                    OutlinedTextField(
                        value = tagInput.value,
                        onValueChange = { tagInput.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            if (tagInput.value.isNotBlank()) {
                                tags.add(tagInput.value)
                                socialData.tags.add(tagInput.value)
                                tagInput.value = ""
                                showTagDialog.value = false
                            }
                        })
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (tagInput.value.isNotBlank()) {
                            tags.add(tagInput.value)
                            socialData.tags.add(tagInput.value)
                            tagInput.value = ""
                            showTagDialog.value = false
                        }
                    }) {
                        Text("Add", color = Color(0xFFBB86FC))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showTagDialog.value = false }) {
                        Text("Cancel", color = Color.White)
                    }
                },
                containerColor = Color(0xFF121212),
                shape = RoundedCornerShape(8.dp)
            )
        }

        // Comments Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Comment Input
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = commentInput.value,
                    onValueChange = { commentInput.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        if (commentInput.value.isNotBlank()) {
                            comments.add(commentInput.value)
                            socialData.comments.add(commentInput.value)
                            commentInput.value = ""
                        }
                    })
                )
                IconButton(
                    onClick = {
                        if (commentInput.value.isNotBlank()) {
                            comments.add(commentInput.value)
                            socialData.comments.add(commentInput.value)
                            commentInput.value = ""
                        }
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Submit Comment",
                        tint = Color(0xFFBB86FC)
                    )
                }
            }

            // Display Comments
            if (comments.isNotEmpty()) {
                Text(
                    text = "Comments",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp)
                )
                comments.forEach { comment ->
                    Text(
                        text = comment,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    )
                }
            } else {
                Text(
                    text = "No comments yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}