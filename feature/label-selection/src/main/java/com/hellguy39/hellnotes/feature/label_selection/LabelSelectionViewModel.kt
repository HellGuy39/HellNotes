package com.hellguy39.hellnotes.feature.label_selection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelSelectionViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val labelSelectionViewModelState = MutableStateFlow(LabelSelectionViewModelState())

    val uiState = labelSelectionViewModelState
        .map(LabelSelectionViewModelState::toUiState)
        .stateIn(
            started = SharingStarted.Eagerly,
            scope = viewModelScope,
            initialValue = labelSelectionViewModelState.value.toUiState()
        )

    init {
        savedStateHandle.get<Long>(ArgumentKeys.NoteId)?.let { noteId ->
            viewModelScope.launch {
                launch {
                    noteRepository.getNoteByIdStream(noteId).collect { note ->
                        labelSelectionViewModelState.update { state ->
                            state.copy(note = note)
                        }
                    }
                }
                launch {
                    labelRepository.getAllLabelsStream().collect { labels ->
                        labelSelectionViewModelState.update { state ->
                            state.copy(labels = labels, isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun sendEvent(event: LabelSelectionUiEvent) {
        when(event) {
            is LabelSelectionUiEvent.SelectLabel -> {
                selectLabel(label = event.label)
            }
            is LabelSelectionUiEvent.UnselectLabel -> {
                unselectLabel(label = event.label)
            }
            is LabelSelectionUiEvent.UpdateSearch -> {
                updateSearch(search = event.search)
            }
            is LabelSelectionUiEvent.CreateNewLabel -> {
                createNewLabel()
            }
        }
    }

    private fun selectLabel(label: Label) {
        viewModelScope.launch {
            val note = labelSelectionViewModelState.value.note
            noteRepository.updateNote(
                note.copy(labelIds = note.labelIds.plus(label.id ?: return@launch))
            )
        }
    }

    private fun unselectLabel(label: Label) {
        viewModelScope.launch {
            val note = labelSelectionViewModelState.value.note
            noteRepository.updateNote(
                note.copy(labelIds = note.labelIds.minus(label.id ?: return@launch))
            )
        }
    }

    private fun updateSearch(search: String) {
        viewModelScope.launch {
            labelSelectionViewModelState.update { state ->
                state.copy(search = search)
            }
        }
    }

    private fun createNewLabel() {
        viewModelScope.launch {
            val label = Label(name = labelSelectionViewModelState.value.search)
            val labelId = labelRepository.insertLabel(label)
            selectLabel(labelRepository.getLabelById(labelId))
        }
    }

}

sealed class LabelSelectionUiEvent {
    data class SelectLabel(val label: Label): LabelSelectionUiEvent()
    data class UnselectLabel(val label: Label): LabelSelectionUiEvent()
    data class UpdateSearch(val search: String): LabelSelectionUiEvent()
    object CreateNewLabel: LabelSelectionUiEvent()
}

private data class LabelSelectionViewModelState(
    val search: String = "",
    val note: Note = Note(),
    val labels: List<Label> = listOf(),
    val isLoading: Boolean = true,
) {
    fun toUiState() = LabelSelectionUiState(
        search = search,
        note = note,
        labels = labels.filter { it.name.contains(search) },
        isLoading = isLoading
    )
}

data class LabelSelectionUiState(
    val isLoading: Boolean,
    val search: String,
    val note: Note,
    val labels: List<Label>
)