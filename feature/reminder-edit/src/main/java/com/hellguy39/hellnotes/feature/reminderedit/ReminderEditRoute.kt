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
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import kotlinx.coroutines.launch

@Composable
fun ReminderEditRoute(
    navigateBack: () -> Unit,
    reminderEditViewModel: ReminderEditViewModel = hiltViewModel(),
) {
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
