package com.hellguy39.hellnotes.feature.update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun UpdateRoute(
    navController: NavController,
    updateViewModel: UpdateViewModel = hiltViewModel()
) {
    BackHandler(onBack = navController::popBackStack)

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