package com.hellguy39.hellnotes.feature.home.label

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.feature.home.reminders.RemindersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelViewModel @Inject constructor(
    noteRepository: NoteRepository,
    labelRepository: LabelRepository,
    reminderRepository: ReminderRepository,
): ViewModel() {

    private val selectedLabel = MutableStateFlow(Label())

    val uiState: StateFlow<LabelUiState> =
        combine(
            selectedLabel,
            noteRepository.getAllNotesStream(),
            labelRepository.getAllLabelsStream(),
            reminderRepository.getAllRemindersStream(),
        ) { selectedLabel, notes, labels, reminders ->
            LabelUiState(
                notes = notes
                    .filter { !it.isArchived }
                    .map { note ->
                        note.toNoteDetailWrapper(
                            reminders = reminders,
                            labels = labels
                        )
                    }
                    .filter { it.labels.contains(selectedLabel) },
                label = selectedLabel
            )
        }
            .stateIn(
                initialValue = LabelUiState.initialInstance(),
                started = SharingStarted.WhileSubscribed(5_000),
                scope = viewModelScope
            )

    fun selectLabel(label: Label) {
        viewModelScope.launch {
            selectedLabel.update { label }
        }
    }

}

data class LabelUiState(
    val label: Label,
    val notes: List<NoteDetailWrapper>,
) {
    companion object {
        fun initialInstance() = LabelUiState(
            label = Label(),
            notes = listOf()
        )
    }
}