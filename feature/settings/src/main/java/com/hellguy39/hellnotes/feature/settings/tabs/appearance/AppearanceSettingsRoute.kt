package com.hellguy39.hellnotes.feature.settings.tabs.appearance

import androidx.compose.runtime.Composable

@Composable
fun AppearanceSettingsRoute(
    navigateBack: () -> Unit,
) {
    AppearanceSettingsScreen(
        onNavigationButtonClick = {
            navigateBack()
        },
    )
}
