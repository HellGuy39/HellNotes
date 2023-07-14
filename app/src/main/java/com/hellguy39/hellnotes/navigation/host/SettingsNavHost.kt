package com.hellguy39.hellnotes.navigation.host

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hellguy39.hellnotes.activity.main.settingsViewModel
import com.hellguy39.hellnotes.feature.settings.SettingsRoute
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel

@Composable
fun SettingsNavHost(
    globalNavController: NavController,
    settingsViewModel: SettingsViewModel = settingsViewModel(navController = globalNavController)
) {

    SettingsRoute()
}