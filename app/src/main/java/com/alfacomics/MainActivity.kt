package com.alfacomics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alfacomics.presentation.ui.components.BottomNavBar
import com.alfacomics.presentation.ui.navigation.NavGraph
import com.alfacomics.presentation.ui.screens.home.TopNavBar
import com.alfacomics.presentation.ui.theme.AlfaComicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlfaComicsTheme {
                AlfaComicsApp()
            }
        }
    }
}

@Composable
fun AlfaComicsApp() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val shouldShowTopBar = (currentDestination == "home")

    Scaffold(
        topBar = {
            if (shouldShowTopBar) {
                TopNavBar(
                    onPremiumClick = { navController.navigate("premium") },
                    onSearchClick = { navController.navigate("search") },
                    onNotificationClick = { navController.navigate("notifications") }
                )
            }
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}