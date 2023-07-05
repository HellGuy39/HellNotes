package com.hellguy39.hellnotes.feature.home.list.trash

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.list.trash.components.TrashTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashRoute(
    trashViewModel: TrashViewModel = hiltViewModel(),
) {
//    val context = LocalContext.current
//
//    val selectedNoteWrappers by homeViewModel.selectedNoteWrappers.collectAsStateWithLifecycle()
//    val noteStyle by homeViewModel.noteStyle.collectAsStateWithLifecycle()
//    val listStyle by homeViewModel.listStyle.collectAsStateWithLifecycle()

    val uiState by trashViewModel.uiState.collectAsStateWithLifecycle()

    val restoreDialogState = rememberDialogState()
    val emptyTrashDialogState = rememberDialogState()
    val scope = rememberCoroutineScope()

    CustomDialog(
        state = emptyTrashDialogState,
        heroIcon = painterResource(id = HellNotesIcons.Delete),
        title = stringResource(id = HellNotesStrings.Title.EmptyTrash),
        message = stringResource(id = HellNotesStrings.Supporting.EmptyTrash),
        onCancel = {
            emptyTrashDialogState.dismiss()
        },
        onAccept = {
            trashViewModel.send(TrashScreenUiEvent.EmptyTrash)
            emptyTrashDialogState.dismiss()
        }
    )

    CustomDialog(
        state = restoreDialogState,
        heroIcon = painterResource(id = HellNotesIcons.RestoreFromTrash),
        title = stringResource(id = HellNotesStrings.Title.RestoreThisNote),
        message = stringResource(id = HellNotesStrings.Supporting.RestoreNote),
        onClose = {
            //trashViewModel.clearSelectedNote()
            restoreDialogState.dismiss()
        },
        onCancel = {
            //trashViewModel.clearSelectedNote()
            restoreDialogState.dismiss()
        },
        onAccept = {
//            val note = trashViewModel.selectedNote.value
//            trashViewModel.clearSelectedNote()
            //multiActionSelection.onRestoreNote(note)
            restoreDialogState.dismiss()
        }
    )

    TrashScreen(
        uiState = uiState,
        screenSelection = TrashScreenSelection(
            listStyle = ListStyle.Column,
            noteSelection = NoteSelection(
                noteStyle = NoteStyle.Outlined,
                isSwipeable = false,
                onClick = { noteWrapper ->
//                    if (selectedNoteWrappers.isNotEmpty()) {
//                        homeViewModel.send(HomeUiEvent.SelectNote(noteWrapper))
//                    } else {
//
//                    }
                },
                onLongClick = { noteWrapper ->
                    //homeViewModel.send(HomeUiEvent.SelectNote(noteWrapper))
                              },
                onDismiss = { _, _ -> false }
            ),
            noteStyle = NoteStyle.Outlined,
            selectedNoteWrappers = listOf()
        ),
        appBarSelection = TrashTopAppBarSelection(
            onCancelSelection = {
                //homeViewModel.send(HomeUiEvent.CancelNoteSelection)
                                },
            onNavigation = {
                //scope.launch { drawerState.open() }
                           },
            onRestoreSelected = {},
            onDeleteSelected = {},
            onEmptyTrash = { emptyTrashDialogState.show() }
        )
    )
}