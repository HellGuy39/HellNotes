package com.hellguy39.hellnotes.feature.labels.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.feature.labels.LabelsRoute

const val labelsNavigationRoute = "labels_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.labelsScreen(
    navController: NavController
) {
    composable(
        route = labelsNavigationRoute,
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
