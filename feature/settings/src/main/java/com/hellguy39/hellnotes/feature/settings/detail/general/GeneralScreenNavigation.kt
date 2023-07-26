package com.hellguy39.hellnotes.feature.settings.detail.general

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel

fun NavGraphBuilder.generalScreen(
    settingsViewModel: SettingsViewModel
) {
    composable(
        route = GraphScreen.Settings.General.route,
    ) {
        GeneralRoute(settingsViewModel)
    }
}