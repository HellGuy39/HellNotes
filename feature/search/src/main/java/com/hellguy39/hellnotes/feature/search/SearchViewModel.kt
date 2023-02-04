package com.hellguy39.hellnotes.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.AppSettingsRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.DateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val noteRepository: NoteRepository,
    val dateHelper: DateHelper,
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _listStyle: MutableStateFlow<ListStyle> = MutableStateFlow(ListStyle.Column)
    val listStyle = _listStyle.asStateFlow()

    private val _reminds: MutableStateFlow<List<Remind>> = MutableStateFlow(listOf())
    val reminds = _reminds.asStateFlow()

    private val _labels: MutableStateFlow<List<Label>> = MutableStateFlow(listOf())
    val labels = _labels.asStateFlow()

    private var getLabelsJob: Job? = null
    private var getRemindersJob: Job? = null
    private var getNotesJob: Job? = null

    init {
        _listStyle.update { appSettingsRepository.getListStyle() }
        fetchNotes("")
        fetchLabels()
        fetchReminds()
    }

    private fun fetchReminds() = viewModelScope.launch {
        getRemindersJob?.cancel()
        getRemindersJob = reminderRepository.getAllRemindsStream()
            .onEach { reminds ->
                _reminds.update { reminds }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchLabels() = viewModelScope.launch {
        getLabelsJob?.cancel()
        getLabelsJob = labelRepository.getAllLabelsStream()
            .onEach { labels ->
                _labels.update { labels }
            }
            .launchIn(viewModelScope)
    }


    @OptIn(FlowPreview::class)
    private fun fetchNotes(query: String) = viewModelScope.launch {
        getNotesJob?.cancel()
        getNotesJob = noteRepository.getAllNotesWithQueryStream(query)
            .onEach { notes ->
                _uiState.update {
                    UiState.Success(
                        notes = notes
                    )
                }
            }
            .debounce(500L)
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) = viewModelScope.launch {
        _query.update { query }
        fetchNotes(query)
    }

}

sealed interface UiState {
    object Loading: UiState
    data class Success(
        val notes: List<Note>
    ): UiState
}