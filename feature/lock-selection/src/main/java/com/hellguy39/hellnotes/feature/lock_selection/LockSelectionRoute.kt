package com.hellguy39.hellnotes.feature.lock_selection

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSetup

@Composable
fun LockSelectionRoute(
    navController: NavController,
    lockSelectionViewModel: LockSelectionViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by lockSelectionViewModel.uiState.collectAsStateWithLifecycle()

    LockSelectionScreen(
        onNavigationBack = navController::popBackStack,
        uiState = uiState,
        onLockScreenTypeSelected = { type ->
            when(type) {
                LockScreenType.None -> {
                    lockSelectionViewModel.resetAppLock()
                    navController.popBackStack()
                }
                LockScreenType.Pin -> {
                    navController.navigateToLockSetup(lockType = type)
                }
                LockScreenType.Password -> {
                    navController.navigateToLockSetup(lockType = type)
                }
                else -> Unit
            }
        },
        snackbarHostState = snackbarHostState
    )
}