package com.hellguy39.hellnotes.note_detail.components

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.hellguy39.hellnotes.common.date.DateHelper
import com.hellguy39.hellnotes.components.CustomDialog
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ReminderDialog(
    note: Note,
    isShowDialog: Boolean,
    events: ReminderDialogEvents
) {
    var isRepeat by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val calendarState = rememberSheetState()
    val clockState = rememberSheetState()

    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.NOON) }

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
                .ofPattern("HH:mm")
                .format(pickedTime)
        }
    }
    val date = DateHelper(LocalContext.current)
        .dateToEpochMillis(pickedTime, pickedDate)

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
        title = stringResource(id = HellNotesStrings.Text.NewRemind),
        limitMaxHeight = false,
        applyBottomSpace = false
    ) { innerPadding ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val notificationPermissionState = rememberPermissionState(
                Manifest.permission.POST_NOTIFICATIONS
            )

            val textToShow = if (notificationPermissionState.status.shouldShowRationale) {
                stringResource(id = HellNotesStrings.Text.NotificationPermissionRationale)
            } else {
                stringResource(id = HellNotesStrings.Text.NotificationPermissionDefault)
            }

            if (!notificationPermissionState.status.isGranted) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Notifications),
                        contentDescription = null,
                        modifier = Modifier
                            .width(128.dp)
                            .height(128.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = textToShow,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { notificationPermissionState.launchPermissionRequest() }
                    ) {
                        Text(text = stringResource(id = HellNotesStrings.Button.RequestPermission))
                    }
                }
                return@CustomDialog
            }
        }

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
                        events.onCreateRemind(
                            Remind(
                                noteId = note.id ?: return@Button,
                                message = message,
                                triggerDate = date
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