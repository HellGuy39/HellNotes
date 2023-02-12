package com.hellguy39.hellnotes.feature.lock_selection

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSetup

@Composable
fun LockSelectionRoute(
    navController: NavController,
    lockSelectionViewModel: LockSelectionViewModel = hiltViewModel()
) {
    LockSelectionScreen(
        onNavigationBack = {
            navController.popBackStack()
        },
        onLockScreenTypeSelected = { type ->
            if (type == LockScreenType.None) {
                lockSelectionViewModel.resetAppLock()
                navController.popBackStack()
            } else {
                navController.navigateToLockSetup(lockType = type)
            }
        }
    )
}