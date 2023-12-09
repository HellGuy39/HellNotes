package com.hellguy39.hellnotes.feature.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.domain.tools.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.tools.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.ui.lifecycle.rememberLifecycleEvent

@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLanguageSelection: () -> Unit,
    navigateToLockSelection: () -> Unit,
    navigateToNoteStyleEdit: () -> Unit,
    navigateToNoteSwipeEdit: () -> Unit,
    navigateToBackup: () -> Unit,
) {
    val activity = LocalContext.current as AppCompatActivity

    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleEvent = rememberLifecycleEvent()

    LaunchedEffect(key1 = lifecycleEvent) {
        when (lifecycleEvent?.targetState) {
            Lifecycle.State.STARTED -> {
                settingsViewModel.send(SettingsUiEvent.FetchLanguage)
            }
            else -> Unit
        }
    }

    SettingsScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        selection =
            SettingsScreenSelection(
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
                },
            ),
    )
}
