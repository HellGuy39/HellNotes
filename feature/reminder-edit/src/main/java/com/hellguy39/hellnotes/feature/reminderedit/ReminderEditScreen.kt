/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.reminderedit

import android.Manifest
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.asDisplayableString
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.items.HNRadioButtonItem
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ReminderEditScreen(
    onNavigationBack: () -> Unit,
    uiState: ReminderEditUiState,
    selection: ReminderEditScreenSelection,
    snackbarHostState: SnackbarHostState,
) {
    val calendarState = rememberSheetState()
    val clockState = rememberSheetState()
    val repeatDialogState = rememberDialogState()

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)

    val notificationPermissionState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            null
        }

    CalendarDialog(
        state = calendarState,
        config =
            CalendarConfig(
                yearSelection = true,
                monthSelection = true,
            ),
        selection =
            CalendarSelection.Date(
                selectedDate = uiState.localDateTime.toLocalDate(),
                onSelectDate = { localDate ->
                    selection.onDateUpdate(localDate)
                },
            ),
    )

    ClockDialog(
        state = clockState,
        config =
            ClockConfig(
                defaultTime = uiState.localDateTime.toLocalTime(),
                is24HourFormat = true,
            ),
        selection =
            ClockSelection.HoursMinutes(
                onPositiveClick = { hours, minutes ->
                    selection.onTimeUpdate(LocalTime.of(hours, minutes))
                },
            ),
    )

    CustomDialog(
        state = repeatDialogState,
        title = stringResource(id = AppStrings.Title.Repeat),
        onCancel = { repeatDialogState.dismiss() },
        limitMaxHeight = false,
        content = {
            val repeats = listOf(Repeat.DoesNotRepeat, Repeat.Daily, Repeat.Weekly, Repeat.Monthly)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                items(repeats) { repeat ->
                    HNRadioButtonItem(
                        modifier =
                            Modifier.fillMaxWidth()
                                .padding(16.dp),
                        title = repeat.asDisplayableString(),
                        isSelected = repeat == uiState.repeat,
                        onClick = {
                            selection.onRepeatUpdate(repeat)
                            repeatDialogState.dismiss()
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        },
    )

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationBack,
                title =
                    if (uiState.isEdit) {
                        stringResource(id = AppStrings.Title.EditReminder)
                    } else {
                        stringResource(id = AppStrings.Title.NewReminder)
                    },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && notificationPermissionState?.status?.isGranted == false) {
                    Button(
                        onClick = { notificationPermissionState.launchPermissionRequest() },
                    ) {
                        Text(text = stringResource(id = AppStrings.Button.RequestPermission))
                    }
                } else if (!uiState.isEdit) {
                    Button(
                        onClick = { selection.onCreateReminder() },
                    ) {
                        Text(
                            text = stringResource(id = AppStrings.Button.Create),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                    ) {
                        TextButton(
                            onClick = { selection.onDeleteReminder() },
                        ) {
                            Text(
                                text = stringResource(id = AppStrings.Button.Delete),
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }

                        Button(
                            modifier = Modifier.padding(start = 8.dp),
                            onClick = { selection.onUpdateReminder() },
                        ) {
                            Text(
                                text = stringResource(id = AppStrings.Button.Save),
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }
        },
        content = { paddingValues ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val textToShow =
                    if (notificationPermissionState?.status?.shouldShowRationale == true) {
                        stringResource(id = AppStrings.Permission.NotificationRationale)
                    } else {
                        stringResource(id = AppStrings.Permission.NotificationDefault)
                    }

                if (notificationPermissionState?.status?.isGranted == false) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp, top = 4.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(
                                space = 16.dp,
                                alignment = Alignment.CenterVertically,
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Notifications),
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .width(128.dp)
                                    .height(128.dp),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = textToShow,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    return@Scaffold
                }
            }

            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize(),
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable { calendarState.show() },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier =
                                    Modifier
                                        .padding(vertical = 16.dp)
                                        .padding(start = 16.dp, end = 12.dp),
                                painter = painterResource(id = AppIcons.Event),
                                contentDescription = null,
                            )
                            Text(
                                text =
                                    DateTimeUtils.formatLocalDateTime(
                                        uiState.localDateTime,
                                        DateTimeUtils.DATE_PATTERN,
                                    ),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable { clockState.show() },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier =
                                    Modifier
                                        .padding(vertical = 12.dp)
                                        .padding(start = 16.dp, end = 12.dp),
                                painter = painterResource(id = AppIcons.Schedule),
                                contentDescription = null,
                            )
                            Text(
                                text =
                                    DateTimeUtils.formatLocalDateTime(
                                        uiState.localDateTime,
                                        DateTimeUtils.TIME_PATTERN,
                                    ),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                item {
                    OutlinedTextField(
                        value = uiState.message,
                        onValueChange = { newText -> selection.onMessageUpdate(newText) },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        placeholder = {
                            Text(
                                text = stringResource(id = AppStrings.Hint.Message),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = AppStrings.Hint.Message),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                    )
                }
                item {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { repeatDialogState.show() },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier =
                                Modifier
                                    .padding(vertical = 16.dp)
                                    .padding(start = 16.dp, end = 12.dp),
                            painter = painterResource(id = AppIcons.Repeat),
                            contentDescription = null,
                        )
                        Text(
                            text = uiState.repeat.asDisplayableString(),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        },
    )
}

data class ReminderEditScreenSelection(
    val onMessageUpdate: (String) -> Unit,
    val onDateUpdate: (LocalDate) -> Unit,
    val onTimeUpdate: (LocalTime) -> Unit,
    val onRepeatUpdate: (Repeat) -> Unit,
    val onCreateReminder: () -> Unit,
    val onUpdateReminder: () -> Unit,
    val onDeleteReminder: () -> Unit,
)
