package com.hellguy39.hellnotes.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.AppSettingsRepository
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.ListStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val noteRepository: NoteRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _listStyle: MutableStateFlow<ListStyle> = MutableStateFlow(ListStyle.Column)
    val listStyle = _listStyle.asStateFlow()

    private var getNotesJob: Job? = null

    init {
        _listStyle.update { appSettingsRepository.getListStyle() }
        fetchNotes("")
    }

    @OptIn(FlowPreview::class)
    private fun fetchNotes(query: String) = viewModelScope.launch {
        getNotesJob?.cancel()
        getNotesJob = noteRepository.getAllNotesWithQueryStream(query)
            .onEach { notes ->
                if (notes.isNotEmpty()) {
                    _uiState.update {
                        UiState.Success(
                            notes = notes
                        )
                    }
                } else {
                    _uiState.update {
                        UiState.Empty
                    }
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
    object Empty: UiState
    object Loading: UiState
    data class Success(
        val notes: List<Note>
    ): UiState
}