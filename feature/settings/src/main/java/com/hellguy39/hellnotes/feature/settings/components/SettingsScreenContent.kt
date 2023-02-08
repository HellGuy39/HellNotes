package com.hellguy39.hellnotes.feature.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.feature.settings.events.SettingsEvents
import com.hellguy39.hellnotes.feature.settings.util.Language

@Composable
fun SettingsScreenContent(
    innerPadding: PaddingValues,
    languageDialogEvents: LanguageDialogEvents,
    appSettings: AppSettings,
    settingsEvents: SettingsEvents,
    isBioAuthAvailable: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        item {
            SettingsPartitionCard(
                heroIcon = painterResource(id = HellNotesIcons.SecurityVerified),
                title = stringResource(id = HellNotesStrings.Label.Security),
                onClick = {  }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Setting.AppLock),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (appSettings.isAppLocked) {
                        Row {
                            Button(
                                onClick = { settingsEvents.setupPIN() }
                            ) {
                                Text(text = stringResource(id = HellNotesStrings.Button.EditPin))
                            }
                            Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))
                            FilledTonalIconButton(
                                onClick = { settingsEvents.deletePIN() }
                            ) {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.Cancel),
                                    contentDescription = null
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick = { settingsEvents.setupPIN() }
                        ) {
                            Text(text = stringResource(id = HellNotesStrings.Button.SetupPin))
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Setting.UseBiometric),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Switch(
                        checked = appSettings.isBiometricSetup,
                        onCheckedChange = { settingsEvents.setUseBiometric(it) },
                        enabled = appSettings.isAppLocked && isBioAuthAvailable
                    )
                }
            }
        }
        item {
            SettingsPartitionCard(
                heroIcon = painterResource(id = HellNotesIcons.Language),
                title = stringResource(id = HellNotesStrings.Label.Language),
                onClick = { languageDialogEvents.show() }
            ) {
                Text(
                    text = Language.getFullName(
                        code = languageDialogEvents.getCurrentLanCode()
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPartitionCard(
    heroIcon: Painter,
    title: String = "",
    onClick: () -> Unit,
    content: @Composable (() -> Unit)
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = heroIcon,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.size(height = 0.dp, width = 0.dp))
            Column(
                modifier = Modifier.padding(start = 32.dp, top = 8.dp, bottom = 8.dp)
            ) {
                content()
            }
        }
    }
}