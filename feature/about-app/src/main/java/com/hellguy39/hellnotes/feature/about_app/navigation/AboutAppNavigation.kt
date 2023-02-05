package com.hellguy39.hellnotes.feature.about_app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.about_app.AboutAppRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.aboutAppScreen(
    navController: NavController
) {
    composable(
        route = Screen.AboutApp.route,
        arguments = listOf(),
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        AboutAppRoute(navController = navController)
    }
}
