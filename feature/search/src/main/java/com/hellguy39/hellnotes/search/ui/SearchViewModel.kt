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

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var getNotesJob: Job? = null

    init {
        _uiState.update { it.copy(listStyle = appSettingsRepository.getListStyle()) }
        fetchNotes("")
    }

    @OptIn(FlowPreview::class)
    private fun fetchNotes(query: String) = viewModelScope.launch {
        getNotesJob?.cancel()
        getNotesJob = noteRepository.getAllNotesWithQuery(query)
            .onEach { notes ->
                if (notes.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            notes = notes
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            notes = listOf()
                        )
                    }
                }
            }
            .debounce(200L)
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(query = query)
        }
        fetchNotes(query)
    }

}

data class UiState(
    val query: String,
    val notes: List<Note>,
    val listStyle: ListStyle,
) {
    constructor(): this("", listOf(), ListStyle.Column)
}