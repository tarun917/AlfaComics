package com.alfacomics

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alfacomics.data.repository.AuthRepository
import com.alfacomics.data.repository.ComicRepository
import com.alfacomics.presentation.ui.components.BottomNavBar
import com.alfacomics.presentation.ui.navigation.NavGraph
import com.alfacomics.presentation.ui.screens.community.CommunityViewModel
import com.alfacomics.presentation.ui.screens.community.LocalActivityResultLauncher
import com.alfacomics.presentation.ui.screens.home.TopNavBar
import com.alfacomics.presentation.ui.theme.AlfaComicsTheme
import com.alfacomics.presentation.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

val LocalActivity = compositionLocalOf<Activity?> { null }
val LocalActivityResultLauncher = compositionLocalOf<ActivityResultLauncher<String>?> { null }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            // CommunityViewModel will handle image URI later
        }

        setContent {
            AlfaComicsTheme {
                CompositionLocalProvider(
                    LocalActivity provides this,
                    LocalActivityResultLauncher provides imagePickerLauncher
                ) {
                    AlfaComicsApp()
                }
            }
        }
    }
}

@Composable
fun AlfaComicsApp() {
    val navController = rememberNavController()
    val communityViewModel: CommunityViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(AuthRepository)
    )
    val comicRepository = ComicRepository(authViewModel)

    LaunchedEffect(authViewModel.isLoggedIn) {
        if (authViewModel.isLoggedIn.value) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    var isLandscape by rememberSaveable { mutableStateOf(false) }
    val activity = LocalActivity.current

    LaunchedEffect(isLandscape) {
        activity?.requestedOrientation = if (isLandscape) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    val onOrientationChange: (Boolean) -> Unit = { landscape ->
        isLandscape = landscape
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val shouldShowTopBar = currentDestination == "home"
    val shouldShowBottomBar = currentDestination != "login" && currentDestination != "signup" && !isLandscape

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
            if (shouldShowBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            communityViewModel = communityViewModel,
            authViewModel = authViewModel,
            comicRepository = comicRepository,
            isLandscape = isLandscape,
            onOrientationChange = onOrientationChange
        )
    }
}