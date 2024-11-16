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
package com.hellguy39.hellnotes.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun SearchRoute(
    navigateBack: () -> Unit,
    navigateToNoteDetail: (id: Long) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "SearchScreen")

    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()

    // TODO: refactor
    SearchScreen(
        onNavigationButtonClick = remember { { navigateBack() } },
        uiState = uiState,
        onQueryChanged =
            remember {
                { search ->
                    searchViewModel.send(SearchScreenUiEvent.OnSearchChange(search))
                }
            },
        onClearQuery =
            remember {
                {
                    searchViewModel.send(SearchScreenUiEvent.OnClearSearch)
                }
            },
        onUpdateArchiveFilter =
            remember {
                { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleArchiveFilter(enabled))
                }
            },
        onUpdateChecklistFilter =
            remember {
                { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleChecklistFilter(enabled))
                }
            },
        onUpdateReminderFilter =
            remember {
                { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleReminderFilter(enabled))
                }
            },
        onClick =
            remember {
                { noteId ->
                    if (noteId != null) {
                        navigateToNoteDetail(noteId)
                    }
                }
            },
        onLongClick = remember { { note -> } },
    )
}
