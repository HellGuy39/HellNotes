package com.hellguy39.hellnotes.feature.home.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllWrappedNotesWithRemindersStreamUseCase
import com.hellguy39.hellnotes.core.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    getAllWrappedNotesWithRemindersStreamUseCase: GetAllWrappedNotesWithRemindersStreamUseCase
): ViewModel() {

    val uiState = getAllWrappedNotesWithRemindersStreamUseCase.invoke()
        .map { noteWrappers ->
            if (noteWrappers.isEmpty()) {
                RemindersUiState.Empty
            } else {
                RemindersUiState.Success(noteWrappers)
            }
        }
            .stateIn(
                initialValue = RemindersUiState.Idle,
                started = SharingStarted.WhileSubscribed(5_000),
                scope = viewModelScope
            )

}

sealed class RemindersUiState {

    object Idle: RemindersUiState()

    object Empty: RemindersUiState()

    data class Success(val noteWrappersWithReminders: List<NoteWrapper>): RemindersUiState()

}

