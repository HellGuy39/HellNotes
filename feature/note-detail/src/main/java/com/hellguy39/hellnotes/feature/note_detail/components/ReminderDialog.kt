package com.hellguy39.hellnotes.feature.note_detail.components

import android.Manifest
import android.os.Build
import androidx.compose.animation.Crossfade
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
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.CustomDialogState
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.maxkeppeker.sheets.core.models.base.SheetState
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
    state: CustomDialogState,
    selection: ReminderDialogSelection
) {
    var message by remember { mutableStateOf("") }
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.NOON) }

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

//    val dialogTitle = if (isEditMode)
//        stringResource(id = HellNotesStrings.Title.EditRemind)
//    else
//        stringResource(id = HellNotesStrings.Title.NewRemind)

    CustomDialog(
        showDialog = state.visible,
        onClose = { state.dismiss() },
        title =  stringResource(id = HellNotesStrings.Title.NewRemind),
        limitMinHeight = false,
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

            Crossfade(targetState = notificationPermissionState.status.isGranted) { isPermissionGranted ->
                if (!isPermissionGranted) {
                    ReminderRequestPermission(
                        innerPadding = innerPadding,
                        message = textToShow,
                        onRequest = {
                            notificationPermissionState.launchPermissionRequest()
                        }
                    )
                } else {
                    ReminderDialogContent(
                        innerPadding = innerPadding,
                        calendarState = calendarState,
                        clockState = clockState,
                        message = message,
                        onMessageUpdate = { message = it },
                        state = state,
                        selection = selection,
                        pickedDate = pickedDate,
                        pickedTime = pickedTime,
                        formattedTime = formattedTime,
                        formattedDate = formattedDate,
                    )
                }
            }

        } else {
            ReminderDialogContent(
                innerPadding = innerPadding,
                calendarState = calendarState,
                clockState = clockState,
                message = message,
                onMessageUpdate = { message = it },
                state = state,
                selection = selection,
                pickedDate = pickedDate,
                pickedTime = pickedTime,
                formattedDate = formattedDate,
                formattedTime = formattedTime,
            )
        }
    }
}

data class ReminderDialogSelection(
    val note: Note,
    val onCreateRemind: (remind: Remind) -> Unit,
    val onDeleteRemind: (remind: Remind) -> Unit,
    val onUpdateRemind: (remind: Remind) -> Unit
)

@Composable
fun ReminderRequestPermission(
    innerPadding: PaddingValues,
    message: String,
    onRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
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
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onRequest() }
        ) {
            Text(text = stringResource(id = HellNotesStrings.Button.RequestPermission))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDialogContent(
    innerPadding: PaddingValues,
    pickedDate: LocalDate,
    pickedTime: LocalTime,
    formattedDate: String,
    formattedTime: String,
    calendarState: SheetState,
    clockState: SheetState,
    state: CustomDialogState,
    selection: ReminderDialogSelection,
    message: String,
    onMessageUpdate: (newText: String) -> Unit,
) {
    val date = DateHelper(LocalContext.current)
        .dateToEpochMillis(pickedTime, pickedDate)
    
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
            IconButton(onClick = { calendarState.show() }) {
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
            IconButton(onClick = { clockState.show() }) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Edit),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Edit)
                )
            }
        }
        OutlinedTextField(
            value = message,
            onValueChange = { onMessageUpdate(it) },
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
        //if (!isEditMode) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        selection.onCreateRemind(
                            Remind(
                                noteId = selection.note.id ?: -1,
                                message = message,
                                triggerDate = date
                            )
                        )
                        state.dismiss()
                    },
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Create),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
//        } else {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.End
//            ) {
//                TextButton(
//                    onClick = {
//                        selection.editableRemind?.let {
//                            selection.onDeleteRemind(it)
//                        }
//                        state.dismiss()
//                    },
//                ) {
//                    Text(
//                        text = stringResource(id = HellNotesStrings.Button.Delete),
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//                Button(
//                    modifier = Modifier.padding(start = 8.dp),
//                    onClick = {
//                        selection.editableRemind?.let {
//                            selection.onUpdateRemind(
//                                Remind(
//                                    id = it.id,
//                                    noteId = it.noteId,
//                                    message = message,
//                                    triggerDate = date
//                                )
//                            )
//                        }
//                        state.dismiss()
//                    },
//                ) {
//                    Text(
//                        text = stringResource(id = HellNotesStrings.Button.Edit),
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//            }
//        }
    }
}