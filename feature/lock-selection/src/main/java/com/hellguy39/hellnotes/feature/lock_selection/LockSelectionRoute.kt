package com.hellguy39.hellnotes.feature.lock_selection

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSetup
import kotlinx.coroutines.launch

@Composable
fun LockSelectionRoute(
    navController: NavController,
    lockSelectionViewModel: LockSelectionViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LockSelectionScreen(
        onNavigationBack = {
            navController.popBackStack()
        },
        onLockScreenTypeSelected = { type ->
            if (type == LockScreenType.None) {
                lockSelectionViewModel.resetAppLock()
                navController.popBackStack()
            } else {
                if(type != LockScreenType.Pin) {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            "Unfortunately this option is not available yet",
                            duration = SnackbarDuration.Short,
                            withDismissAction = true
                        )
                    }
                } else {
                    navController.navigateToLockSetup(lockType = type)
                }
            }
        },
        snackbarHostState = snackbarHostState
    )
}