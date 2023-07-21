package com.hellguy39.hellnotes.feature.startup.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.startup.StartupRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.startupScreen(
    navController: NavController
) {
    composable(
        route = Screen.Startup.route,
    ) {
        StartupRoute(navController)
    }
}