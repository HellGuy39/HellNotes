package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.CustomRadioButton
import com.hellguy39.hellnotes.core.ui.components.cards.ElevatedNoteCard
import com.hellguy39.hellnotes.core.ui.components.cards.OutlinedNoteCard
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteStyleEditScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: NoteStyleEditUiState,
    onNoteStyleChange: (NoteStyle) -> Unit
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val exampleNoteDetailWrapper = NoteDetailWrapper(
        note = Note(
            title = "This is a sample note",
            note = "We have to start from the fact that synthetic testing determines the high demand for the distribution of internal reserves and resources."
        ),
        labels = listOf(
            Label(name = "Important")
        ),
        reminders = listOf()
    )

    Scaffold(
        topBar = {
            CustomLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.NoteStyle)
            )
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues
            ) {
                item {
                    Column(
                        modifier = Modifier.selectableGroup(),
                        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
                    ) {
                        Column {
                            OutlinedNoteCard(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                noteDetailWrapper = exampleNoteDetailWrapper,
                            )
                            CustomRadioButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .selectable(
                                        selected = uiState.noteStyle == NoteStyle.Outlined,
                                        onClick = { onNoteStyleChange(NoteStyle.Outlined) },
                                        role = Role.RadioButton
                                    ),
                                title = stringResource(id = HellNotesStrings.MenuItem.Outlined),
                                isSelected = uiState.noteStyle == NoteStyle.Outlined
                            )
                        }
                        Column {
                            ElevatedNoteCard(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                noteDetailWrapper = exampleNoteDetailWrapper,
                            )
                            CustomRadioButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .selectable(
                                        selected = uiState.noteStyle == NoteStyle.Elevated,
                                        onClick = { onNoteStyleChange(NoteStyle.Elevated) },
                                        role = Role.RadioButton
                                    ),
                                title = stringResource(id = HellNotesStrings.MenuItem.Elevated),
                                isSelected = uiState.noteStyle == NoteStyle.Elevated
                            )
                        }
                    }
                }
            }
        }
    )
}