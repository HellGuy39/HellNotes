package com.hellguy39.hellnotes.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.feature.settings.components.SettingsScreenContent
import com.hellguy39.hellnotes.feature.settings.components.SettingsTopAppBar
import com.hellguy39.hellnotes.feature.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.feature.settings.events.SettingsEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigationButtonClick: () -> Unit,
    languageDialogEvents: LanguageDialogEvents,
    appSettings: AppSettings,
    settingsEvents: SettingsEvents,
    isBioAuthAvailable: Boolean
) {
    BackHandler(onBack = onNavigationButtonClick)

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            SettingsScreenContent(
                innerPadding = innerPadding,
                languageDialogEvents = languageDialogEvents,
                appSettings = appSettings,
                settingsEvents = settingsEvents,
                isBioAuthAvailable = isBioAuthAvailable
            )
        },
        topBar = {
            SettingsTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick
            )
        }
    )
}