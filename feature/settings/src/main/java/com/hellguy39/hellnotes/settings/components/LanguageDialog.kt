package com.hellguy39.hellnotes.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.CustomDialog
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.settings.util.Language

@Composable
fun LanguageDialog(
    isShowDialog: Boolean,
    events: LanguageDialogEvents
) {
    CustomDialog(
        showDialog = isShowDialog,
        onClose = { events.dismiss() },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            items(Language.languageCodes) {
                Row(
                    modifier = Modifier
                        .clickable {
                            events.dismiss()
                            events.onLanguageSelected(it)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val isSelected = it == events.getCurrentLanCode()

                    Spacer(modifier = Modifier.width(16.dp))

                    if (isSelected) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Done),
                            contentDescription = null
                        )
                    } else {
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                    Text(
                        text = Language.getFullName(code = it),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

