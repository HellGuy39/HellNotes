package com.hellguy39.hellnotes.feature.search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNoteWrappersUseCase
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.toSelectable
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        dataStoreRepository: DataStoreRepository,
        getAllNoteWrappersUseCase: GetAllNoteWrappersUseCase,
    ) : ViewModel() {
        private val search = MutableStateFlow("")

        private val filters = MutableStateFlow(FilterSelection())

        val uiState: StateFlow<SearchUiState> =
            combine(
                getAllNoteWrappersUseCase.invoke(),
                search,
                filters,
                dataStoreRepository.readListStyleState(),
                dataStoreRepository.readNoteStyleState(),
            ) { notes, search, filters, listStyle, noteStyle ->

                var searchedNotes: List<NoteDetailWrapper> =
                    notes.filter { note ->
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
                    isEmpty = searchedNotes.isEmpty(),
                    noteWrappers = searchedNotes.toSelectable().toStateList(),
                    filters = filters,
                    listStyle = listStyle,
                    noteStyle = noteStyle,
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = SearchUiState(),
                )

        fun send(uiEvent: SearchScreenUiEvent) {
            when (uiEvent) {
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
    data class OnSearchChange(val search: String) : SearchScreenUiEvent()

    data object OnClearSearch : SearchScreenUiEvent()

    data class OnToggleReminderFilter(val enabled: Boolean) : SearchScreenUiEvent()

    data class OnToggleArchiveFilter(val enabled: Boolean) : SearchScreenUiEvent()

    data class OnToggleChecklistFilter(val enabled: Boolean) : SearchScreenUiEvent()
}

data class SearchUiState(
    val search: String = "",
    val listStyle: ListStyle = ListStyle.Column,
    val noteStyle: NoteStyle = NoteStyle.Outlined,
    val isEmpty: Boolean = false,
    val noteWrappers: SnapshotStateList<Selectable<NoteDetailWrapper>> = mutableStateListOf(),
    val filters: FilterSelection = FilterSelection(),
)

data class FilterSelection(
    val withReminder: Boolean = false,
    val withChecklist: Boolean = false,
    val withArchive: Boolean = false,
)
