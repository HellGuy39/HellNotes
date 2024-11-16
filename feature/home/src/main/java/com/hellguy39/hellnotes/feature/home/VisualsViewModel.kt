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
package com.hellguy39.hellnotes.feature.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.settings.DataStoreRepository
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

        @OptIn(ExperimentalMaterial3Api::class)
        fun calculateSwipeAction(direction: SwipeToDismissBoxValue): NoteSwipe {
            return if (direction == SwipeToDismissBoxValue.StartToEnd) {
                visualState.value.noteSwipesState.swipeRight
            } else {
                visualState.value.noteSwipesState.swipeLeft
            }
        }

        fun calculateSwipeResult(swipe: NoteSwipe): Boolean {
            return when (swipe) {
                NoteSwipe.None -> false
                NoteSwipe.Delete -> true
                NoteSwipe.Archive -> true
            }
        }
    }

data class VisualState(
    val noteSwipesState: NoteSwipesState = NoteSwipesState(false, NoteSwipe.None, NoteSwipe.None),
    val listStyle: ListStyle = ListStyle.Column,
    val noteStyle: NoteStyle = NoteStyle.Outlined,
)
