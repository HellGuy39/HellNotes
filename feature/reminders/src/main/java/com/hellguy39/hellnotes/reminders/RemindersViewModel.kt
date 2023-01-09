package com.hellguy39.hellnotes.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.domain.AlarmScheduler
import com.hellguy39.hellnotes.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.model.Remind
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    val alarmScheduler: AlarmScheduler
): ViewModel() {

    val uiState: StateFlow<UiState> = reminderRepository.getAllRemindsStream()
        .map { reminders ->
            UiState.Success(
                reminders = reminders.sortedBy { it.triggerDate }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    fun deleteRemind(remind: Remind) = viewModelScope.launch {
        reminderRepository.deleteRemind(remind)
    }

    fun updateRemind(remind: Remind) = viewModelScope.launch {
        reminderRepository.updateRemind(remind)
    }

}

sealed interface UiState {
    data class Success(
        val reminders: List<Remind>
    ) : UiState
    object Loading : UiState
}