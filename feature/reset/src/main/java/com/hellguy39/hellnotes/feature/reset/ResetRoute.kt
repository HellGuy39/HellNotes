package com.hellguy39.hellnotes.feature.reset

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ResetRoute(
    navController: NavController,
    resetViewModel: ResetViewModel = hiltViewModel()
) {
    BackHandler { navController.popBackStack() }

    ResetScreen(
        onNavigationButtonClick = navController::popBackStack,
        onReset = { resetDatabase, resetSettings ->
            resetViewModel.reset(resetDatabase, resetSettings)
            navController.popBackStack()
        }
    )
}