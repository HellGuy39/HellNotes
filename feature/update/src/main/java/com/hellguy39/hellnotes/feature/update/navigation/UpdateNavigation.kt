package com.hellguy39.hellnotes.feature.update.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.update.UpdateRoute

fun NavGraphBuilder.updateScreen(
    navController: NavController
) {
    composable(
        route = Screen.Update.route,
    ) {
        UpdateRoute(navController = navController)
    }
}
