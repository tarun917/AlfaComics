package com.alfacomics.presentation.ui.screens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun ImageHandler(
    imageUrl: String?,
    onImageClick: () -> Unit, // Added callback for image click
    modifier: Modifier = Modifier
) {
    if (imageUrl != null) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "Post Image",
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onImageClick), // Make the image clickable
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading image...",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Failed to load image",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            },
            success = {
                SubcomposeAsyncImageContent()
            }
        )
    }
}