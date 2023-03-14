package com.hellguy39.hellnotes.feature.lock

import android.content.Context
import android.view.HapticFeedbackConstants
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardKeys
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardSelection
import kotlinx.coroutines.launch

@Composable
fun LockRoute(
    lockViewModel: LockViewModel = hiltViewModel(),
    biometricAuth: BiometricAuthenticator = lockViewModel.biometricAuth,
    onUnlock: () -> Unit = {},
    context: Context = LocalContext.current
) {
    val uiState by lockViewModel.uiState.collectAsStateWithLifecycle()
    val errorMessage by lockViewModel.errorMessage.collectAsStateWithLifecycle()

    val hapticFeedback = LocalHapticFeedback.current
    val view = LocalView.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.lockState) {
        if (uiState.lockState == LockState.Unlocked) {
            onUnlock()
        }
    }

    LaunchedEffect(key1 = uiState.isBiometricsAllowed) {
        if (uiState.isBiometricsAllowed) {
            lockViewModel.authByBiometric {
                biometricAuth.authenticate(context as AppCompatActivity)
            }
        }
    }

    if (errorMessage.isNotEmpty()) {
        LaunchedEffect(key1 = errorMessage, block = {
            scope.launch {
                snackbarHostState.showSnackbar(errorMessage)
            }
        })
    }

    LockScreen(
        uiState = uiState,
        numberKeyboardSelection = NumberKeyboardSelection(
            onClick = { key ->
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                lockViewModel.enterKey(key)
            },
            onLongClick = { key ->
                if (key == NumberKeyboardKeys.KeyBackspace) {
                    lockViewModel.clearPin()
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }
        ),
        snackbarHostState = snackbarHostState,
        onBiometricsAuth = {
            lockViewModel.authByBiometric {
                biometricAuth.authenticate(context as AppCompatActivity)
            }
        }
    )
}
