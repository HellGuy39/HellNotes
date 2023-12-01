package com.hellguy39.hellnotes.feature.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.feature.settings.components.SettingsScreenSelection

@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLanguageSelection: () -> Unit,
    navigateToLockSelection: () -> Unit,
    navigateToNoteStyleEdit: () -> Unit,
    navigateToNoteSwipeEdit: () -> Unit,
    navigateToBackup: () -> Unit
) {
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    val activity = LocalContext.current as AppCompatActivity

    SettingsScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        selection = SettingsScreenSelection(
            onLanguage = {
                navigateToLanguageSelection()
            },
            onUseBiometric = { isUseBiometricData ->
                if (isUseBiometricData) {
                    if (settingsViewModel.biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success) {
                        settingsViewModel.biometricAuth.setOnAuthListener { result ->
                            if (result == AuthenticationResult.Success) {
                                settingsViewModel.send(SettingsUiEvent.ToggleIsUseBiometricData(isUseBiometricData))
                            }
                        }
                        settingsViewModel.biometricAuth.authenticate(activity)
                    }
                } else {
                    settingsViewModel.send(SettingsUiEvent.ToggleIsUseBiometricData(isUseBiometricData))
                }
            },
            onLockScreen = {
                navigateToLockSelection()
            },
            onNoteStyleEdit = {
                navigateToNoteStyleEdit()
            },
            onNoteSwipeEdit = {
                navigateToNoteSwipeEdit()
            },
            onBackup = {
                navigateToBackup()
            }
        )
    )
}