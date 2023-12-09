package com.hellguy39.hellnotes.feature.locksetup

import androidx.compose.runtime.Composable
import com.hellguy39.hellnotes.core.model.LockScreenType

@Composable
fun LockSetupScreen(
    uiState: LockSetupUiState,
    onNavigationBack: () -> Unit,
    onCodeReceived: (String) -> Unit,
) {
    when (uiState.newLockScreenType) {
        LockScreenType.None -> Unit
        LockScreenType.Pin -> {
            LockSetupPinScreen(
                onNavigationBack = onNavigationBack,
                onPinEntered = onCodeReceived,
            )
        }
        LockScreenType.Password -> {
            LockSetupPasswordScreen(
                onNavigationBack = onNavigationBack,
                onPasswordEntered = onCodeReceived,
            )
        }
        else -> Unit
    }
}
