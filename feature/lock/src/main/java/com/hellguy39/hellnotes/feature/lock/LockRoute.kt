package com.hellguy39.hellnotes.feature.lock

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.ui.components.NumberKeyboardKeys
import com.hellguy39.hellnotes.core.ui.components.NumberKeyboardSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
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
    val scope = rememberCoroutineScope()

    if (uiState.lockState == LockState.Unlocked) {
        onUnlock()
    }

    val snackbarHostState = remember { SnackbarHostState() }

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
                if (key == NumberKeyboardKeys.KeyBio) {
                    lockViewModel.authByBiometric {
                        biometricAuth.authenticate(context as AppCompatActivity)
                    }
                } else {
                    lockViewModel.enterKey(key)
                }
            },
            onLongClick = { key ->
                if (key == NumberKeyboardKeys.KeyBackspace) {
                    lockViewModel.clearPin()
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }
        ),
        snackbarHostState = snackbarHostState
    )
}
