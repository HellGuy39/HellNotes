package com.hellguy39.hellnotes.feature.settings.detail.theme

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel

fun NavGraphBuilder.appearanceScreen(
    settingsViewModel: SettingsViewModel
) {
    composable(
        route = GraphScreen.Settings.Appearance.route,
    ) {
        AppearanceRoute()
    }
}