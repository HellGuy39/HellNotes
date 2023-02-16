package com.hellguy39.hellnotes.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.DateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val noteRepository: NoteRepository,
    val dateHelper: DateHelper,
): ViewModel() {

    private val searchViewModelState = MutableStateFlow(SearchViewModelState())

    private val _listStyle: MutableStateFlow<ListStyle> = MutableStateFlow(ListStyle.Column)
    val listStyle = _listStyle.asStateFlow()

    val uiState = searchViewModelState
        .map(SearchViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            searchViewModelState.value.toUiState()
        )


    init {
        viewModelScope.launch {

            launch {
                dataStoreRepository.readListStyleState().collect { listStyle ->
                    _listStyle.update { listStyle }
                }
            }

            launch {
                noteRepository.getAllNotesStream().collect { notes ->
                    searchViewModelState.update { state ->
                        state.copy(notes = notes, isLoading = false)
                    }
                }
            }
            launch {
                reminderRepository.getAllRemindsStream().collect { reminders ->
                    searchViewModelState.update { it.copy(reminders = reminders) }
                }
            }
            launch {
                labelRepository.getAllLabelsStream().collect { labels ->
                    searchViewModelState.update { it.copy(labels = labels) }
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            searchViewModelState.update { it.copy(search = query) }
        }
    }
}

private data class SearchViewModelState(
    val search: String = "",
    val isLoading: Boolean = true,
    val notes: List<Note> = listOf(),
    val labels: List<Label> = listOf(),
    val reminders: List<Remind> = listOf(),
) {
    fun toUiState(): SearchUiState {

        val allNotes = notes.map { note ->
            note.toNoteDetailWrapper(
                reminders = reminders,
                labels = labels
            )
        }

        val searchNotes = allNotes.filter {
            it.note.note.contains(search, true) ||
                    it.note.title.contains(search, true)
        }

        return SearchUiState(
            search = search,
            isLoading = isLoading,
            notes = searchNotes.filter { !it.note.isArchived },
            archivedNotes = searchNotes.filter { it.note.isArchived }
        )
    }
}

data class SearchUiState(
    val search: String,
    val isLoading: Boolean,
    val notes: List<NoteDetailWrapper>,
    val archivedNotes: List<NoteDetailWrapper>
)