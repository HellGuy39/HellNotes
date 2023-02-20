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
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                SectionHeader(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Security),
                    icon = painterResource(id = HellNotesIcons.SecurityVerified)
                )
                Option(
                    title = stringResource(id = HellNotesStrings.Setting.ScreenLock),
                    value = uiState.appSettings.appLockType.getDisplayName(),
                    onClick = { selection.onLockScreen() }
                )
                CustomSwitch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    checked = uiState.appSettings.isUseBiometricData,
                    enabled = uiState.isBioAuthAvailable,
                    onCheckedChange = { checked ->
                        selection.onUseBiometric(checked)
                    }
                )
            }
        }
        item {
            CustomDivider(
                paddingValues = PaddingValues(horizontal = 16.dp),
                alpha = UiDefaults.Alpha.Inconspicuous,
                color = MaterialTheme.colorScheme.outline
            )
        }
        item {
            Column(
                modifier = Modifier.padding(
                    vertical = 12.dp
                )
            ) {
                SectionHeader(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    title = "Style",
                    icon = painterResource(id = HellNotesIcons.Palette)
                )
                Option(
                    title = "Theme",
                    value = "Default",
                    onClick = {}
                )
            }
        }
        item {
            CustomDivider(
                paddingValues = PaddingValues(horizontal = 16.dp),
                alpha = UiDefaults.Alpha.Inconspicuous,
                color = MaterialTheme.colorScheme.outline
            )
        }
        item {
            Column(
                modifier = Modifier.padding(
                    vertical = 12.dp
                )
            ) {
                SectionHeader(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    title = stringResource(id = HellNotesStrings.Label.Language),
                    icon = painterResource(id = HellNotesIcons.Language)
                )
                Option(
                    title = stringResource(id = HellNotesStrings.Setting.ChangeLanguage),
                    value =  Language.from(uiState.lanCode).getDisplayName(),
                    onClick = { selection.onLanguage() }
                )
            }
        }
    }
}

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val switchTextModifier = if (enabled)
            Modifier
        else
            Modifier.alpha(UiDefaults.Alpha.Emphasize)

        Text(
            modifier = switchTextModifier,
            text = stringResource(id = HellNotesStrings.Setting.UseBiometric),
            style = MaterialTheme.typography.bodyLarge
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            thumbContent = if (checked) {
                {
                    Icon(
                        painterResource(id = HellNotesIcons.Done),
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
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
    title: String = "",
    value: String = "",
    onClick: () -> Unit = {},
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 12.dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier
                .padding(top = verticalPadding)
                .padding(horizontal = horizontalPadding),
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .padding(bottom = verticalPadding)
                .alpha(UiDefaults.Alpha.Accented),
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

data class SettingsScreenSelection(
    val onLockScreen: () -> Unit,
    val onLanguage: () -> Unit,
    val onUseBiometric: (Boolean) -> Unit
)