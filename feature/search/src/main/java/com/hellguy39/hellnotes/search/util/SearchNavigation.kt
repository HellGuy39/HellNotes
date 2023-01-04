package com.hellguy39.hellnotes.search.util

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.navigations.INavigations
import com.hellguy39.hellnotes.search.ui.SearchRoute

const val searchNavigationRoute = "search_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchScreen(
    navController: NavController,
    navigations: INavigations
) {
    composable(
        route = searchNavigationRoute,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -300 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -300 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }
    ) {
        SearchRoute(navController, navigations)
    }
}