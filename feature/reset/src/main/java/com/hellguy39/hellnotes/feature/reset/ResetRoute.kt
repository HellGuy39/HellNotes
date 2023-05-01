package com.hellguy39.hellnotes.feature.reset

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun ResetRoute(
    navController: NavController,
    resetViewModel: ResetViewModel = hiltViewModel()
) {
    BackHandler(onBack = navController::popBackStack)

    ResetScreen(
        onNavigationButtonClick = navController::popBackStack,
        onReset = { resetDatabase, resetSettings ->
            resetViewModel.reset(resetDatabase, resetSettings)
            navController.popBackStack()
        }
    )
}