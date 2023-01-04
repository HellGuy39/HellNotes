package com.hellguy39.hellnotes.note_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.CustomDialog
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.note_detail.events.ShareDialogEvents
import com.hellguy39.hellnotes.resources.HellNotesStrings

@Composable
fun ShareDialog(
    isShowDialog: Boolean,
    events: ShareDialogEvents,
    note: Note
) {
    CustomDialog(
        showDialog = isShowDialog, 
        onClose = { events.dismiss() },
        title = stringResource(id = HellNotesStrings.Text.ShareAs)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .clickable {
                            events.shareAsTxtFile(note = note)
                            events.dismiss()
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(id = HellNotesStrings.Text.TxtFile),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .clickable {
                            events.shareAsPlainText(note = note)
                            events.dismiss()
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(id = HellNotesStrings.Text.PlainText),
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