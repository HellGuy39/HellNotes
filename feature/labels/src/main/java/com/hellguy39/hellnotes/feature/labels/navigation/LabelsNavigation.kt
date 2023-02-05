package com.hellguy39.hellnotes.feature.labels.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.labels.LabelsRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.labelsScreen(
    navController: NavController
) {
    composable(
        route = Screen.Labels.route,
        arguments = listOf(),
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        LabelsRoute(navController = navController)
    }
}
