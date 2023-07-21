package com.hellguy39.hellnotes.feature.changelog.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.changelog.ChangelogRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.changelogScreen(
    navController: NavController
) {
    composable(
        route = Screen.Changelog.route,
    ) {
        ChangelogRoute(navController = navController)
    }
}
