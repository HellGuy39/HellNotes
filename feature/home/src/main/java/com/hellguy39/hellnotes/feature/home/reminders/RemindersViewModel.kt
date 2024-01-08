package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel
    @Inject
    constructor(
        noteRepository: NoteRepository,
        labelRepository: LabelRepository,
        reminderRepository: ReminderRepository,
    ) : ViewModel() {
        val uiState: StateFlow<RemindersUiState> =
            combine(
                noteRepository.getAllNotesStream(),
                labelRepository.getAllLabelsStream(),
                reminderRepository.getAllRemindersStream(),
            ) { notes, labels, reminders ->

                val wrappers =
                    notes.map { note ->
                        note.toNoteDetailWrapper(
                            reminders = reminders.sortedBy { it.triggerDate },
                            labels = labels,
                        )
                    }
                        .filter { wrapper -> wrapper.reminders.isNotEmpty() }
                        .sortedBy { wrapper -> wrapper.reminders.first().triggerDate }

                RemindersUiState(
                    noteCategories =
                        mutableStateListOf(
                            NoteCategory(
                                notes = wrappers.toStateList(),
                            ),
                        ),
                    isEmpty = wrappers.isEmpty(),
                )
            }
                .stateIn(
                    initialValue = RemindersUiState(),
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
                )
    }

data class RemindersUiState(
    val isEmpty: Boolean = false,
    val noteCategories: SnapshotStateList<NoteCategory> = mutableStateListOf(),
)
