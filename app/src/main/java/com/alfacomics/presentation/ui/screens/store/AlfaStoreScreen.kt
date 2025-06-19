package com.alfacomics.presentation.ui.screens.store

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.AlfaStoreData
import com.alfacomics.data.repository.HardCopyComic
import com.alfacomics.pratilipitv.presentation.ui.components.HardCopyComicBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlfaStoreScreen(
    navController: NavHostController
) {
    var hardCopyComics by remember { mutableStateOf<List<HardCopyComic>?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        hardCopyComics = withContext(Dispatchers.IO) {
            AlfaStoreData.getAllHardCopyComics()
        }
        isLoading = false
    }

    val gradientBackground = Brush.linearGradient(
        colors = listOf(
            Color(0xFF121212),
            Color(0xFF1C2526)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    val infiniteTransition = rememberInfiniteTransition()
    val shineOffset by infiniteTransition.animateFloat(
        initialValue = -500f,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shineAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFFBB86FC).copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        start = Offset(shineOffset, shineOffset),
                        end = Offset(shineOffset + 500f, shineOffset + 500f)
                    )
                )
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFBB86FC),
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                spotColor = Color(0xFFBB86FC).copy(alpha = 0.3f)
                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp,
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                            )
                        ),
                        color = Color.White,
                        modifier = Modifier.shadow(2.dp, RoundedCornerShape(8.dp))
                    )
                }
            }
        } else {
            hardCopyComics?.let { comicList ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item(span = { GridItemSpan(2) }) {
                        HeaderSection(navController = navController)
                    }

                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFBB86FC).copy(alpha = 0.3f),
                                            Color(0xFF4361EE).copy(alpha = 0.3f)
                                        )
                                    )
                                )
                        )
                    }

                    items(comicList) { comic ->
                        HardCopyComicBox(
                            title = comic.title,
                            coverImageUrl = comic.coverImageUrl,
                            price = comic.price,
                            rating = comic.rating,
                            comicId = comic.id,
                            navController = navController
                        )
                    }
                }
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Failed to load comics",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                                )
                            ),
                            color = Color.White,
                            modifier = Modifier.shadow(2.dp, RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = {
                                hardCopyComics = null
                                isLoading = true
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                                )
                            )
                        ) {
                            Text(
                                text = "Retry",
                                style = MaterialTheme.typography.labelMedium,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderSection(navController: NavHostController) {
    var historyButtonPressed by remember { mutableStateOf(false) }
    val historyButtonScale by animateFloatAsState(
        targetValue = if (historyButtonPressed) 0.95f else 1f,
        label = "HistoryButtonScale"
    )
    val glowAlpha by animateFloatAsState(
        targetValue = if (historyButtonPressed) 0.5f else 0.2f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "glowAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
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
                    colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "AlfaStore - Hard Copies",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 24.sp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                    )
                ),
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .shadow(2.dp, RoundedCornerShape(8.dp)),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        spotColor = Color(0xFFBB86FC).copy(alpha = glowAlpha),
                        ambientColor = Color(0xFFFFD700).copy(alpha = glowAlpha)
                    )
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                        ),
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                        ),
                        shape = CircleShape
                    )
            ) {
                IconButton(
                    onClick = {
                        historyButtonPressed = true
                        navController.navigate("order_history")
                    },
                    modifier = Modifier
                        .scale(historyButtonScale)
                        .clickable(
                            onClick = {
                                historyButtonPressed = true
                                navController.navigate("order_history")
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Receipt,
                        contentDescription = "Order History",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}