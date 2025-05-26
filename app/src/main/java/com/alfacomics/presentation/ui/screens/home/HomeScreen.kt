package com.alfacomics.presentation.ui.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("Comics", "Motion Comics")
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Gradient background for the screen with a subtle shine effect
    val gradientBackground = Brush.linearGradient(
        colors = listOf(
            Color(0xFF000000).copy(alpha = 0.95f),
            Color(0xFF000000).copy(alpha = 0.95f)
        ),
        start = androidx.compose.ui.geometry.Offset(0f, 0f),
        end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
    )

    // Animated shine effect for background
    val infiniteTransition = rememberInfiniteTransition()
    val shineOffset by infiniteTransition.animateFloat(
        initialValue = -500f,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shineAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        // Subtle shine overlay
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
                        start = androidx.compose.ui.geometry.Offset(shineOffset, shineOffset),
                        end = androidx.compose.ui.geometry.Offset(shineOffset + 500f, shineOffset + 500f)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
            }

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEachIndexed { index, title ->
                    TabItem(
                        title = title,
                        isSelected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        index = index
                    )
                }
            }

            // Content based on selected tab
            when (selectedTabIndex) {
                0 -> ClassicTab(navController = navController)
                1 -> ModernTab(navController = navController)
            }
        }
    }
}

@Composable
fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    index: Int
) {
    // Animation for scale, fade, and glow
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnimation"
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.6f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "alphaAnimation"
    )
    val glowAlpha by animateFloatAsState(
        targetValue = if (isSelected) 0.3f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "glowAnimation"
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .scale(animatedScale)
            .shadow(6.dp, shape = RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2A004D).copy(alpha = 0.8f), Color(0xFF1C2526).copy(alpha = 0.8f))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                brush = Brush.linearGradient(
                    colors = if (isSelected) listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                    else listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        // Neon Glow Effect
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

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = if (isSelected) listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                        else listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
                    )
                ),
                modifier = Modifier
                    .alpha(animatedAlpha)
                    .shadow(2.dp, RoundedCornerShape(8.dp))
            )
        }
    }
}