package com.hellguy39.hellnotes.feature.settings.components

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
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.settings.SettingsUiState

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    selection: SettingsScreenSelection,
    uiState: SettingsUiState
) {
    val listItemModifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)

    LazyColumn(
        contentPadding = innerPadding,
        modifier = modifier
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                HNListHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.General),
                    icon = painterResource(id = HellNotesIcons.Settings)
                )
                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onLanguage,
                    title = stringResource(id = HellNotesStrings.Setting.Language),
                    subtitle =  uiState.language.asDisplayableString(),
                )

                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onBackup,
                    title = stringResource(id = HellNotesStrings.MenuItem.Backup),
                    subtitle = stringResource(
                        id = HellNotesStrings.Subtitle.LastCopy,
                        if (uiState.lastBackupDate == 0L)
                            stringResource(id = HellNotesStrings.Value.Never)
                        else
                            DateTimeUtils.formatEpochMillis(uiState.lastBackupDate, DateTimeUtils.FULL_DATE_PATTERN)
                    )
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                HNListHeader(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Security),
                    icon = painterResource(id = HellNotesIcons.SecurityVerified)
                )

                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onLockScreen,
                    title = stringResource(id = HellNotesStrings.Setting.ScreenLock),
                    subtitle = uiState.securityState.lockType.asDisplayableString(),
                )

                val isChecked = uiState.securityState.isUseBiometricData

                HNSwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spaces.medium),
                    title = stringResource(id = HellNotesStrings.Switch.UseBiometricTitle),
                    checked = isChecked,
                    enabled = uiState.isBioAuthAvailable,
                    onClick = { selection.onUseBiometric(!isChecked) },
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                HNListHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Gestures),
                    icon = painterResource(id = HellNotesIcons.Gesture)
                )
                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onNoteSwipeEdit,
                    title = stringResource(id = HellNotesStrings.Setting.NoteSwipes),
                    subtitle = stringResource(HellNotesStrings.Subtitle.enabled(uiState.noteSwipesState.enabled)),
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                HNListHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Appearance),
                    icon = painterResource(id = HellNotesIcons.Palette)
                )
                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onNoteStyleEdit,
                    title = stringResource(id = HellNotesStrings.Setting.NoteStyle),
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
    val onUseBiometric: (Boolean) -> Unit
)