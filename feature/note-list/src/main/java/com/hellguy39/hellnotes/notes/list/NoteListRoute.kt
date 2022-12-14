package com.hellguy39.hellnotes.notes.list

import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.navigations.INavigations
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.Sorting
import com.hellguy39.hellnotes.notes.list.events.NoteEvents
import com.hellguy39.hellnotes.notes.list.events.SortMenuEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarMenuEvents

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun NoteListRoute(
    navigations: INavigations,
    noteListViewModel: NoteListViewModel = hiltViewModel()
) {
    val uiState by noteListViewModel.uiState.collectAsState()
    val reminds by noteListViewModel.reminders.collectAsStateWithLifecycle()
    val labels by noteListViewModel.labels.collectAsStateWithLifecycle()
    val selectedNotes by noteListViewModel.selectedNotes.collectAsStateWithLifecycle()

    var isShowAppBarMenu by remember { mutableStateOf(false) }
    var isShowSortMenu by remember { mutableStateOf(false) }

    val haptic = LocalHapticFeedback.current

    val noteEvents = object : NoteEvents {
        override fun onClick(note: Note) {
            uiState.let { state ->
                if (state is NoteListUiState.Success) {
                    if (selectedNotes.isEmpty()) {
                        navigations.navigateToNoteDetail(note.id ?: -1)
                    } else {
                        if (selectedNotes.contains(note)) {
                            noteListViewModel.unselectNote(note)
                        } else {
                            noteListViewModel.selectNote(note)
                        }
                    }
                }
            }
        }

        override fun onLongClick(note: Note) {
            uiState.let { state ->

                haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                if (state is NoteListUiState.Success) {
                    if (selectedNotes.contains(note)) {
                        noteListViewModel.unselectNote(note)
                    } else {
                        noteListViewModel.selectNote(note)
                    }
                }
            }
        }
    }

    val topAppBarMenuEvents = object : TopAppBarMenuEvents {
        override fun onDismiss() { isShowAppBarMenu = false }
        override fun show() { isShowAppBarMenu = true }
        override fun onReminders() { navigations.navigateToReminders() }
        override fun onPatchNote() {  }
        override fun onSettings() { navigations.navigateToSettings() }
        override fun onAboutApp() { navigations.navigateToAboutApp() }
    }

    val topAppBarEvents = object : TopAppBarEvents {
        override fun onSearch() { navigations.navigateToSearch() }
        override fun onCancelSelection() { noteListViewModel.cancelNoteSelection() }
        override fun onDeleteAllSelected() { noteListViewModel.deleteAllSelected() }
    }

    val sortMenuEvents = object : SortMenuEvents {
        override fun show() { isShowSortMenu = true }
        override fun onDismiss() { isShowSortMenu = false }
        override fun onSortSelected(sorting: Sorting) { noteListViewModel.updateSorting(sorting) }
    }

    NoteListScreen(
        isShowAppBarMenu = isShowAppBarMenu,
        isShowSortMenu = isShowSortMenu,
        onFabAddClick = { navigations.navigateToNoteDetail(-1) },
        sortMenuEvents = sortMenuEvents,
        topAppBarEvents = topAppBarEvents,
        topAppBarMenuEvents = topAppBarMenuEvents,
        noteListUiState = uiState,
        noteEvents = noteEvents,
        onListStyleChange = { noteListViewModel.updateListStyle() },
        labels = labels,
        reminds = reminds,
        selectedNotes = selectedNotes
    )
}