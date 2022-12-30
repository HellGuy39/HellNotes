package com.hellguy39.hellnotes.note_detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.components.FullScreenDialog
import com.hellguy39.hellnotes.note_detail.events.MenuEvents
import com.hellguy39.hellnotes.note_detail.events.TopAppBarEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val note by noteDetailViewModel.note.collectAsState()

    var isShowMenu by remember { mutableStateOf(false) }
    var isShowRemindDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isOpenColorDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val menuEvents = object : MenuEvents {
        override fun onDismissMenu() { isShowMenu = false }
        override fun onColor() {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "This feature isn't available yet"
                )
            }
        }
        override fun onLabels() {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "This feature isn't available yet"
                )
            }
        }
        override fun onShare() {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "This feature isn't available yet"
                )
            }
        }
        override fun onDelete() {
            noteDetailViewModel.deleteNote()
            navController.popBackStack()
        }
    }

    val topAppBarEvents = object : TopAppBarEvents {
        override fun onReminder() {
            isShowRemindDialog = true
        }
        override fun onPin(isPinned: Boolean) { noteDetailViewModel.updateIsPinned(isPinned) }
        override fun onColorSelected(colorHex: Long) { noteDetailViewModel.updateNoteBackground(colorHex) }
        override fun onMoreMenu() { isShowMenu = true }
    }

    NoteDetailScreen(
        onNavigationButtonClick = {
            noteDetailViewModel.saveNote()
            navController.popBackStack()
        },
        isShowMenu = isShowMenu,
        isShowRemindDialog = isShowRemindDialog,
        isOpenColorDialog = isOpenColorDialog,
        snackbarHostState = snackbarHostState,
        scrollBehavior = scrollBehavior,
        menuEvents = menuEvents,
        topAppBarEvents = topAppBarEvents,
        note = note,
        onCloseRemindDialog = {
            isShowRemindDialog = false
        },
        onTitleTextChanged = { newText -> noteDetailViewModel.updateNoteTitle(newText) },
        onNoteTextChanged = { newText -> noteDetailViewModel.updateNoteContent(newText) },
    )
}