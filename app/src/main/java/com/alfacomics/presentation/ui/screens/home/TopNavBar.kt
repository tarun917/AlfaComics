package com.alfacomics.presentation.ui.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfacomics.R
import com.alfacomics.data.repository.DummyData

@Composable
fun TopNavBar(
    onPremiumClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    // Get the count of unread notifications for the current user
    val notificationCount = remember { mutableStateOf(DummyData.getUnreadNotificationsCount()) }

    // Update notification count when notifications change
    LaunchedEffect(DummyData.getNotifications()) {
        notificationCount.value = DummyData.getUnreadNotificationsCount()
    }

    // Animation states for buttons/icons
    var premiumButtonPressed by remember { mutableStateOf(false) }
    var searchIconPressed by remember { mutableStateOf(false) }
    var notificationIconPressed by remember { mutableStateOf(false) }

    val premiumButtonScale by animateFloatAsState(
        targetValue = if (premiumButtonPressed) 0.95f else 1f,
        label = "PremiumButtonScale"
    )
    val searchIconScale by animateFloatAsState(
        targetValue = if (searchIconPressed) 0.95f else 1f,
        label = "SearchIconScale"
    )
    val notificationIconScale by animateFloatAsState(
        targetValue = if (notificationIconPressed) 0.95f else 1f,
        label = "NotificationIconScale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFBB86FC).copy(alpha = 0.2f),
                        Color(0xFF4361EE).copy(alpha = 0.2f)
                    )
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo
        Column(
            modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.topnav_logo),
                contentDescription = "TopNav Logo",
                modifier = Modifier.size(50.dp)
            )
        }

        // Actions: Premium, Search, Notifications
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    premiumButtonPressed = true
                    onPremiumClick()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF8A80),
                            Color(0xFF4361EE)
                        )
                    )
                ),
                modifier = Modifier
                    .scale(premiumButtonScale)
                    .width(IntrinsicSize.Min)
                    .clickable(
                        onClick = {
                            premiumButtonPressed = true
                            onPremiumClick()
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            ) {
                Text(
                    text = "Premium",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = {
                    searchIconPressed = true
                    onSearchClick()
                },
                modifier = Modifier
                    .scale(searchIconScale)
                    .padding(0.dp) // Remove default padding
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(2.dp)) // Reduced from 4.dp to 2.dp
            Box {
                IconButton(
                    onClick = {
                        notificationIconPressed = true
                        onNotificationClick()
                    },
                    modifier = Modifier
                        .scale(notificationIconScale)
                        .padding(0.dp) // Remove default padding
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White
                    )
                }
                // Show notification count badge if there are unread notifications
                if (notificationCount.value > 0) {
                    Box(
                        modifier = Modifier
                            .offset(x = 8.dp, y = (-8).dp)
                            .background(
                                Color(0xFFFF5252),
                                shape = androidx.compose.foundation.shape.CircleShape
                            )
                            .size(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = notificationCount.value.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}