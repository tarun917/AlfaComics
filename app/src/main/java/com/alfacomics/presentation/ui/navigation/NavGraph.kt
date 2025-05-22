package com.alfacomics.presentation.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alfacomics.presentation.ui.screens.community.CommunityScreen
import com.alfacomics.presentation.ui.screens.community.CommunityViewModel
import com.alfacomics.presentation.ui.screens.favourite.FavouriteScreen
import com.alfacomics.presentation.ui.screens.home.ComicDetailScreen
import com.alfacomics.presentation.ui.screens.home.ComicReaderScreen // Added import for ComicReaderScreen
import com.alfacomics.presentation.ui.screens.home.HomeScreen
import com.alfacomics.presentation.ui.screens.home.MotionComicDetailScreen
import com.alfacomics.presentation.ui.screens.premium.PremiumScreen
import com.alfacomics.presentation.ui.screens.profile.ProfileScreen
import com.alfacomics.presentation.ui.screens.search.SearchScreen
import com.alfacomics.presentation.ui.screens.store.AlfaStoreScreen
import com.alfacomics.presentation.ui.screens.store.ComicPurchaseScreen
import com.alfacomics.presentation.ui.screens.store.OrderHistoryScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("alfaStore") {
            AlfaStoreScreen(navController = navController)
        }
        composable("community") {
            CommunityScreen(navController = navController, viewModel = viewModel)
        }
        composable("favourite") {
            FavouriteScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable(
            route = "comic_detail/{comicId}",
            arguments = listOf(navArgument("comicId") { type = NavType.IntType })
        ) { backStackEntry ->
            val comicId = backStackEntry.arguments?.getInt("comicId") ?: 0
            ComicDetailScreen(
                navController = navController,
                comicId = comicId,
                onEpisodeClick = { episodeId ->
                    // Navigate to ComicReaderScreen instead of printing a message
                    navController.navigate("comic_reader/$comicId/$episodeId")
                }
            )
        }
        composable(
            route = "motion_comic_detail/{motionComicId}",
            arguments = listOf(navArgument("motionComicId") { type = NavType.IntType })
        ) { backStackEntry ->
            val motionComicId = backStackEntry.arguments?.getInt("motionComicId") ?: 0
            MotionComicDetailScreen(navController = navController, motionComicId = motionComicId)
        }
        composable("premium") {
            PremiumScreen(navController = navController)
        }
        composable("search") {
            SearchScreen(navController = navController)
        }
        composable("notifications") {
            Text("Notifications Screen Placeholder")
        }
        composable(
            route = "comic_purchase/{comicId}",
            arguments = listOf(navArgument("comicId") { type = NavType.IntType })
        ) { backStackEntry ->
            val comicId = backStackEntry.arguments?.getInt("comicId") ?: 0
            ComicPurchaseScreen(navController = navController, comicId = comicId)
        }
        composable("order_history") {
            OrderHistoryScreen(navController = navController)
        }
        composable(
            route = "comic_reader/{comicId}/{episodeId}", // New route for ComicReaderScreen
            arguments = listOf(
                navArgument("comicId") { type = NavType.IntType },
                navArgument("episodeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val comicId = backStackEntry.arguments?.getInt("comicId") ?: 0
            val episodeId = backStackEntry.arguments?.getInt("episodeId") ?: 0
            ComicReaderScreen(
                comicId = comicId,
                episodeId = episodeId
            )
        }
    }
}