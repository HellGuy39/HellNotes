package com.hellguy39.hellnotes.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.model.Remind
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
): ViewModel() {

    val uiState: StateFlow<UiState> = reminderRepository.getAllRemindsStream()
        .map { reminders ->
            UiState.Success(reminders = reminders)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

}

sealed interface UiState {
    data class Success(
        val reminders: List<Remind>
    ) : UiState
    object Loading : UiState
}