package com.alfacomics.presentation.ui.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.MotionDummyData

@Composable
fun ModernTab(
    navController: NavHostController
) {
    val genres = MotionDummyData.getGenres()
    val groupedMotionComics = genres.associateWith { genre ->
        MotionDummyData.getMotionComicsByGenre(genre)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp), // Reduced padding
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Grouped Motion Comics by Genre
        items(groupedMotionComics.entries.toList()) { (genre, genreMotionComics) ->
            Column {
                Text(
                    text = genre,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(2.dp, RoundedCornerShape(8.dp)),
                    textAlign = TextAlign.Start
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(genreMotionComics) { motionComic ->
                        MotionComicItem(
                            title = motionComic.title,
                            rating = motionComic.rating,
                            views = motionComic.views,
                            onWatchClick = {
                                navController.navigate("motion_comic_detail/${motionComic.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MotionComicItem(
    title: String,
    rating: Float,
    views: Int,
    onWatchClick: () -> Unit
) {
    // Interaction source to detect press state
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animation for scale and glow
    val animatedScale by animateFloatAsState(
        targetValue = if (isPressed) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnimation"
    )
    val glowAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.3f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "glowAnimation"
    )

    Box(
        modifier = Modifier
            .width(130.dp) // Further reduced card width
            .padding(vertical = 4.dp)
            .scale(animatedScale)
            .shadow(6.dp, shape = RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2A004D).copy(alpha = 0.8f), Color(0xFF1C2526).copy(alpha = 0.8f))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        // Glow Effect on Press
        Box(
            modifier = Modifier
                .matchParentSize()
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(12.dp),
                    spotColor = Color(0xFFBB86FC).copy(alpha = glowAlpha),
                    ambientColor = Color(0xFFFFD700).copy(alpha = glowAlpha)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Thumbnail with Overlays
            Box(
                modifier = Modifier
                    .size(100.dp, 150.dp) // Further reduced thumbnail size
                    .clip(RoundedCornerShape(8.dp))
            ) {
                // Static placeholder for cover image with gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cover\nPlaceholder",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 9.sp, // Adjusted font size for smaller thumbnail
                        textAlign = TextAlign.Center
                    )
                }

                // Overlay for Rating (Top-Left)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(3.dp)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 2.dp, vertical = 1.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(10.dp) // Adjusted icon size
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                        Text(
                            text = rating.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Overlay for Views (Top-Right)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(3.dp)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 2.dp, vertical = 1.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Views",
                        tint = Color.White,
                        modifier = Modifier.size(10.dp) // Adjusted icon size
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = formatViews(views),
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp, // Further reduced font size
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    )
                ),
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Watch Button (Adjusted Size)
            Button(
                onClick = onWatchClick,
                interactionSource = interactionSource,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(55.dp) // Further reduced width
                    .height(22.dp) // Further reduced height
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        ),
                        shape = RoundedCornerShape(4.dp) // Adjusted corner radius
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Text(
                    text = "Watch",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp) // Adjusted font size
                )
            }
        }
    }
}

private fun formatViews(count: Int): String {
    return when {
        count >= 1_000_000 -> "${count / 1_000_000}M"
        count >= 1_000 -> "${count / 1_000}K"
        else -> count.toString()
    }
}