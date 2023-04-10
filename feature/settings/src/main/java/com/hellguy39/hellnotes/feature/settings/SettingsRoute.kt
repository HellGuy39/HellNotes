package com.hellguy39.hellnotes.feature.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.domain.system_features.ProofOfIdentity
import com.hellguy39.hellnotes.core.model.util.LockRequest
import com.hellguy39.hellnotes.core.model.util.LockResult
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.GetResultOnce
import com.hellguy39.hellnotes.core.ui.ResultKey
import com.hellguy39.hellnotes.core.ui.navigations.*
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
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        selection = SettingsScreenSelection(
            onLanguage = {
                navController.navigateToLanguageSelection()
            },
            onUseBiometric = { isUseBiometricData ->
                if (isUseBiometricData) {
                    if (settingsViewModel.biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success) {
                        settingsViewModel.biometricAuth.setOnAuthListener {
                            if (it == AuthenticationResult.Success) {
                                settingsViewModel.saveIsUseBiometricData(isUseBiometricData)
                            }
                        }
                        settingsViewModel.biometricAuth.authenticate(context as AppCompatActivity)
                    }
                } else {
                    settingsViewModel.saveIsUseBiometricData(isUseBiometricData)
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
            }
        )
    )
}