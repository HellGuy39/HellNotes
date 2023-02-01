package com.hellguy39.hellnotes.feature.labels

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.labels.components.LabelItemSelection
import kotlinx.coroutines.launch

@Composable
fun LabelsRoute(
    navController: NavController,
    labelsViewModel: LabelsViewModel = hiltViewModel()
) {
    val labels by labelsViewModel.labels.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val labelAlreadyExist = stringResource(id = HellNotesStrings.Text.LabelAlreadyExist)

    LabelsScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        labels = labels,
        labelItemSelection = LabelItemSelection(
            onDeleteLabel = { label ->
                labelsViewModel.deleteLabel(label)
            },
            onLabelUpdated = { label ->
                labels.find { it.name == label.name }.let {
                    if (it != null) {
                        scope.launch {
                            snackbarHostState.showSnackbar(labelAlreadyExist)
                        }
                    } else {
                        labelsViewModel.updateLabel(label)
                    }
                }
            },
            onCreateLabel = { label ->
                labels.find { it.name == label.name }.let {
                    if (it != null) {
                        scope.launch {
                            snackbarHostState.showSnackbar(labelAlreadyExist)
                        }
                    } else {
                        labelsViewModel.insertLabel(label)
                    }
                }
            }
        ),
        snackbarHostState = snackbarHostState
    )
}