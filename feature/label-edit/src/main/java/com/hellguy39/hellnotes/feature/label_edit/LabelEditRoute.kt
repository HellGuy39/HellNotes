package com.hellguy39.hellnotes.feature.label_edit

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
import com.hellguy39.hellnotes.feature.label_edit.components.LabelItemSelection
import kotlinx.coroutines.launch

@Composable
fun LabelEditRoute(
    navController: NavController,
    labelEditViewModel: LabelEditViewModel = hiltViewModel()
) {
    val uiState by labelEditViewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val labelAlreadyExist = stringResource(id = HellNotesStrings.Text.LabelAlreadyExist)

    LabelEditScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        uiState = uiState,
        labelItemSelection = LabelItemSelection(
            onDeleteLabel = { label ->
                labelEditViewModel.deleteLabel(label)
            },
            onLabelUpdated = { label ->
                uiState.labels.find { it.name == label.name }.let {
                    if (it != null) {
                        scope.launch {
                            snackbarHostState.showSnackbar(labelAlreadyExist)
                        }
                    } else {
                        labelEditViewModel.updateLabel(label)
                    }
                }
            },
            onCreateLabel = { label ->
                uiState.labels.find { it.name == label.name }.let {
                    if (it != null) {
                        scope.launch {
                            snackbarHostState.showSnackbar(labelAlreadyExist)
                        }
                    } else {
                        labelEditViewModel.insertLabel(label)
                    }
                }
            }
        ),
        snackbarHostState = snackbarHostState
    )
}