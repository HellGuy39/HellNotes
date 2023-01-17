package com.hellguy39.hellnotes.feature.about_app.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreenContent(
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = HellNotesIcons.StickyNote),
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
                        text = "Untitled",//ApplicationBuffer.getVersionName(),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                },
                onClick = {}
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
        ) {
            Text(
                text = "HellGuy39\n© 2022",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}