package com.hellguy39.hellnotes.feature.reminder_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.system_features.AlarmScheduler
import com.hellguy39.hellnotes.core.model.Reminder
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ReminderEditViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val alarmScheduler: AlarmScheduler,
    private val dateHelper: DateHelper,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val reminderEditViewModelState = MutableStateFlow(ReminderEditViewModelState(isLoading = true))

    val uiState = reminderEditViewModelState
        .map(ReminderEditViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            reminderEditViewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            val noteId = savedStateHandle.get<Long>(ArgumentKeys.NoteId)
            val reminderId = savedStateHandle.get<Long>(ArgumentKeys.ReminderId)

            val isReminderExist = reminderId != null && reminderId != ArgumentDefaultValues.NewReminder

            reminderEditViewModelState.update { state ->
                state.copy(
                    noteId = noteId,
                    reminderId = reminderId,
                    isEdit = isReminderExist
                )
            }

            if (isReminderExist) {
                val reminder = reminderRepository.getReminderById(id = reminderId ?: return@launch)

                reminderEditViewModelState.update { state ->
                    state.copy(
                        message = reminder.message,
                        localDateTime = dateHelper.epochMillisToLocalDateTime(reminder.triggerDate)
                    )
                }
            }
        }
    }

    fun isPossibleToCreateReminder(): Boolean {
        return dateHelper.dateToEpochMillis(
            reminderEditViewModelState.value.localDateTime
        ) > dateHelper.getCurrentTimeInEpochMilli()
    }

    fun insertReminder() {
        viewModelScope.launch {
            reminderEditViewModelState.value.let { state ->

                val reminder = Reminder(
                    noteId = state.noteId ?: return@launch,
                    message = state.message,
                    triggerDate = dateHelper.dateToEpochMillis(state.localDateTime)
                )

                reminderRepository.insertReminder(reminder)
                alarmScheduler.scheduleAlarm(reminder)
            }
        }
    }

    fun deleteReminder() {
        viewModelScope.launch {
            reminderEditViewModelState.value.let { state ->

                val reminder = Reminder(
                    id = state.reminderId ?: return@launch,
                    noteId = state.noteId ?: return@launch,
                    message = state.message,
                    triggerDate = dateHelper.dateToEpochMillis(state.localDateTime)
                )

                reminderRepository.deleteReminder(reminder)
                alarmScheduler.cancelAlarm(reminder)
            }
        }
    }

    fun updateReminder() {
        viewModelScope.launch {
            reminderEditViewModelState.value.let { state ->

                val reminder = Reminder(
                    id = state.reminderId ?: return@launch,
                    noteId = state.noteId ?: return@launch,
                    message = state.message,
                    triggerDate = dateHelper.dateToEpochMillis(state.localDateTime)
                )

                val oldReminder = reminderRepository.getReminderById(state.reminderId)
                reminderRepository.updateReminder(reminder)

                alarmScheduler.cancelAlarm(oldReminder)
                alarmScheduler.scheduleAlarm(reminder)
            }
        }
    }

    fun updateMessage(message: String) {
        viewModelScope.launch {
            reminderEditViewModelState.update { state ->
                state.copy(message = message)
            }
        }
    }

    fun updateDate(localDate: LocalDate) {
        viewModelScope.launch {
            reminderEditViewModelState.update { state ->
                val localTime = state.localDateTime.toLocalTime()
                state.copy(localDateTime = localTime.atDate(localDate))
            }
        }
    }

    fun updateTime(localTime: LocalTime) {
        viewModelScope.launch {
            reminderEditViewModelState.update { state ->
                val localDate = state.localDateTime.toLocalDate()
                state.copy(localDateTime = localDate.atTime(localTime))
            }
        }
    }

}

private data class ReminderEditViewModelState(
    val localDateTime: LocalDateTime = LocalDateTime.now(),
    val noteId: Long? = ArgumentDefaultValues.NewNote,
    val reminderId: Long? = ArgumentDefaultValues.NewReminder,
    val message: String = "",
    val isEdit: Boolean = false,
    val isLoading: Boolean = true
) {
    fun toUiState() = ReminderEditUiState(
        localDateTime = localDateTime,
        message = message,
        isEdit = isEdit,
        isLoading = isLoading
    )
}

data class ReminderEditUiState(
    val localDateTime: LocalDateTime,
    val message: String,
    val isEdit: Boolean,
    val isLoading: Boolean,
)