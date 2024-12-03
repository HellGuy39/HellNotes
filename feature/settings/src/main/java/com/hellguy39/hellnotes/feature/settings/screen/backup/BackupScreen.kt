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
package com.hellguy39.hellnotes.feature.settings.screen.backup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.components.cards.InfoCard
import com.hellguy39.hellnotes.core.ui.components.items.HNListHeader
import com.hellguy39.hellnotes.core.ui.components.items.HNSwitchListItem
import com.hellguy39.hellnotes.core.ui.components.layout.HNScaffold
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarController
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarEvent
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: BackupUiState,
    onBackupClick: () -> Unit,
    onRestoreClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(key1 = uiState.message) {
        uiState.message.let { message ->
            if (message !is UiText.Empty) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        text = message
                    )
                )
            }
        }
    }

    HNScaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                HNLargeTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onNavigationButtonClick = onNavigationButtonClick,
                    title = stringResource(id = AppStrings.Title.Backup),
                )
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier =
                    Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = Spaces.medium, vertical = Spaces.small),
                horizontalArrangement = Arrangement.spacedBy(Spaces.medium),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    modifier = Modifier,
                    onClick = onRestoreClick,
                ) {
                    Text(text = stringResource(id = AppStrings.Button.Restore))
                }
                Button(
                    modifier = Modifier,
                    onClick = onBackupClick,
                ) {
                    Text(text = stringResource(id = AppStrings.Button.Create))
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement =
                Arrangement.spacedBy(
                    space = Spaces.large,
                    alignment = Alignment.Top,
                ),
            contentPadding =
                PaddingValues(
                    top = innerPadding.calculateTopPadding() + Spaces.medium,
                    bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
                ),
        ) {
            item {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spaces.medium),
                ) {
                    Text(
                        text =
                            stringResource(
                                id = AppStrings.Subtitle.LastCopy,
                                if (uiState.lastBackupDate == 0L) {
                                    stringResource(id = AppStrings.Value.Never)
                                } else {
                                    DateTimeUtils.formatEpochMillis(uiState.lastBackupDate, DateTimeUtils.FULL_DATE_PATTERN)
                                },
                            ),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            item {
                InfoCard(
                    modifier = Modifier.padding(horizontal = Spaces.medium),
                    title = UiText.StringResources(AppStrings.Title.Attention),
                    body = UiText.StringResources(AppStrings.Body.Backup),
                )
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    HNListHeader(
                        modifier =
                            Modifier
                                .padding(horizontal = Spaces.medium)
                                .padding(bottom = Spaces.small),
                        title = stringResource(id = AppStrings.Subtitle.Options),
                    )
                    HNSwitchListItem(
                        headlineContent = { Text(stringResource(id = AppStrings.Switch.AutoBackupTitle)) },
                        supportingContent = { Text(stringResource(id = AppStrings.Switch.AutoBackupSubtitle)) },
                        checked = false,
                        enabled = false,
                        onClick = {}
                    )
                }
            }
        }
    }
}
