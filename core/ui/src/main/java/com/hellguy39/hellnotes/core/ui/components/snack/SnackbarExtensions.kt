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

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun SnackbarHostState.showDismissableSnackbar(
    scope: CoroutineScope,
    message: String = "",
    actionLabel: String? = null,
    onActionPerformed: () -> Unit = {},
    duration: SnackbarDuration = SnackbarDuration.Long,
) {
    currentSnackbarData?.dismiss()
    scope.launch {
        showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = duration,
            withDismissAction = false,
        ).let { result ->
            when (result) {
                SnackbarResult.ActionPerformed -> onActionPerformed()
                SnackbarResult.Dismissed -> currentSnackbarData?.dismiss()
                else -> Unit
            }
        }
    }
}
