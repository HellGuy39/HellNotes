package com.hellguy39.hellnotes.feature.reminder_edit

import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val uiState by reminderEditViewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler { navController.popBackStack() }

    ReminderEditScreen(
        onNavigationBack = navController::popBackStack,
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
                    navController.popBackStack()
                } else {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(HellNotesStrings.Snack.RemindTimeIsTooLate),
                            withDismissAction = true
                        )
                    }
                }
            },
            onDeleteReminder = {
                reminderEditViewModel.deleteReminder()
                navController.popBackStack()
            },
            onUpdateReminder = {
                if (reminderEditViewModel.isPossibleToCreateReminder()) {
                    reminderEditViewModel.updateReminder()
                    navController.popBackStack()
                } else {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(HellNotesStrings.Snack.RemindTimeIsTooLate),
                            withDismissAction = true
                        )
                    }
                }
            }
        ),
        snackbarHostState = snackbarHostState
    )
}