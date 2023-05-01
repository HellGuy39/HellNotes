package com.hellguy39.hellnotes.feature.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.ui.navigations.*
import com.hellguy39.hellnotes.feature.settings.components.SettingsScreenSelection

@Composable
fun SettingsRoute(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val currentOnStart by rememberUpdatedState {
        settingsViewModel.send(SettingsUiEvent.Start)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_START -> currentOnStart()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    SettingsScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        selection = SettingsScreenSelection(
            onLanguage = {
                navController.navigateToLanguageSelection()
            },
            onUseBiometric = { isUseBiometricData ->
                if (isUseBiometricData) {
                    if (settingsViewModel.biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success) {
                        settingsViewModel.biometricAuth.setOnAuthListener { result ->
                            if (result == AuthenticationResult.Success) {
                                settingsViewModel.send(SettingsUiEvent.ToggleIsUseBiometricData(isUseBiometricData))
                            }
                        }
                        settingsViewModel.biometricAuth.authenticate(context as AppCompatActivity)
                    }
                } else {
                    settingsViewModel.send(SettingsUiEvent.ToggleIsUseBiometricData(isUseBiometricData))
                }
            },
            onLockScreen = {
                navController.navigateToLockSelection()
            },
            onNoteStyleEdit = {
                navController.navigateToNoteStyleEdit()
            },
            onNoteSwipeEdit = {
                navController.navigateToNoteSwipeEdit()
            },
            onBackup = {
                navController.navigateToBackup()
            }
        )
    )
}