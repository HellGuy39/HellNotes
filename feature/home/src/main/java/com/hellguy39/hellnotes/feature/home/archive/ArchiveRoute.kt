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
package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

@Composable
fun ArchiveRoute(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "ArchiveScreen")

    val uiState by archiveViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()

    archiveViewModel.singleUiEventFlow.collectAsEventsWithLifecycle { event ->
        when (event) {
            is ArchiveSingleUiEvent.ShowSnackbar -> {
                homeState.showSnack(event.text, event.action)
            }
        }
    }

    archiveViewModel.navigationEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is ArchiveNavigationEvent.NavigateToNoteDetail -> {
                navigateToNoteDetail(event.noteId)
            }
        }
    }

    ArchiveScreen(
        uiState = uiState,
        visualState = visualState,
        onNoteClick = remember { { noteId -> archiveViewModel.onNoteClick(noteId) } },
        onNotePress = remember { { noteId -> archiveViewModel.onNotePress(noteId) } },
        onCancelSelectionClick = remember { { archiveViewModel.onCancelItemSelection() } },
        onDeleteSelectedClick = remember { { archiveViewModel.onDeleteSelectedItems() } },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onUnarchiveSelectedClick = remember { { archiveViewModel.onArchiveSelectedItems() } },
        onSearchClick = remember { { navigateToSearch() } },
        onToggleListStyle = remember { visualsViewModel::toggleListStyle },
        listStyle = visualState.listStyle,
        snackbarHostState = homeState.snackbarHostState,
    )
}
