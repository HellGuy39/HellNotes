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
package com.hellguy39.hellnotes.core.ui.components.snack

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNSnackbarHost(state: SnackbarHostState) {
    SnackbarHost(hostState = state) { data ->

        val dismissState = rememberSnackbarDismissState(snackbarHostState = state)

        LaunchedEffect(dismissState.currentValue) {
            if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                dismissState.reset()
            }
        }

        HNDismissableSnackbar(
            dismissState = dismissState,
            snackbarData = data,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberSnackbarDismissState(snackbarHostState: SnackbarHostState)
    = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue != SwipeToDismissBoxValue.Settled) {
                snackbarHostState.currentSnackbarData?.dismiss()
                true
            } else {
                false
            }
        }
    )
