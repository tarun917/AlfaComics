package com.alfacomics.presentation.ui.screens.profile

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
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

@Composable
fun ShareAndRewardScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    var referralCode by remember { mutableStateOf("ALFA${(1000..9999).random()}") }
    var rewards by remember { mutableStateOf(DummyData.getReferralRewards()) }

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
                    .padding(top = 12.dp, bottom = 20.dp),
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
                    text = "Share & Reward",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.sp,
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

        // Referral Code Section
        item {
            // Scale animation for the referral card
            val animatedScale by animateFloatAsState(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    delayMillis = 0,
                    easing = FastOutSlowInEasing
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .scale(animatedScale)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
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
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Referral Code",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                            )
                        ),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Text(
                        text = referralCode,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                            )
                        ),
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Share this code with friends to earn rewards!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Rewards Section
        item {
            Text(
                text = "Your Rewards",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                    )
                ),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Scale animation for the rewards card
            val animatedScale by animateFloatAsState(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    delayMillis = 200,
                    easing = FastOutSlowInEasing
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .scale(animatedScale)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFBB86FC), Color(0xFF6200EE))
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Alfa Coins Earned",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        text = rewards.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFBB86FC), Color(0xFFFFD700))
                            )
                        ),
                        fontSize = 18.sp
                    )
                }
            }
        }

        // Share Button
        item {
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
                    // Share action
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Join Alfa Comics with my referral code: $referralCode and earn rewards! Download now: [App Link]")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share Referral Code"))
                    // Simulate earning rewards
                    DummyData.addReferralRewards(50)
                    rewards += 50
                    Toast.makeText(context, "Shared successfully! Earned 50 Alfa Coins!", Toast.LENGTH_SHORT).show()
                    isClicked = false
                }
            }

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
                    .padding(top = 12.dp)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        modifier = Modifier.size(18.dp) // Slightly reduced size
                    )
                    Spacer(modifier = Modifier.width(6.dp)) // Reduced from 8.dp
                    Text(
                        text = "Share Now",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp), // Reduced from 16.sp
                        modifier = Modifier.padding(vertical = 6.dp) // Reduced from 8.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}