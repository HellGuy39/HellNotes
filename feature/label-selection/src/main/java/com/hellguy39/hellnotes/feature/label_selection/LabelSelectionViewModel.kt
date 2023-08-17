package com.hellguy39.hellnotes.feature.label_selection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.label.GetAllLabelsStreamUseCase
import com.hellguy39.hellnotes.core.domain.use_case.label.GetLabelByIdUseCase
import com.hellguy39.hellnotes.core.domain.use_case.label.InsertLabelUseCase
import com.hellguy39.hellnotes.core.domain.use_case.label.UpdateLabelUseCase
import com.hellguy39.hellnotes.core.model.local.database.Label
import com.hellguy39.hellnotes.core.ui.model.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.model.ArgumentKeys
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
class LabelSelectionViewModel @Inject constructor(
    private val insertLabelUseCase: InsertLabelUseCase,
    private val updateLabelUseCase: UpdateLabelUseCase,
    getAllLabelsStreamUseCase: GetAllLabelsStreamUseCase,
    private val getLabelByIdUseCase: GetLabelByIdUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val search = MutableStateFlow("")
    private val noteId = savedStateHandle.get<Long>(ArgumentKeys.NoteId)

    val uiState: StateFlow<LabelSelectionUiState> =
        combine(
            getAllLabelsStreamUseCase.invoke(),
            search
        ) { labels, search ->
            LabelSelectionUiState(
                search = search,
                labels = labels
                    .filter { label -> label.name.contains(search) }
                    .sortedByDescending { label -> label.id },
                noteId = noteId ?: ArgumentDefaultValues.NewNote,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LabelSelectionUiState.initialInstance()
        )

    fun sendEvent(event: LabelSelectionUiEvent) {
        when(event) {
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

            if (noteId == null)
                return@launch

            val updatedLabel = label.copy(noteIds = label.noteIds.plus(noteId))
            updateLabelUseCase.invoke(updatedLabel)
        }
    }

    private fun unselectLabel(label: Label) {
        viewModelScope.launch {

            if (noteId == null)
                return@launch

            val updatedLabel = label.copy(noteIds = label.noteIds.minus(noteId))
            updateLabelUseCase.invoke(updatedLabel)
        }
    }

    private fun updateSearch(s: String) {
        viewModelScope.launch {
            search.update { s }
        }
    }

    private fun createNewLabel() {
        viewModelScope.launch {
            val labelId = insertLabelUseCase.invoke(Label(name = search.value))
            val label = getLabelByIdUseCase.invoke(labelId)
            selectLabel(label)
        }
    }

}

sealed class LabelSelectionUiEvent {

    data class SelectLabel(val label: Label): LabelSelectionUiEvent()

    data class UnselectLabel(val label: Label): LabelSelectionUiEvent()

    data class UpdateSearch(val search: String): LabelSelectionUiEvent()

    data object CreateNewLabel: LabelSelectionUiEvent()

}

data class LabelSelectionUiState(
    val isLoading: Boolean,
    val noteId: Long,
    val search: String,
    val labels: List<Label>
) {
    companion object {
        fun initialInstance() = LabelSelectionUiState(
            isLoading = false,
            search = "",
            labels = emptyList(),
            noteId = ArgumentDefaultValues.NewNote
        )
    }
}