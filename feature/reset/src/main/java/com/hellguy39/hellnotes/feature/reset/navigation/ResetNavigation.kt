package com.hellguy39.hellnotes.feature.reset.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.reset.ResetRoute

fun NavGraphBuilder.resetScreen(
    navController: NavController
) {
    composable(
        route = Screen.Reset.route
    ) {
        ResetRoute(navController = navController)
    }
}
