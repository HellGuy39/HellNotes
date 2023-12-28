package com.hellguy39.hellnotes.feature.labelselection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelSelectionViewModel
    @Inject
    constructor(
        private val labelRepository: LabelRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val search = MutableStateFlow("")
        private val noteId = savedStateHandle.getArgument(Arguments.NoteId)

        val uiState: StateFlow<LabelSelectionUiState> =
            combine(
                labelRepository.getAllLabelsStream(),
                search,
            ) { labels, search ->
                LabelSelectionUiState(
                    search = search,
                    labels =
                        labels
                            .filter { label -> label.name.contains(search) }
                            .sortedByDescending { label -> label.id },
                    noteId = noteId,
                    isLoading = false,
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = LabelSelectionUiState(),
                )

        fun sendEvent(event: LabelSelectionUiEvent) {
            when (event) {
                is LabelSelectionUiEvent.SelectLabel -> {
                    selectLabel(label = event.label)
                }
                is LabelSelectionUiEvent.UnselectLabel -> {
                    unselectLabel(label = event.label)
                }
                is LabelSelectionUiEvent.UpdateSearch -> {
                    updateSearch(s = event.search)
                }
                is LabelSelectionUiEvent.CreateNewLabel -> {
                    createNewLabel()
                }
            }
        }

        private fun selectLabel(label: Label) {
            viewModelScope.launch {
                labelRepository.updateLabel(
                    label.copy(noteIds = label.noteIds.plus(noteId)),
                )
            }
        }

        private fun unselectLabel(label: Label) {
            viewModelScope.launch {
                labelRepository.updateLabel(
                    label.copy(noteIds = label.noteIds.minus(noteId)),
                )
            }
        }

        private fun updateSearch(s: String) {
            viewModelScope.launch {
                search.update { s }
            }
        }

        private fun createNewLabel() {
            viewModelScope.launch {
                val label = Label(name = search.value)
                val labelId = labelRepository.insertLabel(label)
                selectLabel(labelRepository.getLabelById(labelId))
            }
        }
    }

sealed class LabelSelectionUiEvent {
    data class SelectLabel(val label: Label) : LabelSelectionUiEvent()

    data class UnselectLabel(val label: Label) : LabelSelectionUiEvent()

    data class UpdateSearch(val search: String) : LabelSelectionUiEvent()

    object CreateNewLabel : LabelSelectionUiEvent()
}

data class LabelSelectionUiState(
    val isLoading: Boolean = false,
    val noteId: Long = Arguments.NoteId.emptyValue,
    val search: String = "",
    val labels: List<Label> = emptyList(),
)
