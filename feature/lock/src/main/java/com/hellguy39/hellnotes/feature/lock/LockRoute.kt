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
package com.hellguy39.hellnotes.feature.lock

import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.domain.repository.system.BiometricAuthenticator
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardKeys
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardSelection
import kotlinx.coroutines.launch

@Composable
fun LockRoute(
    activity: FragmentActivity,
    lockViewModel: LockViewModel = hiltViewModel(),
    biometricAuth: BiometricAuthenticator = lockViewModel.biometricAuth,
    onUnlock: () -> Unit = {},
) {
    TrackScreenView(screenName = "LockScreen")

    BackHandler { /* Block back gesture */ }

    val uiState by lockViewModel.uiState.collectAsStateWithLifecycle()

    val hapticFeedback = LocalHapticFeedback.current
    val view = LocalView.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.lockState) {
        if (uiState.lockState is LockState.Unlocked) {
            onUnlock()
        }
    }

    LaunchedEffect(key1 = uiState.securityState.isUseBiometricData) {
        if (uiState.securityState.isUseBiometricData) {
            lockViewModel.authByBiometric {
                biometricAuth.authenticate(activity)
            }
        }
    }

    if (uiState.errorMessage.isNotEmpty()) {
        LaunchedEffect(key1 = uiState.errorMessage, block = {
            scope.launch {
                snackbarHostState.showSnackbar(uiState.errorMessage)
            }
        })
    }

    LockScreen(
        uiState = uiState,
        numberKeyboardSelection =
            NumberKeyboardSelection(
                onClick = { key ->
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    lockViewModel.enterKey(key)
                },
                onLongClick = { key ->
                    if (key == NumberKeyboardKeys.KEY_BACKSPACE) {
                        lockViewModel.clearPassword()
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                },
            ),
        passwordSelection =
            PasswordSelection(
                onClear = lockViewModel::clearPassword,
                onEntered = lockViewModel::enterPassword,
                onValueChange = lockViewModel::enterValue,
            ),
        snackbarHostState = snackbarHostState,
        onBiometricsAuth = {
            lockViewModel.authByBiometric {
                biometricAuth.authenticate(activity)
            }
        },
    )
}
