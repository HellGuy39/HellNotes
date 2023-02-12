package com.hellguy39.hellnotes.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLanguageSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSelection
import com.hellguy39.hellnotes.feature.settings.components.SettingsScreenSelection

@Composable
fun SettingsRoute(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        uiState = uiState,
        selection = SettingsScreenSelection(
            onLanguage = {
                navController.navigateToLanguageSelection()
            },
            onUseBiometric = { isUseBiometricData ->
                settingsViewModel.saveIsUseBiometricData(isUseBiometricData)
            },
            onLockScreen = {
                navController.navigateToLockSelection()
            }
        )
    )
}