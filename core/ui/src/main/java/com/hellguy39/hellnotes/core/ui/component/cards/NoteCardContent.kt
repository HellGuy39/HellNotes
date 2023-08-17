package com.hellguy39.hellnotes.core.ui.component.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.isChecklistsValid
import com.hellguy39.hellnotes.core.model.local.database.removeCompletedChecklists
import com.hellguy39.hellnotes.core.model.local.database.sortByPriority
import com.hellguy39.hellnotes.core.ui.component.checklist.NoteChecklistGroup
import com.hellguy39.hellnotes.core.ui.component.chip.NoteChipGroup
import com.hellguy39.hellnotes.core.ui.value.spacing

@Composable
internal fun NoteCardContent(
    noteWrapper: NoteWrapper,
) {
    val isTitleValid = noteWrapper.note.title.isNotEmpty() || noteWrapper.note.title.isNotBlank()
    val isNoteValid = noteWrapper.note.note.isNotEmpty() || noteWrapper.note.note.isNotBlank()
    val isChipsValid = noteWrapper.labels.isNotEmpty() || noteWrapper.reminders.isNotEmpty()

    val filteredChecklists = noteWrapper.checklists
        .removeCompletedChecklists()
        .sortByPriority()
    val isChecklistValid = filteredChecklists.isChecklistsValid()

    Column(
        modifier = Modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        if (isTitleValid) {
            Text(
                text = noteWrapper.note.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (isNoteValid) {
            Text(
                text = noteWrapper.note.note,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                modifier = Modifier,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (isChecklistValid) {
            NoteChecklistGroup(
                checklists = filteredChecklists
            )
        }

        if (isChipsValid) {
            NoteChipGroup(
                modifier = Modifier,
                reminders = noteWrapper.reminders,
                labels = noteWrapper.labels,
                limitElements = true,
                maxElements = 2,
            )
        }
    }
}