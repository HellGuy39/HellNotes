package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.ui.components.cards.ElevatedNoteCard
import com.hellguy39.hellnotes.core.ui.components.cards.OutlinedNoteCard
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteStyleEditScreen(
    onNavigationButtonClick: () -> Unit,
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val exampleNoteDetailWrapper = NoteDetailWrapper(
        note = Note(title = "This is a sample note", note = "We have to start from the fact that synthetic testing determines the high demand for the distribution of internal reserves and resources."),
        labels = listOf(Label(name = "Important")),
        reminders = listOf()
    )

    Scaffold(
        topBar = {
            CustomLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = "Note style"
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(space = 16.dp)
            ) {
                item {
                    val isOutlinedSelected = rememberSaveable { mutableStateOf(false) }
                    OutlinedNoteCard(
                        noteDetailWrapper = exampleNoteDetailWrapper,
                        isSelected = isOutlinedSelected.value,
                        onClick = { isOutlinedSelected.value = !isOutlinedSelected.value }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = true, onClick = {  })
                        Text(
                            text = "Outlined",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                item {
                    val isElevatedSelected = rememberSaveable { mutableStateOf(false) }
                    ElevatedNoteCard(
                        noteDetailWrapper = exampleNoteDetailWrapper,
                        isSelected = isElevatedSelected.value,
                        onClick = { isElevatedSelected.value = !isElevatedSelected.value }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = false, onClick = {  })
                        Text(
                            text = "Elevated",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    )
}