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

import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import kotlinx.coroutines.launch

@Composable
fun ReminderEditRoute(
    navigateBack: () -> Unit,
    reminderEditViewModel: ReminderEditViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "ReminderEditScreen")

    val context = LocalContext.current
    val uiState by reminderEditViewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler { navigateBack() }

    ReminderEditScreen(
        onNavigationBack = { navigateBack() },
        uiState = uiState,
        selection =
            ReminderEditScreenSelection(
                onMessageUpdate = { message ->
                    reminderEditViewModel.updateMessage(message)
                },
                onDateUpdate = { localDate ->
                    reminderEditViewModel.updateDate(localDate)
                },
                onTimeUpdate = { localTime ->
                    reminderEditViewModel.updateTime(localTime)
                },
                onRepeatUpdate = { repeat ->
                    reminderEditViewModel.updateRepeat(repeat)
                },
                onCreateReminder = {
                    if (reminderEditViewModel.isPossibleToCreateReminder()) {
                        reminderEditViewModel.insertReminder()
                        navigateBack()
                    } else {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(AppStrings.Snack.RemindTimeIsTooLate),
                                withDismissAction = true,
                            )
                        }
                    }
                },
                onDeleteReminder = {
                    reminderEditViewModel.deleteReminder()
                    navigateBack()
                },
                onUpdateReminder = {
                    if (reminderEditViewModel.isPossibleToCreateReminder()) {
                        reminderEditViewModel.updateReminder()
                        navigateBack()
                    } else {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(AppStrings.Snack.RemindTimeIsTooLate),
                                withDismissAction = true,
                            )
                        }
                    }
                },
            ),
        snackbarHostState = snackbarHostState,
    )
}
