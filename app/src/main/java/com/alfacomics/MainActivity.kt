package com.alfacomics

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alfacomics.presentation.ui.components.BottomNavBar
import com.alfacomics.presentation.ui.navigation.NavGraph
import com.alfacomics.presentation.ui.screens.community.CommunityViewModel
import com.alfacomics.presentation.ui.screens.community.LocalActivityResultLauncher
import com.alfacomics.presentation.ui.screens.home.TopNavBar
import com.alfacomics.presentation.ui.theme.AlfaComicsTheme

// CompositionLocal to provide the Activity instance
val LocalActivity = compositionLocalOf<Activity?> { null }

class MainActivity : ComponentActivity() {
    // Create the ViewModel instance using ViewModelProvider
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set default orientation to portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Register the ActivityResultLauncher before setContent
        val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            // Update the selectedImageUrl property directly
            viewModel.selectedImageUrl = uri?.toString()
        }

        setContent {
            AlfaComicsTheme {
                // Provide the Activity and launcher to the composable tree
                CompositionLocalProvider(
                    LocalActivity provides this,
                    LocalActivityResultLauncher provides imagePickerLauncher
                ) {
                    AlfaComicsApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AlfaComicsApp(viewModel: CommunityViewModel) {
    val navController = rememberNavController()
    // Use rememberSavable to persist isLandscape across configuration changes
    var isLandscape by rememberSaveable { mutableStateOf(false) }

    // Access the Activity from LocalActivity
    val activity = LocalActivity.current

    // Handle orientation changes based on isLandscape state
    LaunchedEffect(isLandscape) {
        activity?.requestedOrientation = if (isLandscape) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    // Callback to toggle orientation
    val onOrientationChange: (Boolean) -> Unit = { landscape ->
        isLandscape = landscape
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val shouldShowTopBar = currentDestination == "home"

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
            if (!isLandscape) { // Hide BottomNavBar in landscape mode
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            isLandscape = isLandscape,
            onOrientationChange = onOrientationChange
        )
    }
}