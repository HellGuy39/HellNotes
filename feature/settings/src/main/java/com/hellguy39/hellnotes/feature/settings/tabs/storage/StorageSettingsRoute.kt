package com.hellguy39.hellnotes.feature.settings.tabs.storage

import androidx.compose.runtime.Composable

@Composable
fun StorageSettingsRoute(
    navigateBack: () -> Unit,
) {
    StorageSettingsScreen(
        onNavigationButtonClick = { navigateBack() },
    )
}
