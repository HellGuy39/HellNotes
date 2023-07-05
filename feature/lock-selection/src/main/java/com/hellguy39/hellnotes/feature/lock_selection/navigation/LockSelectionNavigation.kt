package com.hellguy39.hellnotes.feature.lock_selection.navigation

import androidx.compose.animation.*
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.lock_selection.LockSelectionRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.lockSelectionScreen(
    navController: NavController
) {
    composable(
        route = Screen.LockSelection.route,
    ) {
        LockSelectionRoute(navController = navController)
    }
}