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
package com.hellguy39.hellnotes.feature.aboutapp.screen.reset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.cards.InfoCard
import com.hellguy39.hellnotes.core.ui.components.items.HNCheckboxListItem
import com.hellguy39.hellnotes.core.ui.components.items.HNListHeader
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResetScreen(
    uiState: ResetUiState,
    onNavigationButtonClick: () -> Unit,
    onResetClick: () -> Unit,
    onToggleResetDatabase: () -> Unit,
    onToggleResetSettings: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = AppStrings.Title.Reset),
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier =
                            Modifier
                                .navigationBarsPadding()
                                .padding(Spaces.medium),
                        onClick = onResetClick,
                        enabled = uiState.resetButtonEnabled(),
                    ) {
                        Text(
                            text = stringResource(id = AppStrings.Button.Reset),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize(),
            verticalArrangement =
                Arrangement.spacedBy(
                    space = Spaces.large,
                ),
            contentPadding =
                PaddingValues(
                    top = innerPadding.calculateTopPadding() + Spaces.medium,
                    bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
                ),
        ) {
            item {
                InfoCard(
                    modifier = Modifier.padding(horizontal = Spaces.medium),
                    title = UiText.StringResources(AppStrings.Title.Attention),
                    body = UiText.StringResources(AppStrings.Body.Reset),
                )
            }

            item {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .selectableGroup(),
                ) {
                    HNListHeader(
                        modifier =
                            Modifier
                                .padding(horizontal = Spaces.medium)
                                .padding(bottom = Spaces.small),
                        title = stringResource(id = AppStrings.Subtitle.SelectActions),
                    )

                    HNCheckboxListItem(
                        headlineContent = {
                            Text(stringResource(id = AppStrings.Checkbox.ClearDatabaseTitle))
                        },
                        supportingContent = {
                            Text(stringResource(id = AppStrings.Checkbox.ClearDatabaseSubtitle))
                        },
                        checked = uiState.isResetDatabase,
                        onClick = onToggleResetDatabase,
                    )

                    HNCheckboxListItem(
                        headlineContent = {
                            Text(stringResource(id = AppStrings.Checkbox.ResetSettingsTitle))
                        },
                        supportingContent = {
                            Text(stringResource(id = AppStrings.Checkbox.ResetSettingsSubtitle))
                        },
                        checked = uiState.isResetSettings,
                        onClick = onToggleResetSettings,
                    )
                }
            }
        }
    }
}
