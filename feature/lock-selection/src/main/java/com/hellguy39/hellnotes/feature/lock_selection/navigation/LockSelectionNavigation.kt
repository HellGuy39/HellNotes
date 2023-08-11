package com.hellguy39.hellnotes.feature.lock_selection.navigation

import androidx.compose.animation.*
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.lock_selection.LockSelectionRoute

fun NavGraphBuilder.lockSelectionScreen(
    navController: NavController
) {
    composable(
        route = Screen.LockSelection.route,
    ) {
        LockSelectionRoute(navController = navController)
    }
}