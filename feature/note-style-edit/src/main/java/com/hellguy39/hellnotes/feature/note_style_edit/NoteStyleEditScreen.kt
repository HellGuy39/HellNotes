package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.CustomRadioButton
import com.hellguy39.hellnotes.core.ui.components.cards.ElevatedNoteCard
import com.hellguy39.hellnotes.core.ui.components.cards.NoteCard
import com.hellguy39.hellnotes.core.ui.components.cards.OutlinedNoteCard
import com.hellguy39.hellnotes.core.ui.components.items.ItemHeader
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NoteStyleEditScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: NoteStyleEditUiState,
    onNoteStyleChange: (NoteStyle) -> Unit
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)

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
            CustomLargeTopAppBar(
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
                        shape = RoundedCornerShape(32.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color.Black)
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
                        //verticalArrangement = Arrangement.spacedBy(space = 16.dp)
                    ) {
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
    )
}