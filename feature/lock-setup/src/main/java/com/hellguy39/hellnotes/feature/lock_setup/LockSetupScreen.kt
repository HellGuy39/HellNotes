package com.hellguy39.hellnotes.feature.lock_setup

import androidx.compose.runtime.Composable
import com.hellguy39.hellnotes.core.model.util.LockScreenType

@Composable
fun LockSetupScreen(
    uiState: LockSetupUiState,
    onNavigationBack: () -> Unit,
    onCodeReceived: (String) -> Unit
) {
    when(uiState.newLockScreenType) {
        LockScreenType.None -> Unit
        LockScreenType.Pin -> {
            LockSetupPinScreen(
                onNavigationBack = onNavigationBack,
                onPinEntered = onCodeReceived
            )
        }
        LockScreenType.Password -> {
            LockSetupPasswordScreen(
                onNavigationBack = onNavigationBack,
                onPasswordEntered = onCodeReceived
            )
        }
        else -> Unit
    }
}