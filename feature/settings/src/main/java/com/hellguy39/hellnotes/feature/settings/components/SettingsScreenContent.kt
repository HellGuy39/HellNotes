package com.hellguy39.hellnotes.feature.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Language
import com.hellguy39.hellnotes.core.model.local.datastore.ThemeState
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.components.items.HNListHeader
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.components.items.HNSwitchItem
import com.hellguy39.hellnotes.core.ui.getDisplayName
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
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
                    subtitle =  Language.from(uiState.lanCode).getDisplayName(),
                )

                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onBackup,
                    title = stringResource(id = HellNotesStrings.MenuItem.Backup),
                    subtitle = stringResource(
                        id = HellNotesStrings.Subtitle.LastCopy,
                        if (uiState.lastBackupDate == 0L) stringResource(id = HellNotesStrings.Value.Never) else DateTimeUtils.formatBest(uiState.lastBackupDate)
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
                    subtitle = uiState.securityState.lockType.getDisplayName(),
                )

                val isChecked = uiState.securityState.isUseBiometricData

                HNSwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Switch.UseBiometric),
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
                    subtitle = if (uiState.noteSwipesState.enabled)
                        stringResource(id = HellNotesStrings.Subtitle.Enabled)
                    else
                        stringResource(id = HellNotesStrings.Subtitle.Disabled),
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
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                            .clip(shape = RoundedCornerShape(24.dp)),
                        color = MaterialTheme.colorScheme.onSurface
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(96.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .clickable(
                                        enabled = true,
                                        onClick = { selection.onThemeToggle(ThemeState.Light) }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "Light",
                                    color = MaterialTheme.colorScheme.surface,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .width(96.dp)
                                    .clip(shape = RoundedCornerShape(24.dp))
                                    .clickable(
                                        enabled = true,
                                        onClick = { selection.onThemeToggle(ThemeState.Dark) }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "Dark",
                                    color = MaterialTheme.colorScheme.surface,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .width(96.dp)
                                    .clip(shape = RoundedCornerShape(24.dp))
                                    .background(color = MaterialTheme.colorScheme.surface)
                                    .clickable(
                                        enabled = true,
                                        onClick = { selection.onThemeToggle(ThemeState.System) }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "System",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }

                val isChecked = true

                HNSwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    title = "Material You",
                    checked = isChecked,
                    enabled = true,
                    onClick = {  },
                )

                HNListItem(
                    modifier = listItemModifier,
                    onClick = selection.onNoteStyleEdit,
                    title = stringResource(id = HellNotesStrings.Setting.NoteStyle),
                    subtitle = uiState.noteStyle.getDisplayName(),
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
    val onThemeToggle: (ThemeState) -> Unit,
    val onMaterialYouEnabled: (Boolean) -> Unit
)