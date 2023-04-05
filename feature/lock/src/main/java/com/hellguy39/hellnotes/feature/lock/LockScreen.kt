package com.hellguy39.hellnotes.feature.lock

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardSelection
import kotlinx.coroutines.delay

@Composable
fun LockScreen(
    uiState: LockUiState,
    numberKeyboardSelection: NumberKeyboardSelection,
    passwordSelection: PasswordSelection,
    snackbarHostState: SnackbarHostState,
    onBiometricsAuth: () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { paddingValues ->
            when(uiState.securityState.lockType) {
                LockScreenType.Pin -> {
                    LockScreenPin(
                        paddingValues = paddingValues,
                        uiState = uiState,
                        numberKeyboardSelection = numberKeyboardSelection,
                        onBiometricsAuth = onBiometricsAuth
                    )
                }
                LockScreenType.Password -> {
                    LockScreenPassword(
                        paddingValues = paddingValues,
                        uiState = uiState,
                        onBiometricsAuth = onBiometricsAuth,
                        passwordSelection = passwordSelection,
                    )
                }
                else -> Unit
            }
        }
    )
}