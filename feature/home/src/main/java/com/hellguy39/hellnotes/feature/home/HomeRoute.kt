package com.hellguy39.hellnotes.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.components.NoteSelection
import com.hellguy39.hellnotes.core.ui.navigations.INavigations
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.feature.home.note_list.NoteListScreen
import com.hellguy39.hellnotes.feature.home.note_list.components.DrawerSheetContent
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.util.DrawerItem
import com.hellguy39.hellnotes.feature.home.reminders.ReminderTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.reminders.RemindersScreen
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    navigations: INavigations,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val noteListUiState by homeViewModel.noteListUiState.collectAsStateWithLifecycle()
    val remindersUiState by homeViewModel.remindersUiState.collectAsStateWithLifecycle()
    val selectedNotes by homeViewModel.selectedNotes.collectAsStateWithLifecycle()

    var isShowSortMenu by remember { mutableStateOf(false) }

    val haptic = LocalHapticFeedback.current

    val noteSelection = NoteSelection(
        onClick = { note ->
            if (selectedNotes.isEmpty()) {
                navigations.navigateToNoteDetail(note.id ?: -1)
            } else {
                if (selectedNotes.contains(note)) {
                    homeViewModel.unselectNote(note)
                } else {
                    homeViewModel.selectNote(note)
                }
            }
        },
        onLongClick = { note ->

            haptic.performHapticFeedback(HapticFeedbackType.LongPress)

            if (selectedNotes.contains(note)) {
                homeViewModel.unselectNote(note)
            } else {
                homeViewModel.selectNote(note)
            }

        }
    )

//    val sortMenuEvents = object : SortMenuEvents {
//        override fun show() { isShowSortMenu = true }
//        override fun onDismiss() { isShowSortMenu = false }
//        override fun onSortSelected(sorting: Sorting) { homeViewModel.updateSorting(sorting) }
//    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val mainItemIcons = listOf(
        painterResource(id = HellNotesIcons.StickyNote),
        painterResource(id = HellNotesIcons.Notifications)
    )

    var selectedMainItem by remember { mutableStateOf(mainItemIcons[0]) }

    val mainItems = listOf(
        DrawerItem(
            title = "Notes",
            icon = mainItemIcons[0],
            onClick = {
                scope.launch { drawerState.close() }
                it.icon?.let { selectedMainItem = it }
            }
        ),
        DrawerItem(
            title = "Reminders",
            icon = mainItemIcons[1],
            onClick = {
                scope.launch { drawerState.close() }
                it.icon?.let { selectedMainItem = it }
            }
        )
    )

    val staticItems = listOf(
        DrawerItem(
            title = "Archive",
            icon = painterResource(id = HellNotesIcons.Archive),
            onClick = {
                scope.launch { drawerState.close() }

            }
        ),
        DrawerItem(
            title = "Trash",
            icon = painterResource(id = HellNotesIcons.Delete),
            onClick = {
                scope.launch { drawerState.close() }

            }
        ),
        DrawerItem(
            title = "Settings",
            icon = painterResource(id = HellNotesIcons.Settings),
            onClick = {
                scope.launch { drawerState.close() }
                navigations.navigateToSettings()
            }
        ),
        DrawerItem(
            title = "About app",
            icon = painterResource(id = HellNotesIcons.Info),
            onClick = {
                scope.launch { drawerState.close() }
                navigations.navigateToAboutApp()
            }
        )
    )

    val labelItems = listOf<DrawerItem>()
//        labels.map { label ->
//        DrawerItem(
//            title = label.name,
//            icon = painterResource(id = HellNotesIcons.Label),
//            onClick = {
//                scope.launch { drawerState.close() }
//
//            }
//        )
//    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheetContent(
                selectedItem = mainItems.find { it.icon == selectedMainItem },
                mainItems = mainItems,
                staticItems = staticItems,
                labelItems = labelItems,
            )
        },
        content = {
            Crossfade(targetState = selectedMainItem) {
                if (it == mainItemIcons[0]) {
                    NoteListScreen(
                        onFabAddClick = { navigations.navigateToNoteDetail(-1) },
                        noteListTopAppBarSelection = NoteListTopAppBarSelection(
                            listStyle = noteListUiState.listStyle,
                            onCancelSelection = {
                                homeViewModel.cancelNoteSelection()
                            },
                            onNavigation = {
                                scope.launch { drawerState.open() }
                            },
                            onDeleteSelected = {
                                homeViewModel.deleteAllSelected()
                            },
                            onSearch = {
                                navigations.navigateToSearch()
                            },
                            onChangeListStyle = {
                                homeViewModel.updateListStyle()
                            }
                        ),
                        noteListUiState = noteListUiState,
                        selectedNotes = selectedNotes,
                        noteSelection = noteSelection
                    )
                } else {
                    RemindersScreen(
                        remindersUiState = remindersUiState,
                        noteSelection = noteSelection,
                        selectedNotes = selectedNotes,
                        reminderTopAppBarSelection = ReminderTopAppBarSelection(
                            listStyle = remindersUiState.listStyle,
                            onNavigation = {
                                scope.launch { drawerState.open() }
                            },
                            onChangeListStyle = {
                                homeViewModel.updateListStyle()
                            }
                        )
                    )
                }
            }
        }
    )
}