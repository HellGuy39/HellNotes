package com.hellguy39.hellnotes.reminders

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.system.BackHandler
import com.hellguy39.hellnotes.components.ReminderCardEvents
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.reminders.components.EditReminderDialog
import com.hellguy39.hellnotes.reminders.components.RemindersScreenContent
import com.hellguy39.hellnotes.reminders.components.RemindersTopAppBar
import com.hellguy39.hellnotes.reminders.events.EditReminderDialogEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: UiState,
    reminderCardEvents: ReminderCardEvents,
    isShowEditRemindDialog: Boolean,
    editableRemind: Remind?,
    editReminderDialogEvents: EditReminderDialogEvents
) {
    BackHandler(onBack = onNavigationButtonClick)

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    EditReminderDialog(
        isShowDialog = isShowEditRemindDialog,
        events = editReminderDialogEvents,
        remind = editableRemind
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RemindersTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick
            )
        },
        content = { paddingValues ->
            RemindersScreenContent(
                paddingValues = paddingValues,
                uiState = uiState,
                reminderCardEvents = reminderCardEvents
            )
        }
    )
}