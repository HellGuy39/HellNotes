package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisualsViewModel
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        val visualState: StateFlow<VisualState> =
            combine(
                dataStoreRepository.readListStyleState(),
                dataStoreRepository.readNoteStyleState(),
                dataStoreRepository.readNoteSwipesState(),
            ) { listStyle, noteStyle, noteSwipesState ->
                VisualState(
                    listStyle = listStyle,
                    noteStyle = noteStyle,
                    noteSwipesState = noteSwipesState,
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = VisualState(),
                )

        fun toggleListStyle() {
            viewModelScope.launch {
                val listStyle = visualState.value.listStyle
                dataStoreRepository.saveListStyleState(listStyle.swap())
            }
        }
    }

data class VisualState(
    val noteSwipesState: NoteSwipesState = NoteSwipesState(false, NoteSwipe.None, NoteSwipe.None),
    val listStyle: ListStyle = ListStyle.Column,
    val noteStyle: NoteStyle = NoteStyle.Outlined,
)
