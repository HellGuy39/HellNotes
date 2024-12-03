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
package com.hellguy39.hellnotes.feature.home.trash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.cards.TipCard
import com.hellguy39.hellnotes.core.ui.components.layout.HNScaffold
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.VisualState
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    uiState: TrashUiState,
    visualState: VisualState,
    onNoteClick: (noteId: Long?) -> Unit,
    onNotePress: (noteId: Long?) -> Unit,
    onNavigationClick: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onRestoreSelectedClick: () -> Unit,
    onDeleteForeverSelectedClick: () -> Unit,
    onEmptyTrashClick: () -> Unit,
    onCloseTrashTip: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    HNScaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrashTopAppBar(
                uiState = uiState,
                scrollBehavior = scrollBehavior,
                onNavigationClick = onNavigationClick,
                onCancelSelectionClick = onCancelSelectionClick,
                onRestoreSelectedClick = onRestoreSelectedClick,
                onDeleteForeverSelectedClick = onDeleteForeverSelectedClick,
                onEmptyTrashClick = onEmptyTrashClick,
            )
        },
        content = { paddingValues ->
            if (uiState.isEmpty) {
                EmptyContentPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    heroIcon = UiIcon.DrawableResources(AppIcons.Delete),
                    message = UiText.StringResources(AppStrings.Placeholder.NoNotesInTrash),
                )
            } else {
                NoteList(
                    innerPadding = paddingValues,
                    noteStyle = visualState.noteStyle,
                    onClick = onNoteClick,
                    onLongClick = onNotePress,
                    isSwipeable = visualState.noteSwipesState.enabled,
                    notes = uiState.selectableNoteWrappers,
                    listStyle = visualState.listStyle,
                    listHeader = {
                        TipCard(
                            isVisible = !uiState.trashTipCompleted,
                            message = stringResource(id = AppStrings.Tip.AutoDeleteTrash),
                            onClose = onCloseTrashTip,
                        )
                    },
                )
            }
        },
    )
}
