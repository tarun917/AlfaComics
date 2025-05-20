package com.alfacomics.presentation.ui.navigation

import android.annotation.SuppressLint
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
import com.alfacomics.presentation.ui.screens.home.ComicReaderScreen
import com.alfacomics.presentation.ui.screens.home.HomeScreen
import com.alfacomics.presentation.ui.screens.notification.NotificationScreen
import com.alfacomics.presentation.ui.screens.premium.PremiumScreen
import com.alfacomics.presentation.ui.screens.profile.ProfileScreen
import com.alfacomics.presentation.ui.screens.search.SearchScreen
import com.alfacomics.presentation.ui.screens.store.AlfaStoreScreen
import com.alfacomics.presentation.ui.screens.store.ComicPurchaseScreen
import com.alfacomics.presentation.ui.screens.store.OrderConfirmationScreen
import com.alfacomics.presentation.ui.screens.store.OrderHistoryScreen

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    communityViewModel: CommunityViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("community") {
            CommunityScreen(viewModel = communityViewModel)
        }
        composable("store") {
            AlfaStoreScreen(navController = navController)
        }
        composable("favourite") {
            FavouriteScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen()
        }
        composable("search") {
            SearchScreen(navController = navController)
        }
        composable("notifications") {
            NotificationScreen()
        }
        composable("premium") {
            PremiumScreen(navController = navController)
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
                    navController.navigate("comic_reader/$comicId/$episodeId")
                }
            )
        }
        composable(
            route = "comic_reader/{comicId}/{episodeId}",
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
        composable(
            route = "comic_purchase/{comicId}",
            arguments = listOf(navArgument("comicId") { type = NavType.IntType })
        ) { backStackEntry ->
            val comicId = backStackEntry.arguments?.getInt("comicId") ?: 0
            ComicPurchaseScreen(
                navController = navController,
                comicId = comicId
            )
        }
        composable("order_confirmation") {
            OrderConfirmationScreen(navController = navController)
        }
        composable("order_history") {
            OrderHistoryScreen(navController = navController)
        }
    }
}