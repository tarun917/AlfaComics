package com.alfacomics.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
import com.alfacomics.presentation.ui.screens.notification.NotificationScreen
import com.alfacomics.presentation.ui.screens.payment.CoinPurchasePaymentScreen
import com.alfacomics.presentation.ui.screens.payment.PaymentOptionsScreen
import com.alfacomics.presentation.ui.screens.premium.PremiumScreen
import com.alfacomics.presentation.ui.screens.profile.CoinPurchaseScreen
import com.alfacomics.presentation.ui.screens.profile.EditProfileScreen
import com.alfacomics.presentation.ui.screens.profile.FollowScreen
import com.alfacomics.presentation.ui.screens.profile.LanguageSelectionScreen
import com.alfacomics.presentation.ui.screens.profile.ProfileScreen
import com.alfacomics.presentation.ui.screens.profile.SettingsScreen
import com.alfacomics.presentation.ui.screens.profile.ShareAndRewardScreen
import com.alfacomics.presentation.ui.screens.profile.SupportScreen
import com.alfacomics.presentation.ui.screens.profile.UploadComicScreen
import com.alfacomics.presentation.ui.screens.profile.UserPostsScreen
import com.alfacomics.presentation.ui.screens.profile.UserProfileScreen
import com.alfacomics.presentation.ui.screens.search.SearchScreen
import com.alfacomics.presentation.ui.screens.store.AlfaStoreScreen
import com.alfacomics.presentation.ui.screens.store.ComicPurchaseScreen
import com.alfacomics.presentation.ui.screens.store.OrderHistoryScreen
import com.alfacomics.presentation.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    communityViewModel: CommunityViewModel,
    authViewModel: AuthViewModel, // Re-added parameter
    isLandscape: Boolean,
    onOrientationChange: (Boolean) -> Unit
) {
    // Use AuthViewModel's isLoggedIn state for initial navigation
    LaunchedEffect(authViewModel.isLoggedIn) {
        if (authViewModel.isLoggedIn) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (authViewModel.isLoggedIn) "home" else "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("signup") {
            SignUpScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("alfaStore") {
            AlfaStoreScreen(navController = navController)
        }
        composable("community") {
            CommunityScreen(navController = navController, viewModel = communityViewModel)
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
                    navController.navigate("comic_reader/$comicId/$episodeId")
                }
            )
        }
        composable(
            route = "motion_comic_detail/{motionComicId}",
            arguments = listOf(navArgument("motionComicId") { type = NavType.IntType })
        ) { backStackEntry ->
            val motionComicId = backStackEntry.arguments?.getInt("motionComicId") ?: 0
            MotionComicDetailScreen(
                navController = navController,
                motionComicId = motionComicId,
                isLandscape = isLandscape,
                onOrientationChange = onOrientationChange
            )
        }
        composable("premium") {
            PremiumScreen(navController = navController)
        }
        composable(
            route = "payment/{planDuration}/{price}",
            arguments = listOf(
                navArgument("planDuration") { type = NavType.StringType },
                navArgument("price") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val planDuration = backStackEntry.arguments?.getString("planDuration") ?: ""
            val price = backStackEntry.arguments?.getString("price") ?: ""
            PaymentOptionsScreen(
                navController = navController,
                planDuration = planDuration,
                price = price,
                onPaymentSuccess = {
                    // Handle subscription logic
                },
                onPaymentFailed = {
                    // Handle payment failure
                }
            )
        }
        composable(
            route = "coin_payment/{coins}/{price}",
            arguments = listOf(
                navArgument("coins") { type = NavType.IntType },
                navArgument("price") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val coins = backStackEntry.arguments?.getInt("coins") ?: 0
            val price = backStackEntry.arguments?.getInt("price") ?: 0
            CoinPurchasePaymentScreen(
                navController = navController,
                coins = coins,
                price = price,
                onPaymentSuccess = {
                    // Handle coin purchase logic
                },
                onPaymentFailed = {
                    // Handle payment failure
                }
            )
        }
        composable("search") {
            SearchScreen(navController = navController)
        }
        composable("notifications") {
            NotificationScreen(navController = navController)
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
            route = "episode_player/{motionComicId}/{episodeId}",
            arguments = listOf(
                navArgument("motionComicId") { type = NavType.IntType },
                navArgument("episodeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
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
        composable("edit_profile") {
            EditProfileScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
        composable("language_selection") {
            LanguageSelectionScreen(navController = navController)
        }
        composable("support") {
            SupportScreen(navController = navController)
        }
        composable("share_and_reward") {
            ShareAndRewardScreen(navController = navController)
        }
        composable("upload_comic") {
            UploadComicScreen(navController = navController)
        }
        composable("coin_purchase") {
            CoinPurchaseScreen(navController = navController)
        }
        composable("follow_screen") {
            FollowScreen(navController = navController)
        }
        composable("user_posts") {
            UserPostsScreen(navController = navController)
        }
        composable(
            route = "user_profile/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserProfileScreen(navController = navController, username = username)
        }
    }
}
