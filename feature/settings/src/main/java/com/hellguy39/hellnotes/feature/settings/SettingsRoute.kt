package com.hellguy39.hellnotes.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.domain.system_features.ProofOfIdentity
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLanguageSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteStyleEdit
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteSwipeEdit
import com.hellguy39.hellnotes.feature.settings.components.SettingsScreenSelection

@Composable
fun SettingsRoute(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        settingsViewModel.updateLanguageCode()
    }

    SettingsScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        uiState = uiState,
        selection = SettingsScreenSelection(
            onLanguage = {
                navController.navigateToLanguageSelection()
            },
            onUseBiometric = { isUseBiometricData ->
                if (isUseBiometricData) {
                    (context as ProofOfIdentity).confirmBiometrics {
                        settingsViewModel.saveIsUseBiometricData(isUseBiometricData)
                    }
                } else {
                    settingsViewModel.saveIsUseBiometricData(isUseBiometricData)
                }
            },
            onLockScreen = {
                if (uiState.appSettings.appLockType != LockScreenType.None) {
                    (context as ProofOfIdentity).confirmAppAccess(cancelable = true) {
                        navController.navigateToLockSelection()
                    }
                } else {
                    navController.navigateToLockSelection()
                }
            },
            onNoteStyleEdit = {
                navController.navigateToNoteStyleEdit()
            },
            onNoteSwipeEdit = {
                navController.navigateToNoteSwipeEdit()
            }
        )
    )
}