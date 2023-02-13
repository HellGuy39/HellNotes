package com.hellguy39.hellnotes.feature.home.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.home.HomeRoute
import com.hellguy39.hellnotes.feature.home.util.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(
    navController: NavController,
    startScreen: HomeScreen,
) {
    composable(
        route = Screen.Home.route,
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        }
    ) {
        HomeRoute(
            navController = navController,
            startScreen = startScreen
        )
    }
}