package com.hellguy39.hellnotes.feature.home.trash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.cards.TipCard
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.trash.components.TrashDropdownMenuSelection
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBar
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBarSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    listStyle: ListStyle,
    drawerState: DrawerState,
    onCloseTip: () -> Unit,
    isTipVisible: Boolean,
    trashViewModel: TrashViewModel = hiltViewModel(),
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val restoreDialogState = rememberDialogState()
    val emptyTrashDialogState = rememberDialogState()
    val scope = rememberCoroutineScope()

    val uiState by trashViewModel.uiState.collectAsStateWithLifecycle()

    CustomDialog(
        state = emptyTrashDialogState,
        heroIcon = painterResource(id = HellNotesIcons.Delete),
        title = stringResource(id = HellNotesStrings.Title.EmptyTrash),
        message = stringResource(id = HellNotesStrings.Helper.EmptyTrashDialog),
        onCancel = {
            emptyTrashDialogState.dismiss()
        },
        onAccept = {
            trashViewModel.emptyTrash()
            emptyTrashDialogState.dismiss()
        }
    )

    CustomDialog(
        state = restoreDialogState,
        heroIcon = painterResource(id = HellNotesIcons.RestoreFromTrash),
        title = stringResource(id = HellNotesStrings.Title.RestoreThisNote),
        message = stringResource(id = HellNotesStrings.Helper.RestoreNoteDialog),
        onClose = {
            trashViewModel.selectSingleNote(null)
            restoreDialogState.dismiss()
        },
        onCancel = {
            trashViewModel.selectSingleNote(null)
            restoreDialogState.dismiss()
        },
        onAccept = {
            trashViewModel.restoreSingleNote()
            restoreDialogState.dismiss()
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrashTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = TrashTopAppBarSelection(
                    onNavigation = { scope.launch { drawerState.open() } },
                    selectedNotes = uiState.selectedNotes,
                    onCancelSelection = { trashViewModel.cancelNoteSelection() },
                    onRestoreSelected = { trashViewModel.restoreSelectedNotes() },
                    onDeleteSelected = { trashViewModel.deleteSelectedNotes() }
                ),
                trashDropdownMenuSelection = TrashDropdownMenuSelection(
                    onEmptyTrash = { emptyTrashDialogState.show() }
                )
            )
        },
        content = { paddingValues ->
            NoteList(
                innerPadding = paddingValues,
                noteSelection = NoteSelection(
                    onClick = { note ->
                        if (uiState.selectedNotes.isEmpty()) {
                            trashViewModel.selectSingleNote(note)
                            restoreDialogState.show()
                        } else {
                            if (uiState.selectedNotes.contains(note)) {
                                trashViewModel.unselectNote(note)
                            } else {
                                trashViewModel.selectNote(note)
                            }
                        }
                    },
                    onLongClick = { note ->
                        if (uiState.selectedNotes.contains(note)) {
                            trashViewModel.unselectNote(note)
                        } else {
                            trashViewModel.selectNote(note)
                        }
                    },
                    onDismiss = { direction, note ->
                        false
                    }
                ),
                categories = listOf(
                    NoteCategory(notes = uiState.trashNotes)
                ),
                selectedNotes = uiState.selectedNotes,
                listStyle = listStyle,
                listHeader = {
                    TipCard(
                        isVisible = isTipVisible,
                        message = stringResource(id = HellNotesStrings.Text.AutoDeleteTrash),
                        onClose = onCloseTip
                    )
                },
                placeholder = {
                    EmptyContentPlaceholder(
                        paddingValues = paddingValues,
                        heroIcon = painterResource(id = HellNotesIcons.Delete),
                        message = stringResource(id = HellNotesStrings.Text.NoNotesInTrash)
                    )
                }
            )
        }
    )
}