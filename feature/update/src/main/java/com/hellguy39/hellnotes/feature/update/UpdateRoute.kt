package com.hellguy39.hellnotes.feature.update

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun UpdateRoute(
    navController: NavController,
    updateViewModel: UpdateViewModel = hiltViewModel()
) {
    BackHandler { navController.popBackStack() }

    val uiState by updateViewModel.uiState.collectAsStateWithLifecycle()

    UpdateScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        selection = UpdateScreenSelection(
            onDownload = {
                updateViewModel.send(UpdateUiEvent.DownloadUpdate)
            }
        )
    )
}