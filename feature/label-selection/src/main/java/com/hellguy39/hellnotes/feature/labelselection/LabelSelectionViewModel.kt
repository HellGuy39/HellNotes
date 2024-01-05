package com.hellguy39.hellnotes.feature.labelselection

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
                val filteredLabels =
                    labels
                        .filter { label -> label.name.contains(search) }
                        .sortedByDescending { label -> label.id }

                val checkableLabels =
                    filteredLabels.map { label ->
                        CheckableLabel(
                            label = label,
                            isChecked = label.noteIds.contains(noteId),
                        )
                    }

                val isShowCreateNewLabel = isShowCreateNewLabel(filteredLabels, search)

                val isEmpty = filteredLabels.isEmpty() && search.isEmpty()

                LabelSelectionUiState(
                    isShowCreateNewLabelItem = isShowCreateNewLabel,
                    isEmpty = isEmpty,
                    search = search,
                    checkableLabels = checkableLabels.toStateList(),
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = LabelSelectionUiState(),
                )

        fun sendEvent(event: LabelSelectionUiEvent) {
            when (event) {
                is LabelSelectionUiEvent.ToggleLabelCheckbox -> {
                    toggleLabelCheckbox(event.index)
                }
                is LabelSelectionUiEvent.UpdateSearch -> {
                    updateSearch(event.search)
                }
                is LabelSelectionUiEvent.CreateNewLabel -> {
                    createNewLabel()
                }
            }
        }

        private fun toggleLabelCheckbox(index: Int) {
            viewModelScope.launch {
                val checkableLabel = uiState.value.checkableLabels[index]
                val noteIds = checkableLabel.label.noteIds
                labelRepository.updateLabel(
                    checkableLabel.label.copy(
                        noteIds =
                            if (checkableLabel.isChecked.not()) {
                                noteIds.plus(noteId)
                            } else {
                                noteIds.minus(noteId)
                            },
                    ),
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
                val name = search.value.trim()
                updateSearch(name)
                labelRepository.insertLabel(
                    Label(
                        name = name,
                        noteIds = listOf(noteId),
                    ),
                )
            }
        }
    }

sealed class LabelSelectionUiEvent {
    data class ToggleLabelCheckbox(val index: Int) : LabelSelectionUiEvent()

    data class UpdateSearch(val search: String) : LabelSelectionUiEvent()

    data object CreateNewLabel : LabelSelectionUiEvent()
}

data class CheckableLabel(
    val label: Label,
    val isChecked: Boolean,
)

data class LabelSelectionUiState(
    val isShowCreateNewLabelItem: Boolean = false,
    val isEmpty: Boolean = false,
    val search: String = "",
    val checkableLabels: SnapshotStateList<CheckableLabel> = mutableStateListOf()
)

private fun isShowCreateNewLabel(
    labels: List<Label>,
    query: String,
): Boolean {
    return (
        labels.isEmpty() || labels.size > 2 ||
            (labels.size == 1 && query != labels[0].name)
    ) &&
        (query.isNotBlank() && query.isNotEmpty())
}
