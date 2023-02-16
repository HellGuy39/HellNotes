package com.hellguy39.hellnotes.feature.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    innerPadding: PaddingValues,
    selection: SettingsScreenSelection,
    uiState: SettingsUiState
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            //.padding(horizontal = 16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.padding(
                    vertical = 12.dp
                )
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    text = stringResource(id = HellNotesStrings.Label.Security),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selection.onLockScreen()
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 2.dp)
                            .padding(horizontal = 16.dp),
                        text = stringResource(id = HellNotesStrings.Setting.ScreenLock),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp)
                            .alpha(UiDefaults.Alpha.Accented),
                        text = uiState.appSettings.appLockType.getDisplayName(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val switchTextModifier = if (uiState.isBioAuthAvailable)
                        Modifier
                    else
                        Modifier.alpha(UiDefaults.Alpha.Emphasize)

                    Text(
                        modifier = switchTextModifier,
                        text = stringResource(id = HellNotesStrings.Setting.UseBiometric),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = uiState.appSettings.isUseBiometricData,
                        onCheckedChange = { selection.onUseBiometric(!uiState.appSettings.isUseBiometricData) },
                        enabled = uiState.isBioAuthAvailable,
                        thumbContent = if (uiState.appSettings.isUseBiometricData) {
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
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    text = stringResource(id = HellNotesStrings.Label.Language),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selection.onLanguage() }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 2.dp)
                            .padding(horizontal = 16.dp),
                        text = stringResource(id = HellNotesStrings.Setting.ChangeLanguage),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp)
                            .alpha(UiDefaults.Alpha.Accented),
                        text = Language.from(uiState.lanCode).getDisplayName(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

data class SettingsScreenSelection(
    val onLockScreen: () -> Unit,
    val onLanguage: () -> Unit,
    val onUseBiometric: (Boolean) -> Unit
)