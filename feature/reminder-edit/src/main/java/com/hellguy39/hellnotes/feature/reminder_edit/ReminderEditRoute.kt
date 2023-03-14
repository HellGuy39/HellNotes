package com.hellguy39.hellnotes.feature.reminder_edit

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import kotlinx.coroutines.launch

@Composable
fun ReminderEditRoute(
    navController: NavController,
    reminderEditViewModel: ReminderEditViewModel = hiltViewModel()
) {
    val uiState by reminderEditViewModel.uiState.collectAsStateWithLifecycle()

    fun onNavigationBack() {
        navController.popBackStack()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val remindIsTooLateMessage = stringResource(id = HellNotesStrings.Text.RemindTimeIsTooLate)

    ReminderEditScreen(
        onNavigationBack = { onNavigationBack() },
        uiState = uiState,
        selection = ReminderEditScreenSelection(
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
                    onNavigationBack()
                } else {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = remindIsTooLateMessage,
                            withDismissAction = true
                        )
                    }
                }
            },
            onDeleteReminder = {
                reminderEditViewModel.deleteReminder()
                onNavigationBack()
            },
            onUpdateReminder = {
                if (reminderEditViewModel.isPossibleToCreateReminder()) {
                    reminderEditViewModel.updateReminder()
                    onNavigationBack()
                } else {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = remindIsTooLateMessage,
                            withDismissAction = true
                        )
                    }
                }
            }
        ),
        snackbarHostState = snackbarHostState
    )
}