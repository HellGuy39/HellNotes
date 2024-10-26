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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.asDisplayableString
import com.hellguy39.hellnotes.core.ui.components.items.HNListHeader
import com.hellguy39.hellnotes.core.ui.components.items.HNRadioButtonItem
import com.hellguy39.hellnotes.core.ui.components.items.HNSwitchItem
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteSwipeEditScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: NoteSwipeEditScreenUiState,
    selection: NoteSwipeEditScreenSelection,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val swipes by remember { mutableStateOf(NoteSwipe.swipes()) }

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = AppStrings.Title.NoteSwipe),
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding =
                    PaddingValues(
                        top = innerPadding.calculateTopPadding() + Spaces.medium,
                        bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
                    ),
                verticalArrangement = Arrangement.spacedBy(space = Spaces.medium),
            ) {
                item {
                    val fraction = if (uiState.noteSwipesState.enabled) 1f else 0f
                    val containerColor by animateColorAsState(
                        targetValue =
                            lerp(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.colorScheme.primaryContainer,
                                FastOutSlowInEasing.transform(fraction),
                            ),
                        animationSpec = tween(200),
                        label = "container_color",
                    )

                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = CardDefaults.cardColors(containerColor = containerColor),
                    ) {
                        val isChecked = uiState.noteSwipesState.enabled

                        HNSwitchItem(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(Spaces.large),
                            title = stringResource(id = AppStrings.Switch.UseNoteSwipesTitle),
                            checked = isChecked,
                            onClick = { selection.onNoteSwipesEnabled(!isChecked) },
                            showDivider = false,
                        )
                    }
                }
                item {
                    HNListHeader(
                        modifier =
                            Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                        title = stringResource(id = AppStrings.Label.SwipeLeft),
                        icon = painterResource(id = AppIcons.SwipeLeft),
                    )

                    Column(
                        modifier = Modifier.selectableGroup(),
                    ) {
                        swipes.forEach { swipeAction ->
                            HNRadioButtonItem(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(Spaces.medium),
                                isSelected = uiState.noteSwipesState.swipeLeft == swipeAction,
                                title = swipeAction.asDisplayableString(),
                                enabled = uiState.noteSwipesState.enabled,
                                onClick = { selection.onSwipeLeftActionSelected(swipeAction) },
                            )
                        }
                    }
                }
                item {
                    HNListHeader(
                        modifier =
                            Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                        title = stringResource(id = AppStrings.Label.SwipeRight),
                        icon = painterResource(id = AppIcons.SwipeRight),
                    )
                    Column(
                        modifier = Modifier.selectableGroup(),
                    ) {
                        swipes.forEach { swipeAction ->
                            HNRadioButtonItem(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(Spaces.medium),
                                isSelected = uiState.noteSwipesState.swipeRight == swipeAction,
                                title = swipeAction.asDisplayableString(),
                                enabled = uiState.noteSwipesState.enabled,
                                onClick = { selection.onSwipeRightActionSelected(swipeAction) },
                            )
                        }
                    }
                }
            }
        },
    )
}

data class NoteSwipeEditScreenSelection(
    val onNoteSwipesEnabled: (Boolean) -> Unit,
    val onSwipeRightActionSelected: (NoteSwipe) -> Unit,
    val onSwipeLeftActionSelected: (NoteSwipe) -> Unit,
)
