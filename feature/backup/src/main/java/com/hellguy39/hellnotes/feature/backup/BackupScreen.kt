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
package com.hellguy39.hellnotes.feature.backup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.components.cards.InfoCard
import com.hellguy39.hellnotes.core.ui.components.items.HNListHeader
import com.hellguy39.hellnotes.core.ui.components.items.HNSwitchItem
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.components.snack.showDismissableSnackbar
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
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.message) {
        uiState.message.let { message ->
            if (message !is UiText.Empty) {
                snackbarHostState.showDismissableSnackbar(
                    scope = scope,
                    message = message.asString(context),
                    duration = SnackbarDuration.Long,
                )
            }
        }
    }

    Scaffold(
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
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
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
                    HNSwitchItem(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Spaces.medium, vertical = Spaces.medium),
                        title = stringResource(id = AppStrings.Switch.AutoBackupTitle),
                        subtitle = stringResource(id = AppStrings.Switch.AutoBackupSubtitle),
                        checked = false,
                        enabled = false,
                    )
                }
            }
        }
    }
}
