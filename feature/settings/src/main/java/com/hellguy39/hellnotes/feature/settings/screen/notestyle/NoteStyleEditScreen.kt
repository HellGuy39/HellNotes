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
package com.hellguy39.hellnotes.feature.settings.screen.notestyle

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.animations.slideRightOnlyContentTransform
import com.hellguy39.hellnotes.core.ui.asDisplayableString
import com.hellguy39.hellnotes.core.ui.components.cards.NoteCard
import com.hellguy39.hellnotes.core.ui.components.items.HNRadioListItem
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Duration
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteStyleEditScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: NoteStyleEditUiState,
    onNoteStyleChange: (NoteStyle) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val styles by remember { mutableStateOf(NoteStyle.styles()) }

    val exampleNoteWrapper =
        NoteWrapper(
            note =
                Note(
                    title = "This is a sample note",
                    note =
                        "We have to start from the fact that synthetic testing determines the " +
                            "high demand for the distribution of internal reserves and resources.",
                ),
            labels =
                listOf(
                    Label(name = "Important"),
                ),
            reminders = listOf(),
        )

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = AppStrings.Title.NoteStyle),
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier =
                    Modifier.fillMaxSize()
                        .selectableGroup(),
                contentPadding =
                    PaddingValues(
                        top = innerPadding.calculateTopPadding() + Spaces.medium,
                        bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
                    ),
            ) {
                item {
                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Spaces.medium)
                                .padding(bottom = Spaces.medium),
                        shape = MaterialTheme.shapes.extraLarge,
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
                                    .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            AnimatedContent(
                                targetState = uiState.noteStyle,
                                transitionSpec = {
                                    slideRightOnlyContentTransform(Duration.FAST, Duration.FAST)
                                },
                                label = "slideContentTransform",
                            ) { style ->
                                NoteCard(
                                    modifier =
                                        Modifier
                                            .padding(horizontal = 32.dp, vertical = 32.dp)
                                            .width(192.dp),
                                    noteWrapper = exampleNoteWrapper,
                                    noteStyle = style,
                                    isSelected = false,
                                    onClick = {},
                                    onLongClick = {},
                                )
                            }
                        }
                    }
                }
                items(
                    items = styles,
                    key = { style -> style.tag },
                ) { style ->
                    HNRadioListItem(
                        headlineContent = { Text(style.asDisplayableString()) },
                        selected = uiState.noteStyle == style,
                        onClick = { onNoteStyleChange(style) },
                    )
                }
            }
        },
    )
}
