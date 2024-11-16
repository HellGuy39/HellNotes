/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
