package com.hellguy39.hellnotes.feature.labeledit

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.components.snack.showDismissableSnackbar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.labeledit.components.LabelEditScreenContentSelection

@Composable
fun LabelEditRoute(
    navigateBack: () -> Unit,
    labelEditViewModel: LabelEditViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val uiState by labelEditViewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun Label.isLabelUnique(): Boolean {
        uiState.let { state ->
            return if (state is LabelEditUiState.Success) {
                state.labels.find { label -> label.name == this.name } == null
            } else {
                false
            }
        }
    }

    fun showLabelIsAlreadyExistSnack() {
        snackbarHostState.showDismissableSnackbar(
            scope = scope,
            message = context.getString(AppStrings.Snack.LabelAlreadyExist),
            duration = SnackbarDuration.Short,
        )
    }

    LabelEditScreen(
        onNavigationButtonClick = { navigateBack() },
        uiState = uiState,
        labelEditScreenContentSelection =
            LabelEditScreenContentSelection(
                onCreateLabel = { label ->
                    if (label.isLabelUnique()) {
                        labelEditViewModel.send(LabelEditScreenUiEvent.InsertLabel(label))
                        true
                    } else {
                        showLabelIsAlreadyExistSnack()
                        false
                    }
                },
                onLabelUpdated = { label ->
                    labelEditViewModel.send(LabelEditScreenUiEvent.UpdateLabel(label))
                },
                onDeleteLabel = { label ->
                    labelEditViewModel.send(LabelEditScreenUiEvent.DeleteLabel(label))
                },
            ),
        snackbarHostState = snackbarHostState,
    )
}
