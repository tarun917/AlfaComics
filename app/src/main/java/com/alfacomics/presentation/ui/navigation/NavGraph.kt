package com.alfacomics.presentation.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alfacomics.data.repository.DummyData
import com.alfacomics.presentation.ui.screens.auth.LoginScreen
import com.alfacomics.presentation.ui.screens.auth.SignUpScreen
import com.alfacomics.presentation.ui.screens.community.CommunityScreen
import com.alfacomics.presentation.ui.screens.community.CommunityViewModel
import com.alfacomics.presentation.ui.screens.favourite.FavouriteScreen
import com.alfacomics.presentation.ui.screens.home.ComicDetailScreen
import com.alfacomics.presentation.ui.screens.home.ComicReaderScreen
import com.alfacomics.presentation.ui.screens.home.EpisodePlayerScreen
import com.alfacomics.presentation.ui.screens.home.HomeScreen
import com.alfacomics.presentation.ui.screens.home.MotionComicDetailScreen
import com.alfacomics.presentation.ui.screens.premium.PremiumScreen
import com.alfacomics.presentation.ui.screens.profile.CoinPurchaseScreen
import com.alfacomics.presentation.ui.screens.profile.EditProfileScreen
import com.alfacomics.presentation.ui.screens.profile.LanguageSelectionScreen
import com.alfacomics.presentation.ui.screens.profile.ProfileScreen
import com.alfacomics.presentation.ui.screens.profile.SettingsScreen
import com.alfacomics.presentation.ui.screens.profile.ShareAndRewardScreen
import com.alfacomics.presentation.ui.screens.profile.SupportScreen
import com.alfacomics.presentation.ui.screens.profile.UploadComicScreen
import com.alfacomics.presentation.ui.screens.search.SearchScreen
import com.alfacomics.presentation.ui.screens.store.AlfaStoreScreen
import com.alfacomics.presentation.ui.screens.store.ComicPurchaseScreen
import com.alfacomics.presentation.ui.screens.store.OrderHistoryScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel,
    isLandscape: Boolean,
    onOrientationChange: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "login", // Start with login screen
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController = navController)

            // Check if user is already logged in
            LaunchedEffect(Unit) {
                if (DummyData.isLoggedIn) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
        composable("signup") {
            SignUpScreen(navController = navController)
        }
        composable("home") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
            HomeScreen(navController = navController)
        }
        composable("alfaStore") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("alfaStore") { inclusive = true }
                    }
                }
            }
            AlfaStoreScreen(navController = navController)
        }
        composable("community") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("community") { inclusive = true }
                    }
                }
            }
            CommunityScreen(navController = navController, viewModel = viewModel)
        }
        composable("favourite") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("favourite") { inclusive = true }
                    }
                }
            }
            FavouriteScreen(navController = navController)
        }
        composable("profile") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            }
            ProfileScreen(navController = navController)
        }
        composable(
            route = "comic_detail/{comicId}",
            arguments = listOf(navArgument("comicId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("comic_detail/{comicId}") { inclusive = true }
                    }
                }
            }
            val comicId = backStackEntry.arguments?.getInt("comicId") ?: 0
            ComicDetailScreen(
                navController = navController,
                comicId = comicId,
                onEpisodeClick = { episodeId ->
                    navController.navigate("comic_reader/$comicId/$episodeId")
                }
            )
        }
        composable(
            route = "motion_comic_detail/{motionComicId}",
            arguments = listOf(navArgument("motionComicId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("motion_comic_detail/{motionComicId}") { inclusive = true }
                    }
                }
            }
            val motionComicId = backStackEntry.arguments?.getInt("motionComicId") ?: 0
            MotionComicDetailScreen(
                navController = navController,
                motionComicId = motionComicId,
                isLandscape = isLandscape,
                onOrientationChange = onOrientationChange
            )
        }
        composable("premium") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("premium") { inclusive = true }
                    }
                }
            }
            PremiumScreen(navController = navController)
        }
        composable("search") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("search") { inclusive = true }
                    }
                }
            }
            SearchScreen(navController = navController)
        }
        composable("notifications") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("notifications") { inclusive = true }
                    }
                }
            }
            Text("Notifications Screen Placeholder")
        }
        composable(
            route = "comic_purchase/{comicId}",
            arguments = listOf(navArgument("comicId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("comic_purchase/{comicId}") { inclusive = true }
                    }
                }
            }
            val comicId = backStackEntry.arguments?.getInt("comicId") ?: 0
            ComicPurchaseScreen(navController = navController, comicId = comicId)
        }
        composable("order_history") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("order_history") { inclusive = true }
                    }
                }
            }
            OrderHistoryScreen(navController = navController)
        }
        composable(
            route = "comic_reader/{comicId}/{episodeId}",
            arguments = listOf(
                navArgument("comicId") { type = NavType.IntType },
                navArgument("episodeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("comic_reader/{comicId}/{episodeId}") { inclusive = true }
                    }
                }
            }
            val comicId = backStackEntry.arguments?.getInt("comicId") ?: 0
            val episodeId = backStackEntry.arguments?.getInt("episodeId") ?: 0
            ComicReaderScreen(
                comicId = comicId,
                episodeId = episodeId
            )
        }
        composable(
            route = "episode_player/{motionComicId}/{episodeId}",
            arguments = listOf(
                navArgument("motionComicId") { type = NavType.IntType },
                navArgument("episodeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("episode_player/{motionComicId}/{episodeId}") { inclusive = true }
                    }
                }
            }
            val motionComicId = backStackEntry.arguments?.getInt("motionComicId") ?: 0
            val episodeId = backStackEntry.arguments?.getInt("episodeId") ?: 0
            EpisodePlayerScreen(
                navController = navController,
                motionComicId = motionComicId,
                initialEpisodeId = episodeId,
                isLandscape = isLandscape,
                onOrientationChange = onOrientationChange
            )
        }
        // Profile Tab Routes
        composable("edit_profile") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("edit_profile") { inclusive = true }
                    }
                }
            }
            EditProfileScreen(navController = navController)
        }
        composable("settings") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("settings") { inclusive = true }
                    }
                }
            }
            SettingsScreen(navController = navController)
        }
        composable("language_selection") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("language_selection") { inclusive = true }
                    }
                }
            }
            LanguageSelectionScreen(navController = navController)
        }
        composable("support") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("support") { inclusive = true }
                    }
                }
            }
            SupportScreen(navController = navController)
        }
        composable("share_and_reward") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("share_and_reward") { inclusive = true }
                    }
                }
            }
            ShareAndRewardScreen(navController = navController)
        }
        composable("upload_comic") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("upload_comic") { inclusive = true }
                    }
                }
            }
            UploadComicScreen(navController = navController)
        }
        composable("coin_purchase") {
            // Redirect to login if not logged in
            LaunchedEffect(Unit) {
                if (!DummyData.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo("coin_purchase") { inclusive = true }
                    }
                }
            }
            CoinPurchaseScreen(navController = navController)
        }
    }
}