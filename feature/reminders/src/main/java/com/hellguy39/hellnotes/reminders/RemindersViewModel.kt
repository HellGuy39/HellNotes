package com.hellguy39.hellnotes.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.RemindRepository
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.model.util.ListStyle
import com.hellguy39.hellnotes.model.util.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val remindRepository: RemindRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var getRemindersJob: Job? = null

    init {
        fetchReminders()
    }

    private fun fetchReminders() = viewModelScope.launch {
        getRemindersJob?.cancel()
        getRemindersJob = remindRepository.getAllReminds()
            .onEach { reminders ->
                if (reminders.isNotEmpty()) {
                    _uiState.update {
                        UiState.Success(
                            reminders = reminders,
                        )
                    }
                } else {
                    _uiState.update { UiState.Empty }
                }
            }
            .launchIn(viewModelScope)
    }
}

sealed interface UiState {
    data class Success(
        val reminders: List<Remind>
    ) : UiState
    object Empty : UiState
    object Loading : UiState

}