package com.hellguy39.hellnotes.feature.home.list.labels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LabelsRoute(
    labelsViewModel: LabelsViewModel = hiltViewModel()
) {

    val uiState by labelsViewModel.uiState.collectAsStateWithLifecycle()

    LabelsScreen(
        uiState = uiState,
        onLabelClick = { label ->

        },
    )

}