package com.hellguy39.hellnotes.feature.label_edit.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.label_edit.LabelEditRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.labelEditScreen(
    navController: NavController
) {
    composable(
        route = Screen.LabelEdit.route,
        arguments = listOf(),
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        LabelEditRoute(navController = navController)
    }
}
