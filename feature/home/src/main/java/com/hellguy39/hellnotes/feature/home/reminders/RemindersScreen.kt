package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.reminders.components.ReminderTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.reminders.components.RemindersTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RemindersScreen(
    navController: NavController,
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    listStyle: ListStyle,
    drawerState: DrawerState,
    onChangeListStyle: () -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scope = rememberCoroutineScope()

    val uiState by remindersViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RemindersTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = ReminderTopAppBarSelection(
                    listStyle = listStyle,
                    selectedNotes = uiState.selectedNotes,
                    onNavigation = { scope.launch { drawerState.open() } },
                    onChangeListStyle = onChangeListStyle,
                    onDeleteSelected = { remindersViewModel.deleteAllSelected() },
                    onCancelSelection = { remindersViewModel.cancelNoteSelection() }
                )
            )
        },
        content = { paddingValues ->
            AnimatedContent(targetState = listStyle) { listStyle ->
                NoteList(
                    innerPadding = paddingValues,
                    noteSelection = NoteSelection(
                        onClick = { note ->
                            if (uiState.selectedNotes.isEmpty()) {
                                navController.navigateToNoteDetail(note.id)
                            } else {
                                if (uiState.selectedNotes.contains(note)) {
                                    remindersViewModel.unselectNote(note)
                                } else {
                                    remindersViewModel.selectNote(note)
                                }
                            }
                        },
                        onLongClick = { note ->
                            if (uiState.selectedNotes.contains(note)) {
                                remindersViewModel.unselectNote(note)
                            } else {
                                remindersViewModel.selectNote(note)
                            }
                        },
                        onDismiss = { direction, note ->
                            false
                        }
                    ),
                    categories = listOf(
                        NoteCategory(notes = uiState.notes)
                    ),
                    listStyle = listStyle,
                    selectedNotes = uiState.selectedNotes,
                    placeholder = {
                        EmptyContentPlaceholder(
                            paddingValues = paddingValues,
                            heroIcon = painterResource(id = HellNotesIcons.Notifications),
                            message = stringResource(id = HellNotesStrings.Text.Empty)
                        )
                    },
                    listHeader = {
                        Text(
                            text = stringResource(id = HellNotesStrings.Label.Upcoming),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                )
            }
        }
    )
}