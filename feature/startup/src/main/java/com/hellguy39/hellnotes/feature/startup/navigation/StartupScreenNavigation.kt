package com.hellguy39.hellnotes.feature.startup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.startup.StartupRoute

fun NavGraphBuilder.startupScreen(
    navController: NavController
) {
    composable(
        route = Screen.Startup.route,
    ) {
        StartupRoute(navController)
    }
}