package com.hellguy39.hellnotes.reminders.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.components.ReminderCard
import com.hellguy39.hellnotes.components.ReminderCardEvents
import com.hellguy39.hellnotes.reminders.UiState
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@Composable
fun RemindersScreenContent(
    paddingValues: PaddingValues,
    reminderCardEvents: ReminderCardEvents,
    uiState: UiState
) {
    when(uiState) {
        is UiState.Success -> {
            if (uiState.reminders.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                    contentPadding = paddingValues
                ) {
                    item {
                        Text(
                            text = stringResource(id = HellNotesStrings.Label.Upcoming),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    items(uiState.reminders) {
                        ReminderCard(
                            remind = it,
                            events = reminderCardEvents
                        )
                    }
                }
            } else {
                EmptyContentPlaceholder(
                    heroIcon = painterResource(id = HellNotesIcons.Notifications),
                    message = stringResource(id = HellNotesStrings.Text.Empty)
                )
            }
        }
        else -> Unit
    }
}