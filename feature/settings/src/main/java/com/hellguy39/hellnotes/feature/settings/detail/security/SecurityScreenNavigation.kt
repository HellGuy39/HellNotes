package com.hellguy39.hellnotes.feature.settings.detail.security

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel

fun NavGraphBuilder.securityScreen(
    settingsViewModel: SettingsViewModel
) {
    composable(
        route = GraphScreen.Settings.Security.route,
    ) {
        SecurityRoute(settingsViewModel = settingsViewModel)
    }
}