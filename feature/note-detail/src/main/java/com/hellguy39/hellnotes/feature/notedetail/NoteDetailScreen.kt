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
package com.hellguy39.hellnotes.feature.notedetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import com.hellguy39.hellnotes.core.ui.components.layout.HNScaffold
import com.hellguy39.hellnotes.feature.notedetail.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    uiState: NoteDetailUiState,
    onNavigationButtonClick: () -> Unit,
    onUiEvent: (event: NoteDetailUiEvent) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val focusRequester = remember { FocusRequester() }

    val lazyListState = rememberLazyListState()

    HNScaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            if (uiState.wrapper.note.id != null) {
                NoteDetailContent(
                    innerPadding = innerPadding,
                    onUiEvent = onUiEvent,
                    focusRequester = focusRequester,
                    uiState = uiState,
                    lazyListState = lazyListState,
                )
            }
        },
        topBar = {
            NoteDetailTopAppBar(
                scrollBehavior = scrollBehavior,
                uiState = uiState,
                onUiEvent = onUiEvent,
                onNavigationButtonClick = onNavigationButtonClick
            )
        },
        bottomBar = {
            NoteDetailBottomBar(
                uiState = uiState,
                lazyListState = lazyListState,
                onUiEvent = onUiEvent
            )
        },
    )
}
