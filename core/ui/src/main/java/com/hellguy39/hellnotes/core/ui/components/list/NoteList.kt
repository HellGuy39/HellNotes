package com.hellguy39.hellnotes.core.ui.components.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.isNotesEmpty
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun NoteList(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteSelection: NoteSelection,
    categories: List<NoteCategory> = listOf(),
    listStyle: ListStyle = ListStyle.Column,
    selectedNotes: List<Note> = listOf(),
    listHeader: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {
        EmptyContentPlaceholder(
            heroIcon = painterResource(id = HellNotesIcons.Info),
            message = stringResource(id = HellNotesStrings.Text.Empty)
        )
    }
) {

    if (categories.isNotesEmpty()) {
        placeholder()
        return
    }

    when(listStyle) {
        ListStyle.Column -> {
            NoteColumnList(
                innerPadding = innerPadding,
                noteSelection = noteSelection,
                categories = categories,
                selectedNotes = selectedNotes,
                listHeader = listHeader
            )
        }
        ListStyle.Grid -> {
            NoteGridList(
                innerPadding = innerPadding,
                noteSelection = noteSelection,
                categories = categories,
                selectedNotes = selectedNotes,
                listHeader = listHeader
            )
        }
    }
}