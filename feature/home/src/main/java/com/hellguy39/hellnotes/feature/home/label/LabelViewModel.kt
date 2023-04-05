package com.hellguy39.hellnotes.feature.home.label

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.domain.use_case.GetAllNotesWithRemindersAndLabelsStreamUseCase
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.feature.home.reminders.RemindersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelViewModel @Inject constructor(
    private val labelRepository: LabelRepository,
    getAllNotesWithRemindersAndLabelsStreamUseCase: GetAllNotesWithRemindersAndLabelsStreamUseCase
): ViewModel() {

    private val selectedLabel = MutableStateFlow(Label())

    val uiState: StateFlow<LabelUiState> =
        combine(
            selectedLabel,
            labelRepository.getAllLabelsStream(),
            getAllNotesWithRemindersAndLabelsStreamUseCase.invoke(),
        ) { selectedLabel, labels, notes ->
            LabelUiState(
                notes = notes
                    .filter { wrapper -> !wrapper.note.isArchived }
                    .filter { wrapper -> wrapper.labels.contains(selectedLabel) },
                label = selectedLabel,
                allLabels = labels
            )
        }
            .stateIn(
                initialValue = LabelUiState.initialInstance(),
                started = SharingStarted.WhileSubscribed(5_000),
                scope = viewModelScope
            )

    fun send(uiEvent: LabelUiEvent) {
        when(uiEvent) {
            is LabelUiEvent.DeleteLabel -> {
                deleteLabel()
            }
            is LabelUiEvent.RenameLabel -> {
                renameLabel(uiEvent.name)
            }
            is LabelUiEvent.SelectLabel -> {
                selectLabel(uiEvent.label)
            }
        }
    }

    private fun selectLabel(label: Label) {
        viewModelScope.launch {
            selectedLabel.update { label }
        }
    }

    private fun deleteLabel() {
        viewModelScope.launch {
            val label = selectedLabel.value
            labelRepository.deleteLabel(label)
        }
    }

    private fun renameLabel(name: String) {
        viewModelScope.launch {
            val label = selectedLabel.value.copy(name = name)
            labelRepository.updateLabel(label)
            selectedLabel.update { label }
        }
    }

    fun isLabelUnique(name: String) =
        uiState.value.allLabels.find { label -> label.name == name } == null

}

sealed class LabelUiEvent {
    data class RenameLabel(val name: String): LabelUiEvent()
    object DeleteLabel: LabelUiEvent()
    data class SelectLabel(val label: Label): LabelUiEvent()
}

data class LabelUiState(
    val label: Label,
    val allLabels: List<Label>,
    val notes: List<NoteDetailWrapper>,
) {
    companion object {
        fun initialInstance() = LabelUiState(
            label = Label(),
            notes = listOf(),
            allLabels = listOf()
        )
    }
}