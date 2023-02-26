package com.hellguy39.hellnotes.feature.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.Language
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.CustomDivider
import com.hellguy39.hellnotes.core.ui.components.CustomSwitch
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
                SectionHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.General),
                    icon = painterResource(id = HellNotesIcons.Settings)
                )
                Option(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clickable { selection.onLanguage() },
                    title = stringResource(id = HellNotesStrings.Setting.Language),
                    value =  Language.from(uiState.lanCode).getDisplayName(),
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                SectionHeader(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Security),
                    icon = painterResource(id = HellNotesIcons.SecurityVerified)
                )
                Option(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clickable { selection.onLockScreen() },
                    title = stringResource(id = HellNotesStrings.Setting.ScreenLock),
                    value = uiState.appSettings.appLockType.getDisplayName(),
                )
                CustomSwitch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Setting.UseBiometric),
                    checked = uiState.appSettings.isUseBiometricData,
                    enabled = uiState.isBioAuthAvailable,
                    onCheckedChange = { checked ->
                        selection.onUseBiometric(checked)
                    }
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                SectionHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Gestures),
                    icon = painterResource(id = HellNotesIcons.Gesture)
                )
                Option(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clickable { selection.onNoteSwipeEdit() },
                    title = stringResource(id = HellNotesStrings.Setting.NoteSwipes),
                    value = "Enabled",
                )
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                SectionHeader(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Personalization),
                    icon = painterResource(id = HellNotesIcons.Palette)
                )
                Option(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clickable { selection.onNoteStyleEdit() },
                    title = stringResource(id = HellNotesStrings.Setting.NoteStyle),
                    value = uiState.noteStyle.getDisplayName(),
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String = "",
    icon: Painter? = null,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = icon,
                contentDescription = null,
                tint = color
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                color = color
            )
        )   
    }
}

@Composable
fun Option(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = "",
    horizontalPadding: Dp = 16.dp,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = horizontalPadding),
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        if (value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .alpha(UiDefaults.Alpha.Accented),
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
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