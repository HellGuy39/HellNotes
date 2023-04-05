package com.hellguy39.hellnotes.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.use_case.GetAllNotesWithRemindersAndLabelsStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.toNoteDetailWrapper
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
    getAllNotesWithRemindersAndLabelsStreamUseCase: GetAllNotesWithRemindersAndLabelsStreamUseCase
): ViewModel() {

    private val _listStyle: MutableStateFlow<ListStyle> = MutableStateFlow(ListStyle.Column)
    val listStyle = _listStyle.asStateFlow()

    private val _noteStyle: MutableStateFlow<NoteStyle> = MutableStateFlow(NoteStyle.Outlined)
    val noteStyle = _noteStyle.asStateFlow()

    private val search = MutableStateFlow("")

    private val filters = MutableStateFlow(FilterSelection())

    val uiState: StateFlow<SearchUiState> =
        combine(
            getAllNotesWithRemindersAndLabelsStreamUseCase.invoke(),
            search,
            filters
        ) { notes, search, filters ->

            var searchedNotes: List<NoteDetailWrapper> = notes.filter { note ->
                note.note.note.contains(search, true) ||
                        note.note.title.contains(search, true)
            }

            if (filters.withArchive) {
                searchedNotes = searchedNotes.filter { note -> note.note.isArchived }
            } else {
                searchedNotes = searchedNotes.filter { note -> !note.note.isArchived }
            }

            if (filters.withChecklist) {
                searchedNotes = searchedNotes.filter { note -> note.checklists.isNotEmpty() }
            }

            if (filters.withReminder) {
                searchedNotes = searchedNotes.filter { note -> note.reminders.isNotEmpty() }
            }

            SearchUiState(
                search = search,
                isLoading = false,
                notes = searchedNotes,
                filters = filters
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SearchUiState.initialInstance()
            )


    init {
        viewModelScope.launch {
            launch {
                dataStoreRepository.readListStyleState().collect { listStyle ->
                    _listStyle.update { listStyle }
                }
            }
            launch {
                dataStoreRepository.readNoteStyleState().collect { noteStyle ->
                    _noteStyle.update { noteStyle }
                }
            }
        }
    }

    fun send(uiEvent: SearchScreenUiEvent) {
        when(uiEvent) {
            is SearchScreenUiEvent.OnSearchChange -> updateSearch(uiEvent.search)

            is SearchScreenUiEvent.OnClearSearch -> updateSearch("")

            is SearchScreenUiEvent.OnToggleChecklistFilter -> updateChecklistFilter(uiEvent.enabled)

            is SearchScreenUiEvent.OnToggleArchiveFilter -> updateArchiveFilter(uiEvent.enabled)

            is SearchScreenUiEvent.OnToggleReminderFilter -> updateReminderFilter(uiEvent.enabled)
        }
    }

    private fun updateSearch(query: String) {
        viewModelScope.launch {
            search.update { query }
        }
    }

    private fun updateReminderFilter(enabled: Boolean) {
        viewModelScope.launch {
            filters.update { state -> state.copy(withReminder = enabled) }
        }
    }

    private fun updateChecklistFilter(enabled: Boolean) {
        viewModelScope.launch {
            filters.update { state -> state.copy(withChecklist = enabled) }
        }
    }

    private fun updateArchiveFilter(enabled: Boolean) {
        viewModelScope.launch {
            filters.update { state -> state.copy(withArchive = enabled) }
        }
    }

}

sealed class SearchScreenUiEvent {
    data class OnSearchChange(val search: String): SearchScreenUiEvent()
    object OnClearSearch: SearchScreenUiEvent()
    data class OnToggleReminderFilter(val enabled: Boolean): SearchScreenUiEvent()
    data class OnToggleArchiveFilter(val enabled: Boolean): SearchScreenUiEvent()
    data class OnToggleChecklistFilter(val enabled: Boolean): SearchScreenUiEvent()

}

data class SearchUiState(
    val search: String,
    val isLoading: Boolean,
    val notes: List<NoteDetailWrapper>,
    val filters: FilterSelection
) {
    companion object {
        fun initialInstance() = SearchUiState(
            search = "",
            isLoading = true,
            notes = listOf(),
            filters = FilterSelection()
        )
    }
}

data class FilterSelection(
    val withReminder: Boolean = false,
    val withChecklist: Boolean = false,
    val withArchive: Boolean = false
)