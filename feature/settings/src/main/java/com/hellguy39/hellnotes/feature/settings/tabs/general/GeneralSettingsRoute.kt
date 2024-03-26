package com.hellguy39.hellnotes.feature.settings.tabs.general

import androidx.compose.runtime.Composable

@Composable
fun GeneralSettingsRoute(
    navigateBack: () -> Unit,
) {
    GeneralSettingsScreen(
        onNavigationButtonClick = {
            navigateBack()
        },
    )
}
