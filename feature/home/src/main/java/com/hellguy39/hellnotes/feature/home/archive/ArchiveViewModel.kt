package com.hellguy39.hellnotes.feature.home.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel
    @Inject
    constructor(
        noteRepository: NoteRepository,
        labelRepository: LabelRepository,
        reminderRepository: ReminderRepository,
    ) : ViewModel() {
        val uiState: StateFlow<ArchiveUiState> =
            combine(
                noteRepository.getAllNotesStream(),
                reminderRepository.getAllRemindersStream(),
                labelRepository.getAllLabelsStream(),
            ) { notes, reminders, labels ->
                ArchiveUiState(
                    notes =
                        notes
                            .filter { note -> note.isArchived }
                            .map { note -> note.toNoteDetailWrapper(reminders, labels) },
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = ArchiveUiState(),
                )
    }

data class ArchiveUiState(
    val notes: List<NoteDetailWrapper> = listOf(),
)
