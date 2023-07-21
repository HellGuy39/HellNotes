package com.hellguy39.hellnotes.feature.settings.detail.security

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.value.Motions
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.securityScreen(
    settingsViewModel: SettingsViewModel
) {
    composable(
        route = GraphScreen.Settings.Security.route,
        enterTransition = { Motions.slideEnter() },
        exitTransition = { Motions.fadeExit() },
        popEnterTransition = { Motions.slideEnter() },
        popExitTransition = { Motions.fadeExit() },
    ) {
        SecurityRoute(settingsViewModel = settingsViewModel)
    }
}