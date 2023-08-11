package com.hellguy39.hellnotes.feature.changelog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.changelog.ChangelogRoute

fun NavGraphBuilder.changelogScreen(
    navController: NavController
) {
    composable(
        route = Screen.Changelog.route,
    ) {
        ChangelogRoute(navController = navController)
    }
}
