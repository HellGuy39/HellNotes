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
package com.hellguy39.hellnotes.feature.lockselection

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun LockSelectionRoute(
    lockSelectionViewModel: LockSelectionViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLockSetup: (type: LockScreenType) -> Unit,
) {
    TrackScreenView(screenName = "LockSelectionScreen")

    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by lockSelectionViewModel.uiState.collectAsStateWithLifecycle()

    LockSelectionScreen(
        onNavigationBack = navigateBack,
        uiState = uiState,
        onLockScreenTypeSelected = { type ->
            when (type) {
                LockScreenType.None -> {
                    lockSelectionViewModel.resetAppLock()
                    navigateBack()
                }
                LockScreenType.Pin -> {
                    navigateToLockSetup(type)
                }
                LockScreenType.Password -> {
                    navigateToLockSetup(type)
                }
                else -> Unit
            }
        },
        snackbarHostState = snackbarHostState,
    )
}
