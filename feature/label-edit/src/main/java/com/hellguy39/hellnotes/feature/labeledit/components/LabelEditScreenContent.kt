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
package com.hellguy39.hellnotes.feature.labeledit.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import com.hellguy39.hellnotes.core.ui.focus.requestOnceAfterRecompositionIf
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.labeledit.LabelEditScreenUiEvent
import com.hellguy39.hellnotes.feature.labeledit.LabelEditUiState

@Composable
fun LabelEditScreenContent(
    paddingValues: PaddingValues,
    uiState: LabelEditUiState,
    onUiEvent: (uiEvent: LabelEditScreenUiEvent) -> Unit,
    onCreateLabel: (name: String) -> Boolean,
    focusRequester: FocusRequester,
) {
    val context = LocalContext.current

    focusRequester.requestOnceAfterRecompositionIf {
        uiState.action == context.getString(AppStrings.Action.Create)
    }

    val itemModifier =
        remember {
            Modifier
                .fillMaxWidth()
                .padding(Spaces.extraSmall)
        }


    LazyColumn(
        contentPadding = paddingValues,
    ) {
        item(key = -1) {
            LabelSearchItem(
                modifier = itemModifier,
                focusRequester = focusRequester,
                onCreateLabel = onCreateLabel,
            )
        }
        itemsIndexed(
            items = uiState.labels,
            key = { index, label -> label.id ?: 0 },
        ) { index, label ->
            LabelItem(
                modifier = itemModifier,
                label = label,
                onDelete = { onUiEvent(LabelEditScreenUiEvent.DeleteLabel(index)) },
                onLabelChange = { text ->
                    onUiEvent(LabelEditScreenUiEvent.UpdateLabel(index, text))
                },
            )
        }
    }
}
