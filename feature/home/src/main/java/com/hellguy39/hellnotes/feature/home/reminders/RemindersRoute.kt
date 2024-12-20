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
package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersRoute(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "RemindersScreen")

    val uiState by remindersViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()

    remindersViewModel.singleUiEventFlow.collectAsEventsWithLifecycle { event ->
        when (event) {
            is RemindersSingleUiEvent.ShowSnackbar -> {
                homeState.showSnack(event.text, event.action)
            }
        }
    }

    remindersViewModel.navigationEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is RemindersNavigationEvent.NavigateToNoteDetail -> {
                navigateToNoteDetail(event.noteId)
            }
        }
    }

    RemindersScreen(
        uiState = uiState,
        visualState = visualState,
        snackbarHostState = homeState.snackbarHostState,
        onNoteClick = remember { { noteId -> remindersViewModel.onNoteClick(noteId) } },
        onNotePress = remember { { noteId -> remindersViewModel.onNotePress(noteId) } },
        onDismissNote =
            remember {
                { direction, noteId ->
                    val swipeAction = visualsViewModel.calculateSwipeAction(direction)
                    remindersViewModel.onNoteDismiss(swipeAction, noteId)
                    visualsViewModel.calculateSwipeResult(swipeAction)
                }
            },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onToggleListStyle = remember { visualsViewModel::toggleListStyle },
        onDeleteSelectedClick = remember { { remindersViewModel.onDeleteSelectedItems() } },
        onCancelSelectionClick = remember { { remindersViewModel.onCancelItemSelection() } },
        onSearchClick = remember { { navigateToSearch() } },
    )
}
