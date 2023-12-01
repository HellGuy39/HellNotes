package com.hellguy39.hellnotes.feature.update

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun UpdateRoute(
    updateViewModel: UpdateViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    BackHandler { navigateBack() }

    val uiState by updateViewModel.uiState.collectAsStateWithLifecycle()

    UpdateScreen(
        onNavigationButtonClick = { navigateBack() },
        uiState = uiState,
        selection = UpdateScreenSelection(
            onDownload = {
                updateViewModel.send(UpdateUiEvent.DownloadUpdate)
            }
        )
    )
}