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
package com.hellguy39.hellnotes.feature.home.notes

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.home.VisualState
import com.hellguy39.hellnotes.feature.home.notes.components.NoteListTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    uiState: NoteListUiState,
    visualState: VisualState,
    onNoteClick: (noteId: Long?) -> Unit,
    onNotePress: (noteId: Long?) -> Unit,
    onDismissNote: (direction: SwipeToDismissBoxValue, noteId: Long?) -> Boolean,
    onNavigationClick: () -> Unit,
    onDeleteSelectedNotesClick: () -> Unit,
    onToggleListStyle: () -> Unit,
    onSearchbarClick: () -> Unit,
    onCancelNoteSelection: () -> Unit,
    onArchiveSelectedNotesClick: () -> Unit,
    onAddNewNoteClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteListTopAppBar(
                scrollBehavior = scrollBehavior,
                uiState = uiState,
                listStyle = visualState.listStyle,
                onCancelSelectionClick = onCancelNoteSelection,
                onNavigationClick = onNavigationClick,
                onDeleteSelectedClick = onDeleteSelectedNotesClick,
                onSearchClick = onSearchbarClick,
                onToggleListStyle = onToggleListStyle,
                onArchiveClick = onArchiveSelectedNotesClick,
            )
        },
        content = { innerPadding ->
            if (uiState.isEmpty) {
                EmptyContentPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    heroIcon = UiIcon.DrawableResources(AppIcons.NoteAdd),
                    message = UiText.StringResources(AppStrings.Placeholder.Empty),
                )
            } else {
                AnimatedContent(
                    targetState = visualState.listStyle,
                    label = "note_list_screen_animation",
                ) { listStyle ->
                    NoteList(
                        innerPadding =
                            PaddingValues(
                                top = Spaces.small + innerPadding.calculateTopPadding(),
                                bottom = Spaces.small + innerPadding.calculateBottomPadding(),
                            ),
                        noteStyle = visualState.noteStyle,
                        volume = uiState.noteVolume,
                        onClick = onNoteClick,
                        onLongClick = onNotePress,
                        onDismiss = onDismissNote,
                        isSwipeable = visualState.noteSwipesState.enabled,
                        listStyle = listStyle,
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag("fab_create_note"),
                onClick = onAddNewNoteClick,
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.Add),
                    contentDescription = stringResource(id = AppStrings.ContentDescription.AddNote),
                )
            }
        },
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
    )
}
