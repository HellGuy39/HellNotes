package com.hellguy39.hellnotes.feature.labeledit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
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
                    LabelEditUiState.Success(
                        labels = labels.sortedByDescending { label -> label.id },
                        action = action,
                    )
                }
                .stateIn(
                    initialValue = LabelEditUiState.Loading,
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
                )

        fun send(uiEvent: LabelEditScreenUiEvent) {
            when (uiEvent) {
                is LabelEditScreenUiEvent.InsertLabel -> {
                    insertLabel(uiEvent.label)
                }
                is LabelEditScreenUiEvent.UpdateLabel -> {
                    updateLabel(uiEvent.label)
                }
                is LabelEditScreenUiEvent.DeleteLabel -> {
                    deleteLabel(uiEvent.label)
                }
            }
        }

        private fun insertLabel(label: Label) {
            viewModelScope.launch {
                labelRepository.insertLabel(label)
            }
        }

        private fun deleteLabel(label: Label) {
            viewModelScope.launch {
                labelRepository.deleteLabel(label)
            }
        }

        private fun updateLabel(label: Label) {
            viewModelScope.launch {
                labelRepository.updateLabel(label)
            }
        }
    }

sealed interface LabelEditUiState {
    data object Loading : LabelEditUiState

    data class Success(
        val action: String,
        val labels: List<Label>,
    ) : LabelEditUiState
}

sealed class LabelEditScreenUiEvent {
    data class InsertLabel(val label: Label) : LabelEditScreenUiEvent()

    data class DeleteLabel(val label: Label) : LabelEditScreenUiEvent()

    data class UpdateLabel(val label: Label) : LabelEditScreenUiEvent()
}
