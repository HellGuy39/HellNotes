/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.settings.screen.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.domain.repository.system.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.repository.system.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLanguageSelection: () -> Unit,
    navigateToLockSelection: () -> Unit,
    navigateToNoteStyleEdit: () -> Unit,
    navigateToNoteSwipeEdit: () -> Unit,
    navigateToBackup: () -> Unit,
    navigateToTheme: () -> Unit,
    navigateToColorMode: () -> Unit,
) {
    TrackScreenView(screenName = "SettingsScreen")

    val activity = LocalContext.current as FragmentActivity

    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
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
        onLockScreen = navigateToLockSelection,
        onNoteStyleEdit = navigateToNoteStyleEdit,
        onNoteSwipeEdit = navigateToNoteSwipeEdit,
        onTheme = navigateToTheme,
        onBackup = navigateToBackup,
        onColorMode = navigateToColorMode
    )
}
