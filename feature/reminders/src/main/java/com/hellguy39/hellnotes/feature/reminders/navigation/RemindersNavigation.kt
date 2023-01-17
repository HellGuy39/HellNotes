package com.hellguy39.hellnotes.feature.reminders.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.feature.reminders.RemindersRoute

const val remindersNavigationRoute = "reminders_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.remindersScreen(
    navController: NavController
) {
    composable(
        route = remindersNavigationRoute,
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
        RemindersRoute(navController = navController)
    }
}
