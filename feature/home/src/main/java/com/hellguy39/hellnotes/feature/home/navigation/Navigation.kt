package com.hellguy39.hellnotes.feature.home.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.INavigations
import com.hellguy39.hellnotes.feature.home.HomeRoute

const val homeNavigationRoute = "home_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(
    navController: NavController,
    navigations: INavigations,
    startFromReminders: Boolean = false
) {
    composable(
        route = homeNavigationRoute,
        exitTransition = {
//            slideOutHorizontally(
//                targetOffsetX = { -300 },
//                animationSpec = tween(300)
//            ) +
                    fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
//            slideInHorizontally(
//                initialOffsetX = { -300 },
//                animationSpec = tween(300)
//            ) +
                    fadeIn(animationSpec = tween(300))
        }
    ) {
        HomeRoute(
            navigations,
            startScreenIndex = if (startFromReminders) 1 else 0
        )
    }
}