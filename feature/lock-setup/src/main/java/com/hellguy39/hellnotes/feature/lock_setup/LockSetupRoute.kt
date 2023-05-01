package com.hellguy39.hellnotes.feature.lock_setup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.navigations.Screen

@Composable
fun LockSetupRoute(
    navController: NavController,
    lockSetupViewModel: LockSetupViewModel = hiltViewModel()
) {
    val uiState by lockSetupViewModel.uiState.collectAsStateWithLifecycle()

    LockSetupScreen(
        uiState = uiState,
        onCodeReceived = { code ->
            lockSetupViewModel.saveAppCode(code)
            navController.popBackStack(route = Screen.Settings.route, inclusive = false)
        },
        onNavigationBack = navController::popBackStack
    )
}