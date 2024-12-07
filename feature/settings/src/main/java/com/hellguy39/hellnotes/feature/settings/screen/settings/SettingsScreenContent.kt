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
package com.hellguy39.hellnotes.feature.settings.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.asDisplayableString
import com.hellguy39.hellnotes.core.ui.components.items.HNListHeader
import com.hellguy39.hellnotes.core.ui.components.items.HNNavigateListItem
import com.hellguy39.hellnotes.core.ui.components.items.HNSwitchListItem
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.settings.BuildConfig

@Composable
internal fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    uiState: SettingsUiState,
    onNoteSwipeEdit: () -> Unit,
    onNoteStyleEdit: () -> Unit,
    onLockScreen: () -> Unit,
    onLanguage: () -> Unit,
    onBackup: () -> Unit,
    onTheme: () -> Unit,
    onColorMode: () -> Unit,
    onUseBiometric: (Boolean) -> Unit,
) {
    LazyColumn(
        contentPadding = innerPadding,
        modifier = modifier,
    ) {
        item {
            Column(
                modifier =
                    Modifier
                        .padding(vertical = 8.dp),
            ) {
                HNListHeader(
                    modifier =
                        Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = AppStrings.Label.General),
                    icon = painterResource(id = AppIcons.Settings),
                )
                HNNavigateListItem(
                    onClick = onLanguage,
                    headlineContent = { Text(stringResource(id = AppStrings.Setting.Language)) },
                    supportingContent = { Text(uiState.language.asDisplayableString()) },
                )

                HNNavigateListItem(
                    onClick = onBackup,
                    headlineContent = {
                        Text(stringResource(id = AppStrings.MenuItem.Backup))
                    },
                    supportingContent = {
                        Text(
                            stringResource(
                                id = AppStrings.Subtitle.LastCopy,
                                if (uiState.lastBackupDate == 0L) {
                                    stringResource(id = AppStrings.Value.Never)
                                } else {
                                    DateTimeUtils.formatEpochMillis(uiState.lastBackupDate, DateTimeUtils.FULL_DATE_PATTERN)
                                },
                            ),
                        )
                    },
                )

                if (BuildConfig.DEBUG) {
                    HNNavigateListItem(
                        onClick = {},
                        headlineContent = {
                            Text("Storage")
                        },
                        supportingContent = {
                            Text("2.6 MB used")
                        },
                    )

                    HNNavigateListItem(
                        onClick = {},
                        headlineContent = {
                            Text("Activity Insights")
                        },
                        supportingContent = {
                            Text("Your in app activity")
                        },
                    )
                }
            }
        }
        item {
            Column(
                modifier =
                    Modifier
                        .padding(vertical = 8.dp),
            ) {
                HNListHeader(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = AppStrings.Label.Security),
                    icon = painterResource(id = AppIcons.SecurityVerified),
                )

                HNNavigateListItem(
                    onClick = onLockScreen,
                    headlineContent = {
                        Text(stringResource(id = AppStrings.Setting.ScreenLock))
                    },
                    supportingContent = {
                        Text(uiState.securityState.lockType.asDisplayableString())
                    }
                )

                val isChecked = uiState.securityState.isUseBiometricData

                HNSwitchListItem(
                    headlineContent = { Text(stringResource(id = AppStrings.Switch.UseBiometricTitle)) },
                    checked = isChecked,
                    enabled = uiState.isBioAuthAvailable,
                    onClick = { onUseBiometric(!isChecked) },
                )
            }
        }
        item {
            Column(
                modifier =
                    Modifier
                        .padding(vertical = 8.dp),
            ) {
                HNListHeader(
                    modifier =
                        Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = AppStrings.Label.Gestures),
                    icon = painterResource(id = AppIcons.Gesture),
                )
                HNNavigateListItem(
                    onClick = onNoteSwipeEdit,
                    headlineContent = {
                        Text(stringResource(id = AppStrings.Setting.NoteSwipes))
                    },
                    supportingContent = {
                        Text(stringResource(AppStrings.Subtitle.enabled(uiState.noteSwipesState.enabled)))
                    }
                )
            }
        }
        item {
            Column(
                modifier =
                    Modifier
                        .padding(vertical = 8.dp),
            ) {
                HNListHeader(
                    modifier =
                        Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = AppStrings.Label.Appearance),
                    icon = painterResource(id = AppIcons.Palette),
                )
                if (BuildConfig.DEBUG) {
                    HNNavigateListItem(
                        onClick = onTheme,
                        headlineContent = {
                            Text("Theme")
                        },
                        supportingContent = {
                            Text("Material You")
                        },
                    )
                    HNNavigateListItem(
                        onClick = onColorMode,
                        headlineContent = {
                            Text("Color mode")
                        },
                        supportingContent = {
                            Text("Dark")
                        },
                    )
                }
                HNNavigateListItem(
                    onClick = onNoteStyleEdit,
                    headlineContent = {
                        Text(stringResource(id = AppStrings.Setting.NoteStyle))
                    },
                    supportingContent = {
                        Text(uiState.noteStyle.asDisplayableString())
                    },
                )
            }
        }
    }
}
