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

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelEditViewModel
    @Inject
    constructor(
        private val labelRepository: LabelRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val action = savedStateHandle.getArgument(Arguments.Action)

        val uiState: StateFlow<LabelEditUiState> =
            labelRepository.getAllLabelsStream()
                .map { labels ->
                    LabelEditUiState(
                        labels = labels.sortedByDescending { label -> label.id }.toStateList(),
                        action = action,
                        isIdle = false,
                    )
                }
                .stateIn(
                    initialValue = LabelEditUiState(),
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
                )

        fun send(uiEvent: LabelEditScreenUiEvent) {
            when (uiEvent) {
                is LabelEditScreenUiEvent.InsertLabel -> {
                    insertLabel(uiEvent.name)
                }
                is LabelEditScreenUiEvent.UpdateLabel -> {
                    updateLabel(uiEvent.index, uiEvent.name)
                }
                is LabelEditScreenUiEvent.DeleteLabel -> {
                    deleteLabel(uiEvent.index)
                }
            }
        }

        fun isLabelUnique(name: String): Boolean {
            return uiState.value.labels.find { label ->
                label.name == name.trim()
            } == null
        }

        private fun insertLabel(name: String) {
            viewModelScope.launch {
                val label = Label(name = name.trim())
                labelRepository.insertLabel(label)
            }
        }

        private fun deleteLabel(index: Int) {
            viewModelScope.launch {
                val label = uiState.value.labels[index]
                labelRepository.deleteLabel(label)
            }
        }

        private fun updateLabel(index: Int, name: String) {
            viewModelScope.launch {
                val label = uiState.value.labels[index].copy(name = name.trim())
                labelRepository.updateLabel(label)
            }
        }
    }

data class LabelEditUiState(
    val isIdle: Boolean = true,
    val action: String = "",
    val labels: SnapshotStateList<Label> = mutableStateListOf(),
)

sealed class LabelEditScreenUiEvent {
    data class InsertLabel(val name: String) : LabelEditScreenUiEvent()

    data class DeleteLabel(val index: Int) : LabelEditScreenUiEvent()

    data class UpdateLabel(val index: Int, val name: String) : LabelEditScreenUiEvent()
}
