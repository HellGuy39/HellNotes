package com.hellguy39.hellnotes.feature.reminders

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.ui.components.ReminderCardEvents
import com.hellguy39.hellnotes.feature.reminders.components.RemindersScreenContent
import com.hellguy39.hellnotes.feature.reminders.components.RemindersTopAppBar
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: UiState,
    reminderCardEvents: ReminderCardEvents,
) {
    BackHandler(onBack = onNavigationButtonClick)

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

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