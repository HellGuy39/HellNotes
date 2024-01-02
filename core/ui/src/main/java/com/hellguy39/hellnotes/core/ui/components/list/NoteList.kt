package com.hellguy39.hellnotes.core.ui.components.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteList(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteStyle: NoteStyle,
    isSwipeable: Boolean,
    onClick: (Note) -> Unit,
    onLongClick: (Note) -> Unit,
    onDismiss: (DismissDirection, Note) -> Boolean,
    categories: List<NoteCategory> = listOf(),
    listStyle: ListStyle = ListStyle.Column,
    selectedNotes: List<Note> = listOf(),
    listHeader: @Composable () -> Unit = {},
) {
    val listModifier =
        Modifier
            .fillMaxSize()
            .padding(horizontal = Spaces.extraSmall, vertical = Spaces.extraSmall)
            .testTag("item_list")

    when (listStyle) {
        ListStyle.Column -> {
            NoteColumnList(
                modifier = listModifier,
                noteStyle = noteStyle,
                isSwipeable = isSwipeable,
                onClick = onClick,
                onLongClick = onLongClick,
                onDismiss = onDismiss,
                innerPadding = innerPadding,
                categories = categories,
                selectedNotes = selectedNotes,
                listHeader = listHeader,
            )
        }
        ListStyle.Grid -> {
            NoteGridList(
                modifier = listModifier,
                innerPadding = innerPadding,
                isSwipeable = isSwipeable,
                onClick = onClick,
                onLongClick = onLongClick,
                onDismiss = onDismiss,
                categories = categories,
                selectedNotes = selectedNotes,
                noteStyle = noteStyle,
                listHeader = listHeader,
            )
        }
    }
}
