package com.hellguy39.hellnotes.feature.home.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    noteRepository: NoteRepository,
    labelRepository: LabelRepository,
    reminderRepository: ReminderRepository,
): ViewModel() {

    val uiState: StateFlow<ArchiveUiState> =
        combine(
            noteRepository.getAllNotesStream(),
            reminderRepository.getAllRemindersStream(),
            labelRepository.getAllLabelsStream()
        ) { notes, reminders, labels ->
            ArchiveUiState(
                notes = notes
                    .filter { note -> note.isArchived }
                    .map { note -> note.toNoteDetailWrapper(reminders, labels) },
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ArchiveUiState.initialInstance()
        )

}

data class ArchiveUiState(
    val notes: List<NoteDetailWrapper>,
) {
    companion object {
        fun initialInstance() = ArchiveUiState(
            notes = listOf()
        )
    }
}
