package com.alfacomics.presentation.ui.screens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun FullScreenImageViewer(
    imageUrl: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .clickable(onClick = { onDismiss() }, indication = null, interactionSource = null) // Dismiss on background click
    ) {
        // Close button at the top-right corner
        IconButton(
            onClick = { onDismiss() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(48.dp)
                .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        // Full-screen image
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "Full Screen Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 48.dp),
            contentScale = ContentScale.Fit,
            success = {
                SubcomposeAsyncImageContent()
            }
        )
    }
}