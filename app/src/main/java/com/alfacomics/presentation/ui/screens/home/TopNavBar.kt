package com.alfacomics.presentation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfacomics.data.repository.DummyData
import com.alfacomics.presentation.ui.components.GlassmorphicSurface

@Composable
fun TopNavBar(
    onPremiumClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    // Get the count of notifications for the current user
    val notificationCount = remember { DummyData.getNotifications() }.size

    GlassmorphicSurface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        cornerRadius = 16.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo and Title
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GradientACLogo()
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Actions: Premium, Search, Notifications
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    onClick = onPremiumClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBB86FC),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Premium",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
                Box {
                    IconButton(onClick = onNotificationClick) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                    // Show notification count badge if there are notifications
                    if (notificationCount > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 10.dp, y = (-10).dp)
                                .background(Color.Red, shape = androidx.compose.foundation.shape.CircleShape)
                                .size(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = notificationCount.toString(),
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GradientACLogo() {
    val gradient = Brush.horizontalGradient(
        listOf(
            Color(0xFFF72585), // Pinkish
            Color(0xFF4361EE)  // Bluish
        )
    )
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(brush = gradient)) {
                append("AC")
            }
        },
        style = MaterialTheme.typography.headlineMedium
    )
}