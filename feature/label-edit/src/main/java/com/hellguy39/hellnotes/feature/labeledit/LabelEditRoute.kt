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
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.snack.showDismissableSnackbar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@Composable
fun LabelEditRoute(
    navigateBack: () -> Unit,
    labelEditViewModel: LabelEditViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "LabelEditScreen")

    val context = LocalContext.current

    val uiState by labelEditViewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun showLabelIsAlreadyExistSnack() {
        snackbarHostState.showDismissableSnackbar(
            scope = scope,
            message = context.getString(AppStrings.Snack.LabelAlreadyExist),
            duration = SnackbarDuration.Short,
        )
    }

    val onNavigationButtonClick = remember { navigateBack }
    val onCreateLabel =
        remember {
            { name: String ->
                if (labelEditViewModel.isLabelUnique(name)) {
                    labelEditViewModel.send(LabelEditScreenUiEvent.InsertLabel(name))
                    true
                } else {
                    showLabelIsAlreadyExistSnack()
                    false
                }
            }
        }
    val onLabelUpdated =
        remember {
            { index: Int, name: String ->
                labelEditViewModel.send(LabelEditScreenUiEvent.UpdateLabel(index, name))
            }
        }
    val onDeleteLabel =
        remember {
            { index: Int ->
                labelEditViewModel.send(LabelEditScreenUiEvent.DeleteLabel(index))
            }
        }

    LabelEditScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        uiState = uiState,
        onCreateLabel = onCreateLabel,
        onLabelUpdated = onLabelUpdated,
        onDeleteLabel = onDeleteLabel,
        snackbarHostState = snackbarHostState,
    )
}
