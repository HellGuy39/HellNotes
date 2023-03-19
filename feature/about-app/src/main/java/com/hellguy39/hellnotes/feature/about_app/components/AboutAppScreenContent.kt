package com.hellguy39.hellnotes.feature.about_app.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.ProjectInfoProvider
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons


@Composable
fun AboutAppScreenContent(
    innerPadding: PaddingValues,
    onEasterEgg: () -> Unit
) {
    var easterEggCounter by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = HellNotesIcons.DoubleStickyNote),
                contentDescription = null,
                modifier = Modifier
                    .width(128.dp)
                    .height(128.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "HellNotes",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            AssistChip(
                label = {
                    Text(
                        text = ProjectInfoProvider.appConfig.versionName,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                },
                onClick = {
                    if (easterEggCounter >= 5) {
                        easterEggCounter = 0
                        onEasterEgg()
                    } else {
                        easterEggCounter++
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        ) {
            Text(
                text = "HellGuy39",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "© 2023",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}