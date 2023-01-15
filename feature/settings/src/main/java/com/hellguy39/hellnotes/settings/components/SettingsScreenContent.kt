package com.hellguy39.hellnotes.settings.components

import android.widget.Space
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
import com.hellguy39.hellnotes.model.AppSettings
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.settings.events.PINDialogEvents
import com.hellguy39.hellnotes.settings.events.SettingsEvents
import com.hellguy39.hellnotes.settings.util.Language

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
                title = "Security",
                onClick = {  }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "App Lock",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (appSettings.isPinSetup) {
                        Row {
                            Button(
                                onClick = { settingsEvents.setupPIN() }
                            ) {
                                Text(text = "Edit PIN")
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
                            Text(text = "Setup PIN")
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Use biometric",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Switch(
                        checked = appSettings.isUseBiometric,
                        onCheckedChange = { settingsEvents.setUseBiometric(it) },
                        enabled = appSettings.isPinSetup && isBioAuthAvailable
                    )
                }
            }
        }
        item {
            SettingsPartitionCard(
                heroIcon = painterResource(id = HellNotesIcons.Language),
                title = stringResource(id = HellNotesStrings.Text.Language),
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
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.size(height = 0.dp, width = 0.dp))
            content()
        }
    }
}