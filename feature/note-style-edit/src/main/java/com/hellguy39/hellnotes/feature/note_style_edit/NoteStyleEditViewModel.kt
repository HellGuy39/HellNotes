package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteStyleEditViewModel
@Inject
constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    val uiState = dataStoreRepository.readNoteStyleState()
        .map {  noteStyle -> NoteStyleEditUiState(noteStyle = noteStyle) }
        .stateIn(
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope,
            initialValue = NoteStyleEditUiState()
        )

    fun saveNoteStyle(noteStyle: NoteStyle) {
        viewModelScope.launch {
            dataStoreRepository.saveNoteStyleState(
                noteStyle = noteStyle
            )
        }
    }
}

data class NoteStyleEditUiState(
    val noteStyle: NoteStyle = NoteStyle.Outlined
)