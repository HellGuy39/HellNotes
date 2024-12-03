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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarController
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarEvent
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import kotlinx.coroutines.launch

@Composable
fun LabelEditRoute(
    navigateBack: () -> Unit,
    labelEditViewModel: LabelEditViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "LabelEditScreen")

    val uiState by labelEditViewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    fun showLabelIsAlreadyExistSnack() {
        scope.launch {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    text = UiText.StringResources(AppStrings.Snack.LabelAlreadyExist),
                )
            )
        }
    }

    val onNavigationButtonClick = remember { navigateBack }

    LabelEditScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        uiState = uiState,
        onUiEvent = { event -> labelEditViewModel.send(event) },
        onCreateLabel = { name: String ->
            if (labelEditViewModel.isLabelUnique(name)) {
                labelEditViewModel.send(LabelEditScreenUiEvent.InsertLabel(name))
                true
            } else {
                showLabelIsAlreadyExistSnack()
                false
            }
        }
    )
}
