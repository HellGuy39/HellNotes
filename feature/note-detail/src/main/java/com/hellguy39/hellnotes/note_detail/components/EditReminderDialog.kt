package com.hellguy39.hellnotes.note_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.common.date.DateHelper
import com.hellguy39.hellnotes.components.CustomDialog
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.note_detail.events.EditReminderDialogEvents
import com.hellguy39.hellnotes.note_detail.events.ReminderDialogEvents
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReminderDialog(
    note: Note,
    isShowDialog: Boolean,
    events: EditReminderDialogEvents,
    remind: Remind?
) {
    var message by remember { mutableStateOf("") }
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.NOON) }

    if (remind != null &&
        message == "" &&
        pickedTime == LocalTime.NOON &&
        pickedDate == LocalDate.now()) {

        message = remind.message
        pickedTime = DateHelper(LocalContext.current)
            .epochMillisToLocalTime(remind.triggerDate)
        pickedDate = DateHelper(LocalContext.current)
            .epochMillisToLocalDate(remind.triggerDate)
    }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern(DateHelper.DATE_SHORT_PATTERN)
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern(DateHelper.TIME_PATTERN)
                .format(pickedTime)
        }
    }
    val date = DateHelper(LocalContext.current)
        .dateToEpochMillis(pickedTime, pickedDate)

    val calendarState = rememberSheetState()
    val clockState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true
        ),
        selection = CalendarSelection.Date {
            pickedDate = it
        }
    )

    ClockDialog(
        state = clockState,
        config = ClockConfig(
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            pickedTime = LocalTime.of(hours, minutes)
        }
    )

    CustomDialog(
        showDialog = isShowDialog,
        onClose = { events.dismiss() },
        limitMaxHeight = false,
        applyBottomSpace = false,
        title = stringResource(id = HellNotesStrings.Text.EditRemind)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
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
                IconButton(
                    onClick = {
                        calendarState.show()
                    }
                ) {
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
                IconButton(onClick = {
                    clockState.show()
                }) {
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
                        text = stringResource(id = HellNotesStrings.Hint.Message),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        if (remind != null)
                            events.deleteRemind(remind)

                        events.dismiss()
                    },
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Text.Delete),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {
                        events.updateRemind(
                            Remind(
                                id = remind?.id,
                                noteId = remind?.noteId ?: -1,
                                message = message,
                                triggerDate = date
                            )
                        )
                        events.dismiss()
                    },
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Edit),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}