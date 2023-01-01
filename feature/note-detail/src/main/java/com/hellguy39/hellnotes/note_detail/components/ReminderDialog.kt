package com.hellguy39.hellnotes.note_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.CustomDialog
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.note_detail.events.ReminderDialogEvents
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDialog(
    note: Note,
    isShowDialog: Boolean,
    events: ReminderDialogEvents
) {
    var isRepeat by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    CustomDialog(
        showDialog = isShowDialog,
        onClose = { events.dismiss() },
        title = stringResource(id = HellNotesStrings.Text.NewRemind)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Edit),
                        contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Edit)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Edit),
                        contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Edit)
                    )
                }
            }
            OutlinedTextField(
                value = message,
                onValueChange = { newText -> message = newText },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Message",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Repeat",
                    style = MaterialTheme.typography.bodyMedium
                )
                Switch(
                    checked = isRepeat,
                    onCheckedChange = { isRepeat = !isRepeat},
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        val date = Date(
                            pickedTime.atDate(pickedDate).toInstant(ZoneOffset.UTC)
                                .toEpochMilli()
                        )
                        events.onCreateRemind(
                            Remind(
                                noteId = note.id ?: -1,
                                message = message,
                                triggerDate = date.time
                            )
                        )
                        events.dismiss()
                    },
                ) {
                    Text(
                        text = "Create",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}