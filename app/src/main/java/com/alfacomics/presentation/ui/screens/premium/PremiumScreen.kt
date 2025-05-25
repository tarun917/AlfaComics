package com.alfacomics.presentation.ui.screens.premium

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.alfacomics.data.repository.DummyData

@Composable
fun PremiumScreen(
    navController: NavHostController
) {
    val isSubscribed by remember { derivedStateOf { DummyData.isUserSubscribed() } }

    // Gradient background for the screen
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF1A0033), Color(0xFF121212)),
        startY = 0f,
        endY = 1000f
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Button and Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Go Premium!",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 28.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        )
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Current Status (if already subscribed)
        if (isSubscribed) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "You are already a Premium member! 🎉",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                                )
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Enjoy unlimited access to all Comics and Motion Comics.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Display subscription details
                        DummyData.getPremiumSubscription()?.let { subscription ->
                            Text(
                                text = "Plan: ${subscription.planDuration}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Price: ${subscription.price}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Subscribed on: ${subscription.subscriptionStartDate}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            // Benefits of Premium
            item {
                Text(
                    text = "Why Go Premium?",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Start
                )

                BenefitItem(
                    benefit = "Unlock all episodes of Comics and Motion Comics for free!",
                    emoji = "🔓",
                    index = 0
                )
                BenefitItem(
                    benefit = "Access exclusive content!",
                    emoji = "✨",
                    index = 1
                )
                BenefitItem(
                    benefit = "Enjoy an ad-free experience!",
                    emoji = "🚫",
                    index = 2
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Premium Plans
            item {
                Text(
                    text = "Choose Your Plan",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Start
                )

                PremiumPlanCard(
                    planName = "3-Month Plan",
                    price = "₹349",
                    onSubscribe = {
                        navController.navigate("payment/3-Month/₹349") {
                            launchSingleTop = true
                        }
                    },
                    index = 0
                )

                PremiumPlanCard(
                    planName = "6-Month Plan",
                    price = "₹499",
                    onSubscribe = {
                        navController.navigate("payment/6-Month/₹499") {
                            launchSingleTop = true
                        }
                    },
                    index = 1
                )

                PremiumPlanCard(
                    planName = "12-Month Plan",
                    price = "₹799",
                    isRecommended = true, // Highlight this plan
                    onSubscribe = {
                        navController.navigate("payment/12-Month/₹799") {
                            launchSingleTop = true
                        }
                    },
                    index = 2
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun BenefitItem(benefit: String, emoji: String, index: Int) {
    // Fade-in animation for benefits
    val animatedScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = index * 200,
            easing = FastOutSlowInEasing
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .scale(animatedScale)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                ),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(end = 12.dp)
            )
            Text(
                text = benefit,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun PremiumPlanCard(
    planName: String,
    price: String,
    onSubscribe: () -> Unit,
    index: Int,
    isRecommended: Boolean = false
) {
    // Scale animation for plan cards
    val animatedScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = index * 200,
            easing = FastOutSlowInEasing
        )
    )

    // Scale animation state for the button on click
    val buttonScale = remember { Animatable(1f) }
    var isClicked by remember { mutableStateOf(false) }

    // Launch the animation when the button is clicked
    LaunchedEffect(isClicked) {
        if (isClicked) {
            buttonScale.animateTo(
                targetValue = 0.95f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            buttonScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            onSubscribe()
            isClicked = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .scale(animatedScale)
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = if (isRecommended) listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                    else listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2A004D), Color(0xFF1C2526))
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isRecommended) {
                Text(
                    text = "Recommended",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                        )
                    ),
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFBB86FC).copy(alpha = 0.2f), Color(0xFFFFD700).copy(alpha = 0.2f))
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .padding(bottom = 8.dp)
                )
            }
            Text(
                text = planName,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    )
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = price,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    isClicked = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(buttonScale.value)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Text(
                    text = "Subscribe Now",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}