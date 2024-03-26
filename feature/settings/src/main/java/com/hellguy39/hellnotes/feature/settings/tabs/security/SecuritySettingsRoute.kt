package com.hellguy39.hellnotes.feature.settings.tabs.security

import androidx.compose.runtime.Composable

@Composable
fun SecuritySettingsRoute(
    navigateBack: () -> Unit,
) {
    SecuritySettingsScreen(
        onNavigationButtonClick = {
            navigateBack()
        },
    )
}
