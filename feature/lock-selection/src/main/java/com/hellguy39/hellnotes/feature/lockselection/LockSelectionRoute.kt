package com.hellguy39.hellnotes.feature.lockselection

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.LockScreenType

@Composable
fun LockSelectionRoute(
    lockSelectionViewModel: LockSelectionViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLockSetup: (type: LockScreenType) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by lockSelectionViewModel.uiState.collectAsStateWithLifecycle()

    LockSelectionScreen(
        onNavigationBack = navigateBack,
        uiState = uiState,
        onLockScreenTypeSelected = { type ->
            when (type) {
                LockScreenType.None -> {
                    lockSelectionViewModel.resetAppLock()
                    navigateBack()
                }
                LockScreenType.Pin -> {
                    navigateToLockSetup(type)
                }
                LockScreenType.Password -> {
                    navigateToLockSetup(type)
                }
                else -> Unit
            }
        },
        snackbarHostState = snackbarHostState,
    )
}
