package com.alfacomics.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
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
import com.alfacomics.data.repository.Comic
import com.alfacomics.data.repository.MotionComic

@Composable
fun <T> HorizontalComicScroll(
    items: List<T>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onClick: (T) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp), // Gap between cards
        contentPadding = PaddingValues(horizontal = 0.dp) // Ensure no extra padding at the edges
    ) {
        items(items) { item ->
            when (item) {
                is Comic -> {
                    ComicCard(
                        comic = item,
                        onClick = { onClick(item) }
                    )
                }
                is MotionComic -> {
                    MotionComicCard(
                        motionComic = item,
                        onClick = { onClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun ComicCard(
    comic: Comic,
    onClick: () -> Unit
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
            .width(140.dp)
            .padding(vertical = 4.dp)
            .scale(animatedScale)
            .shadow(6.dp, shape = RoundedCornerShape(8.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2A004D).copy(alpha = 0.8f), Color(0xFF1C2526).copy(alpha = 0.8f))
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        // Glow Effect on Press
        Box(
            modifier = Modifier
                .matchParentSize()
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp),
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
                    .size(120.dp, 150.dp)
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
                        fontSize = 9.sp,
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
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                        Text(
                            text = comic.rating.toString(),
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
                        contentDescription = "Read Count",
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = formatViews(comic.readCount),
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Title (Below Cover Image)
            Text(
                text = comic.title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
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
        }
    }
}

@Composable
fun MotionComicCard(
    motionComic: MotionComic,
    onClick: () -> Unit
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
            .width(150.dp)
            .padding(vertical = 4.dp)
            .scale(animatedScale)
            .shadow(6.dp, shape = RoundedCornerShape(8.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2A004D).copy(alpha = 0.8f), Color(0xFF1C2526).copy(alpha = 0.8f))
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        // Glow Effect on Press
        Box(
            modifier = Modifier
                .matchParentSize()
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp),
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
                    .size(130.dp, 150.dp)
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
                        fontSize = 9.sp,
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
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                        Text(
                            text = motionComic.rating.toString(),
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
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = formatViews(motionComic.views),
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Title (Below Cover Image)
            Text(
                text = motionComic.title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
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