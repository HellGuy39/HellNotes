package com.hellguy39.hellnotes.feature.about_app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.feature.about_app.AboutAppRoute

const val aboutAppNavigationRoute = "about_app_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.aboutAppScreen(
    navController: NavController
) {
    composable(
        route = aboutAppNavigationRoute,
        arguments = listOf(),
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
        }
    ) {
        AboutAppRoute(navController = navController)
    }
}
