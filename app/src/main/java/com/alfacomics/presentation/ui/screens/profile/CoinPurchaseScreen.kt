package com.alfacomics.presentation.ui.screens.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alfacomics.data.repository.DummyData

data class CoinPackage(val coins: Int, val price: Int)

@Composable
fun CoinPurchaseScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val coinPackages = listOf(
        CoinPackage(coins = 100, price = 99),
        CoinPackage(coins = 500, price = 399),
        CoinPackage(coins = 1000, price = 799)
    )

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
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Buy Alfa Coins",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                        )
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Current Balance
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
                        text = "Current Balance",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                            )
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${DummyData.getUserProfile().alfaCoins} Coins",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Coin Packages
        item {
            Text(
                text = "Choose a Package",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Start
            )

            coinPackages.forEachIndexed { index, coinPackage ->
                CoinPackageCard(
                    coins = coinPackage.coins,
                    price = "₹${coinPackage.price}",
                    onBuy = {
                        navController.navigate("coin_payment/${coinPackage.coins}/${coinPackage.price}") {
                            launchSingleTop = true
                        }
                    },
                    index = index,
                    isRecommended = coinPackage.coins == 1000 // Highlight the largest package
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CoinPackageCard(
    coins: Int,
    price: String,
    onBuy: () -> Unit,
    index: Int,
    isRecommended: Boolean = false
) {
    // Scale animation for cards
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
            onBuy()
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
                        fontWeight = FontWeight.Bold,
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
                text = "$coins Coins",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
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
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFFFD700), Color(0xFFBB86FC))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Text(
                    text = "Buy Now",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}