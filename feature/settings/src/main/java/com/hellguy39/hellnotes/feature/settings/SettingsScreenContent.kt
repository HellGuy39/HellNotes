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
package com.hellguy39.hellnotes.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.asDisplayableString
import com.hellguy39.hellnotes.core.ui.components.items.HNListHeader
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.components.items.HNSwitchItem
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    selection: SettingsScreenSelection,
    uiState: SettingsUiState,
) {
    val listItemModifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)

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
                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onLanguage,
                    title = stringResource(id = AppStrings.Setting.Language),
                    subtitle = uiState.language.asDisplayableString(),
                )

                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onBackup,
                    title = stringResource(id = AppStrings.MenuItem.Backup),
                    subtitle =
                        stringResource(
                            id = AppStrings.Subtitle.LastCopy,
                            if (uiState.lastBackupDate == 0L) {
                                stringResource(id = AppStrings.Value.Never)
                            } else {
                                DateTimeUtils.formatEpochMillis(uiState.lastBackupDate, DateTimeUtils.FULL_DATE_PATTERN)
                            },
                        ),
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
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = AppStrings.Label.Security),
                    icon = painterResource(id = AppIcons.SecurityVerified),
                )

                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onLockScreen,
                    title = stringResource(id = AppStrings.Setting.ScreenLock),
                    subtitle = uiState.securityState.lockType.asDisplayableString(),
                )

                val isChecked = uiState.securityState.isUseBiometricData

                HNSwitchItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(Spaces.medium),
                    title = stringResource(id = AppStrings.Switch.UseBiometricTitle),
                    checked = isChecked,
                    enabled = uiState.isBioAuthAvailable,
                    onClick = { selection.onUseBiometric(!isChecked) },
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
                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onNoteSwipeEdit,
                    title = stringResource(id = AppStrings.Setting.NoteSwipes),
                    subtitle = stringResource(AppStrings.Subtitle.enabled(uiState.noteSwipesState.enabled)),
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
                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onNoteStyleEdit,
                    title = stringResource(id = AppStrings.Setting.NoteStyle),
                    subtitle = uiState.noteStyle.asDisplayableString(),
                )
            }
        }
    }
}

data class SettingsScreenSelection(
    val onNoteSwipeEdit: () -> Unit,
    val onNoteStyleEdit: () -> Unit,
    val onLockScreen: () -> Unit,
    val onLanguage: () -> Unit,
    val onBackup: () -> Unit,
    val onUseBiometric: (Boolean) -> Unit,
)
