package com.hellguy39.hellnotes.feature.labelselection

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun LabelSelectionRoute(
    navigateBack: () -> Unit,
    labelSelectionViewModel: LabelSelectionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "LabelSelectionScreen")

    BackHandler { navigateBack() }

    val uiState by labelSelectionViewModel.uiState.collectAsStateWithLifecycle()

    val onToggleLabelCheckbox =
        remember {
            { index: Int ->
                labelSelectionViewModel.sendEvent(LabelSelectionUiEvent.ToggleLabelCheckbox(index))
            }
        }

    val onSearchUpdate =
        remember {
            { search: String ->
                labelSelectionViewModel.sendEvent(LabelSelectionUiEvent.UpdateSearch(search))
            }
        }

    val onCreateNewLabelClick =
        remember {
            {
                labelSelectionViewModel.sendEvent(LabelSelectionUiEvent.CreateNewLabel)
            }
        }

    val onNavigationBack = remember { navigateBack }

    LabelSelectionScreen(
        onNavigationBack = onNavigationBack,
        uiState = uiState,
        onToggleLabelCheckbox = onToggleLabelCheckbox,
        onSearchUpdate = onSearchUpdate,
        onCreateNewLabelClick = onCreateNewLabelClick,
    )
}
