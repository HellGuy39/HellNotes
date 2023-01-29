package com.hellguy39.hellnotes.feature.labels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.feature.labels.components.LabelItemSelection

@Composable
fun LabelsRoute(
    navController: NavController,
    labelsViewModel: LabelsViewModel = hiltViewModel()
) {
    val labels by labelsViewModel.labels.collectAsStateWithLifecycle()

    LabelsScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        labels = labels,
        labelItemSelection = LabelItemSelection(
            onDeleteLabel = { label ->
                labelsViewModel.deleteLabel(label)
            },
            onLabelUpdated = { label ->
                labelsViewModel.updateLabel(label)
            },
            onCreateLabel = { label ->
                labelsViewModel.insertLabel(label)
            }
        )
    )
}