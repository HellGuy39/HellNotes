package com.hellguy39.hellnotes.feature.label_selection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun LabelSelectionRoute(
    navController: NavController,
    labelSelectionViewModel: LabelSelectionViewModel = hiltViewModel()
) {
    val onNavigationBack: () -> Unit = {
        navController.popBackStack()
    }

    BackHandler(onBack = onNavigationBack)

    val uiState by labelSelectionViewModel.uiState.collectAsStateWithLifecycle()

    LabelSelectionScreen(
        onNavigationBack = onNavigationBack,
        uiState = uiState,
        selection = LabelSelectionScreenSelection(
            onLabelSelectedUpdate = { label, checked ->
                if(checked) {
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
            }
        )
    )
}