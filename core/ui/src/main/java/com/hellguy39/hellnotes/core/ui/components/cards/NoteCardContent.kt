package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.isChecklistsValid
import com.hellguy39.hellnotes.core.model.repository.local.database.removeCompletedChecklists
import com.hellguy39.hellnotes.core.model.repository.local.database.sortByPriority
import com.hellguy39.hellnotes.core.ui.components.NoteChecklistGroup
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup

@Composable
internal fun NoteCardContent(
    noteDetailWrapper: NoteDetailWrapper,
) {
    val isTitleValid = noteDetailWrapper.note.title.isNotEmpty() || noteDetailWrapper.note.title.isNotBlank()
    val isNoteValid = noteDetailWrapper.note.note.isNotEmpty() || noteDetailWrapper.note.note.isNotBlank()
    val isChipsValid = noteDetailWrapper.labels.isNotEmpty() || noteDetailWrapper.reminders.isNotEmpty()

    val filteredChecklists = noteDetailWrapper.checklists
        .removeCompletedChecklists()
        .sortByPriority()
    val isChecklistValid = filteredChecklists.isChecklistsValid()

    Column(
        modifier = Modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isTitleValid) {
            Text(
                text = noteDetailWrapper.note.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (isNoteValid) {
            Text(
                text = noteDetailWrapper.note.note,
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
                reminders = noteDetailWrapper.reminders,
                labels = noteDetailWrapper.labels,
                limitElements = true,
                maxElements = 2,
            )
        }
    }
}