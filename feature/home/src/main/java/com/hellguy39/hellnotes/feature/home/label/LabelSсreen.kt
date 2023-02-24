package com.hellguy39.hellnotes.feature.home.label

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBar
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBarSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelScreen(
    navController: NavController,
    drawerState: DrawerState,
    listStyle: ListStyle,
    labelViewModel: LabelViewModel = hiltViewModel(),
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val uiState by labelViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { paddingValues ->
            NoteList(
                innerPadding = paddingValues,
                noteSelection = NoteSelection(
                    onClick = { note ->
                        if (uiState.selectedNotes.isEmpty()) {
                            navController.navigateToNoteDetail(note.id ?: -1)
                        } else {
                            if (uiState.selectedNotes.contains(note)) {
                                labelViewModel.unselectNote(note)
                            } else {
                                labelViewModel.selectNote(note)
                            }
                        }
                    },
                    onLongClick = { note ->
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        if (uiState.selectedNotes.contains(note)) {
                            labelViewModel.unselectNote(note)
                        } else {
                            labelViewModel.selectNote(note)
                        }
                    },
                    onDismiss = { direction, note ->
                        false
                    }
                ),
                categories = listOf(
                    NoteCategory(
                        notes = uiState.notes
                    )
                ),
                selectedNotes = uiState.selectedNotes,
                listStyle = listStyle,
                placeholder = {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.Label),
                        message = stringResource(id = HellNotesStrings.Text.Empty)
                    )
                }
            )
        },
        topBar = {
            LabelTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = LabelTopAppBarSelection(
                    selectedNotes = uiState.selectedNotes,
                    onDeleteSelected = {
                        labelViewModel.deleteAllSelected()
                    },
                    onCancelSelection = {
                        labelViewModel.cancelNoteSelection()
                    },
                    onArchiveSelected = {
                        labelViewModel.archiveAllSelected()
                    },
                    onNavigation = { scope.launch { drawerState.open() } }
                ),
                label = uiState.label
            )
        }
    )
}