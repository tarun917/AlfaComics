package com.alfacomics.presentation.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Community,
        BottomNavItem.AlfaStore,
        BottomNavItem.Favourite,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    // Debugging: Log the current route and hierarchy
    println("Current Route: $currentRoute")
    currentDestination?.hierarchy?.forEach { destination ->
        println("Hierarchy Route: ${destination.route}")
    }

    // Map nested routes to their parent tab routes
    val routeToTabMap = mapOf(
        "comic_detail/\\d+" to BottomNavItem.Home.route, // Maps comic_detail/{comicId} to Home tab
        "comic_reader/\\d+/\\d+" to BottomNavItem.Home.route, // Maps comic_reader/{comicId}/{episodeId} to Home tab
        "comic_purchase/\\d+" to BottomNavItem.AlfaStore.route, // Maps comic_purchase/{comicId} to AlfaStore tab
        "order_history" to BottomNavItem.AlfaStore.route // Maps order_history to AlfaStore tab
    )

    GlassmorphicSurface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        cornerRadius = 16.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ) {
            items.forEach { screen ->
                // Check if the current destination or any parent matches the screen's route
                val isDirectMatch = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                // Check if the current route is a nested route associated with this tab
                val isNestedMatch = routeToTabMap.entries.any { (pattern, tabRoute) ->
                    currentRoute?.matches(Regex(pattern)) == true && tabRoute == screen.route
                }
                val isSelected = isDirectMatch || isNestedMatch

                // Debugging: Log isSelected for each tab
                println("Tab: ${screen.label}, Route: ${screen.route}, isSelected: $isSelected, isDirectMatch: $isDirectMatch, isNestedMatch: $isNestedMatch")

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.2f else 1f,
                    animationSpec = spring(),
                    label = "scale"
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.label,
                            modifier = Modifier.scale(scale),
                            tint = if (isSelected) Color(0xFFBB86FC) else Color.White.copy(alpha = 0.7f)
                        )
                    },
                    label = {
                        Text(
                            text = screen.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) Color(0xFFBB86FC) else Color.White.copy(alpha = 0.7f)
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}