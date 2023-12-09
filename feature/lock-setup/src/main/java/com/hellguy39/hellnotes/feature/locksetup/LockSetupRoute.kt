package com.hellguy39.hellnotes.feature.locksetup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LockSetupRoute(
    lockSetupViewModel: LockSetupViewModel = hiltViewModel(),
    navigateBackToSettings: () -> Unit,
    navigateBack: () -> Unit,
) {
    val uiState by lockSetupViewModel.uiState.collectAsStateWithLifecycle()

    LockSetupScreen(
        uiState = uiState,
        onCodeReceived = { code ->
            lockSetupViewModel.saveAppCode(code)
            navigateBackToSettings()
        },
        onNavigationBack = navigateBack,
    )
}
