package com.hellguy39.hellnotes.feature.note_detail.components

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
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.CustomDialogState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun ShareDialog(
    state: CustomDialogState,
    selection: ShareDialogSelection
) {
    CustomDialog(
        showDialog = state.visible,
        onClose = { state.dismiss() },
        title = stringResource(id = HellNotesStrings.Title.ShareAs)
    ) {
        LazyColumn(
            modifier = Modifier
        ) {
            item {
                Row(
                    modifier = Modifier
                        .clickable {
                            selection.shareAsTxtFile()
                            state.dismiss()
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(id = HellNotesStrings.MenuItem.TxtFile),
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
                            selection.shareAsPlainText()
                            state.dismiss()
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(id = HellNotesStrings.MenuItem.PlainText),
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

data class ShareDialogSelection(
    val shareAsTxtFile: () -> Unit,
    val shareAsPlainText: () -> Unit
)