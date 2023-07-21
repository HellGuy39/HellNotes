package com.hellguy39.hellnotes.feature.settings.detail.theme

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.value.Motions
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.themeScreen(
    settingsViewModel: SettingsViewModel
) {
    composable(
        route = GraphScreen.Settings.Theme.route,
        enterTransition = { Motions.fadeEnter() },
        exitTransition = { Motions.fadeExit() },
        popEnterTransition = { Motions.fadePopEnter() },
        popExitTransition = { Motions.fadePopExit() },
    ) {

    }
}