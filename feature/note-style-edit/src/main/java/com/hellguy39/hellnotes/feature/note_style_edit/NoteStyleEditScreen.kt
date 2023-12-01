package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.cards.NoteCard
import com.hellguy39.hellnotes.core.ui.components.items.HNRadioButtonItem
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NoteStyleEditScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: NoteStyleEditUiState,
    onNoteStyleChange: (NoteStyle) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

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
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.NoteStyle)
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            val cardModifier = Modifier
                                .padding(horizontal = 32.dp, vertical = 32.dp)
                                .width(192.dp)

                            val duration = 200

                            AnimatedContent(
                                targetState = uiState.noteStyle,
                                transitionSpec = {
                                    slideInHorizontally(animationSpec = tween(duration)) { fullWidth -> fullWidth } + fadeIn(animationSpec = tween(duration)) with
                                            slideOutHorizontally(animationSpec = tween(duration)) { fullWidth -> -fullWidth } + fadeOut(animationSpec = tween(duration))
                                }
                            ) { style ->
                                NoteCard(
                                    modifier = cardModifier,
                                    noteDetailWrapper = exampleNoteDetailWrapper,
                                    noteStyle = style
                                )
                            }
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier.selectableGroup(),
                    ) {
                        val listItemModifier = Modifier.fillMaxWidth()
                            .padding(16.dp)

                        HNRadioButtonItem(
                            modifier = listItemModifier,
                            title = stringResource(id = HellNotesStrings.MenuItem.Outlined),
                            isSelected = uiState.noteStyle == NoteStyle.Outlined,
                            onClick = { onNoteStyleChange(NoteStyle.Outlined) },
                        )

                        HNRadioButtonItem(
                            modifier = listItemModifier,
                            title = stringResource(id = HellNotesStrings.MenuItem.Elevated),
                            isSelected = uiState.noteStyle == NoteStyle.Elevated,
                            onClick = { onNoteStyleChange(NoteStyle.Elevated) },
                        )
                    }
                }
            }
        }
    )
}