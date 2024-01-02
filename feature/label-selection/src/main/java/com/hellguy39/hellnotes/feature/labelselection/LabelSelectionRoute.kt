package com.hellguy39.hellnotes.feature.labelselection

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    LabelSelectionScreen(
        onNavigationBack = { navigateBack() },
        uiState = uiState,
        selection =
            LabelSelectionScreenSelection(
                onLabelSelectedUpdate = { label, checked ->
                    if (checked) {
                        labelSelectionViewModel.sendEvent(LabelSelectionUiEvent.SelectLabel(label))
                    } else {
                        labelSelectionViewModel.sendEvent(LabelSelectionUiEvent.UnselectLabel(label))
                    }
                },
                onSearchUpdate = { search ->
                    labelSelectionViewModel.sendEvent(LabelSelectionUiEvent.UpdateSearch(search))
                },
                onCreateNewLabel = {
                    labelSelectionViewModel.sendEvent(LabelSelectionUiEvent.CreateNewLabel)
                },
            ),
    )
}
