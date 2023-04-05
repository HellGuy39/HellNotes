package com.hellguy39.hellnotes.feature.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.Language
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.CustomSwitch
import com.hellguy39.hellnotes.core.ui.components.items.ItemHeader
import com.hellguy39.hellnotes.core.ui.components.items.SelectionItemDefault
import com.hellguy39.hellnotes.core.ui.components.items.SelectionItemLarge
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
    LazyColumn(
        contentPadding = innerPadding,
        modifier = modifier
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                ItemHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.General),
                    icon = painterResource(id = HellNotesIcons.Settings)
                )
                SelectionItemLarge(
                    onClick = selection.onLanguage,
                    title = stringResource(id = HellNotesStrings.Setting.Language),
                    subtitle =  Language.from(uiState.lanCode).getDisplayName(),
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                ItemHeader(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Security),
                    icon = painterResource(id = HellNotesIcons.SecurityVerified)
                )
                SelectionItemLarge(
                    onClick = selection.onLockScreen,
                    title = stringResource(id = HellNotesStrings.Setting.ScreenLock),
                    subtitle = uiState.securityState.lockType.getDisplayName(),
                )

                val isChecked = uiState.securityState.isUseBiometricData

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(UiDefaults.ListItem.LargeHeight)
                        .selectable(
                            selected = uiState.noteStyle == NoteStyle.Elevated,
                            onClick = { selection.onUseBiometric(!isChecked) },
                            role = Role.Switch
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CustomSwitch(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        title = stringResource(id = HellNotesStrings.Switch.UseBiometric),
                        checked = isChecked,
                        enabled = uiState.isBioAuthAvailable,
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                ItemHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Gestures),
                    icon = painterResource(id = HellNotesIcons.Gesture)
                )
                SelectionItemLarge(
                    onClick = selection.onNoteSwipeEdit,
                    title = stringResource(id = HellNotesStrings.Setting.NoteSwipes),
                    subtitle = if (uiState.noteSwipesState.enabled)
                        stringResource(id = HellNotesStrings.Label.Enabled)
                    else
                        stringResource(id = HellNotesStrings.Label.Disabled),
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                ItemHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Appearance),
                    icon = painterResource(id = HellNotesIcons.Palette)
                )
                SelectionItemLarge(
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
    val onUseBiometric: (Boolean) -> Unit
)