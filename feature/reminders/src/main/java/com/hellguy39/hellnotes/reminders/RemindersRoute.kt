package com.hellguy39.hellnotes.reminders

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.components.ReminderCardEvents
import com.hellguy39.hellnotes.domain.AlarmScheduler
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.reminders.events.EditReminderDialogEvents

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RemindersRoute(
    navController: NavController,
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    alarmScheduler: AlarmScheduler = remindersViewModel.alarmScheduler
) {
    val uiState by remindersViewModel.uiState.collectAsStateWithLifecycle()

    var isShowEditRemindDialog by remember { mutableStateOf(false) }
    var editableRemind: Remind? by remember { mutableStateOf(null) }

    val editReminderDialogEvents = object : EditReminderDialogEvents {
        override fun show() { isShowEditRemindDialog = true }
        override fun dismiss() { isShowEditRemindDialog = false }
        override fun setRemindToEdit(remind: Remind) { editableRemind = remind }
        override fun deleteRemind(remind: Remind) {
            alarmScheduler.cancelAlarm(remind)
            remindersViewModel.deleteRemind(remind)
        }
        override fun updateRemind(remind: Remind) {
            uiState.let { state ->
                if (state is UiState.Success) {
                    val oldRemind = state.reminders.find { it.id == remind.id }
                    oldRemind?.let { alarmScheduler.cancelAlarm(it) }
                    alarmScheduler.scheduleAlarm(remind)
                    remindersViewModel.updateRemind(remind)
                }
            }
        }
    }

    val reminderCardEvents = object : ReminderCardEvents {
        override fun onClick(remind: Remind) {}
        override fun onLongClick(remind: Remind) {}
        override fun onDeleteButtonClick(remind: Remind) {
            alarmScheduler.cancelAlarm(remind)
            remindersViewModel.deleteRemind(remind)
        }
        override fun onEditButtonClick(remind: Remind) {
            editReminderDialogEvents.setRemindToEdit(remind)
            editReminderDialogEvents.show()
        }
    }

    RemindersScreen(
        onNavigationButtonClick = {
            navController.popBackStack()
        },
        uiState = uiState,
        reminderCardEvents = reminderCardEvents,
        isShowEditRemindDialog = isShowEditRemindDialog,
        editReminderDialogEvents = editReminderDialogEvents,
        editableRemind = editableRemind
    )
}