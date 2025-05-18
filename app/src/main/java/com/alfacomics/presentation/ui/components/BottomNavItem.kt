package com.alfacomics.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
sealed class BottomNavItem(val label: String, val icon: ImageVector, val route: String) {
    data object Home : BottomNavItem("Home", Icons.Default.Home, "home")
    data object Community : BottomNavItem("Community", Icons.Default.Group, "community")
    data object AlfaStore : BottomNavItem("AlfaStore", Icons.Default.ShoppingCart, "store")
    data object Favourite : BottomNavItem("Favourite", Icons.Default.Favorite, "favourite")
    data object Profile : BottomNavItem("Profile", Icons.Default.Person, "profile")
}