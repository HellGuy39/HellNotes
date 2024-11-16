/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.noteswipeedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.settings.DataStoreRepository
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteSwipeEditScreenViewModel
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        val uiState =
            dataStoreRepository.readNoteSwipesState()
                .map { state ->
                    NoteSwipeEditScreenUiState(
                        noteSwipesState = state,
                    )
                }
                .stateIn(
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = NoteSwipeEditScreenUiState(),
                    scope = viewModelScope,
                )

        fun send(event: NoteSwipeEditScreenUiEvent) {
            when (event) {
                is NoteSwipeEditScreenUiEvent.EnableNoteSwipes -> {
                    saveNoteSwipesEnabled(event.enabled)
                }
                is NoteSwipeEditScreenUiEvent.SelectLeftAction -> {
                    saveNoteSwipeLeft(event.noteSwipe)
                }
                is NoteSwipeEditScreenUiEvent.SelectRightAction -> {
                    saveNoteSwipeRight(event.noteSwipe)
                }
            }
        }

        private fun saveNoteSwipeLeft(noteSwipe: NoteSwipe) {
            viewModelScope.launch {
                val state = uiState.value.noteSwipesState

                dataStoreRepository.saveNoteSwipesState(
                    state.copy(swipeLeft = noteSwipe),
                )
            }
        }

        private fun saveNoteSwipeRight(noteSwipe: NoteSwipe) {
            viewModelScope.launch {
                val state = uiState.value.noteSwipesState

                dataStoreRepository.saveNoteSwipesState(
                    state.copy(swipeRight = noteSwipe),
                )
            }
        }

        private fun saveNoteSwipesEnabled(enabled: Boolean) {
            viewModelScope.launch {
                val state = uiState.value.noteSwipesState

                dataStoreRepository.saveNoteSwipesState(
                    state.copy(enabled = enabled),
                )
            }
        }
    }

data class NoteSwipeEditScreenUiState(
    val noteSwipesState: NoteSwipesState = NoteSwipesState(false, NoteSwipe.None, NoteSwipe.None),
)

sealed class NoteSwipeEditScreenUiEvent {
    data class SelectRightAction(val noteSwipe: NoteSwipe) : NoteSwipeEditScreenUiEvent()

    data class SelectLeftAction(val noteSwipe: NoteSwipe) : NoteSwipeEditScreenUiEvent()

    data class EnableNoteSwipes(val enabled: Boolean) : NoteSwipeEditScreenUiEvent()
}
