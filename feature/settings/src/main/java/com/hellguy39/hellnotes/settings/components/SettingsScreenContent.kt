package com.hellguy39.hellnotes.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.resources.HellNotesStrings
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.settings.util.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    innerPadding: PaddingValues,
    languageDialogEvents: LanguageDialogEvents
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                onClick = {
                    languageDialogEvents.show()
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Text.Language),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = Language.getFullName(
                            code = languageDialogEvents.getCurrentLanCode()
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}