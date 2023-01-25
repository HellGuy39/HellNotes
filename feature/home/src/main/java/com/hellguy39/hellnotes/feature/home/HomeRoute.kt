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
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.components.NoteSelection
import com.hellguy39.hellnotes.core.ui.navigations.INavigations
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.feature.home.note_list.NoteListScreen
import com.hellguy39.hellnotes.feature.home.note_list.NoteListViewModel
import com.hellguy39.hellnotes.feature.home.note_list.components.DrawerSheetContent
import com.hellguy39.hellnotes.feature.home.note_list.components.ListConfigurationSelection
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.reminders.RemindersScreen
import com.hellguy39.hellnotes.feature.home.reminders.RemindersViewModel
import com.hellguy39.hellnotes.feature.home.reminders.components.ReminderTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.trash.TrashScreen
import com.hellguy39.hellnotes.feature.home.trash.TrashViewModel
import com.hellguy39.hellnotes.feature.home.trash.components.TrashDropdownMenuSelection
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.util.DrawerItem
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    navigations: INavigations,
    homeViewModel: HomeViewModel = hiltViewModel(),
    noteListViewModel: NoteListViewModel = hiltViewModel(),
    trashViewModel: TrashViewModel = hiltViewModel(),
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    dateHelper: DateHelper = homeViewModel.dateHelper
) {
    val noteListUiState by noteListViewModel.uiState.collectAsStateWithLifecycle()
    val remindersUiState by remindersViewModel.uiState.collectAsStateWithLifecycle()
    val trashUiState by trashViewModel.uiState.collectAsStateWithLifecycle()

    val listStyle by homeViewModel.listStyle.collectAsStateWithLifecycle()
    val labels by homeViewModel.labels.collectAsStateWithLifecycle()
    val selectedDrawerItem by homeViewModel.drawerItem.collectAsStateWithLifecycle()

    val haptic = LocalHapticFeedback.current

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val selectableItems = listOf(
        "Notes",
        "Reminders",
        "Archive",
        "Trash"
    )

    val mainItems = listOf(
        DrawerItem(
            title = "Notes",
            icon = painterResource(id = HellNotesIcons.StickyNote),
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                homeViewModel.setDrawerItem(selectableItems.indexOf(drawerItem.title))
            }
        ),
        DrawerItem(
            title = "Reminders",
            icon = painterResource(id = HellNotesIcons.Notifications),
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                homeViewModel.setDrawerItem(selectableItems.indexOf(drawerItem.title))
            }
        ),
        DrawerItem(
            title = "Archive",
            icon = painterResource(id = HellNotesIcons.Archive),
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                homeViewModel.setDrawerItem(selectableItems.indexOf(drawerItem.title))
            }
        ),
        DrawerItem(
            title = "Trash",
            icon = painterResource(id = HellNotesIcons.Delete),
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                homeViewModel.setDrawerItem(selectableItems.indexOf(drawerItem.title))
            }
        ),
    )

    val staticItems = listOf(
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

    val labelItems = labels.map { label ->
        DrawerItem(
            title = label.name,
            icon = painterResource(id = HellNotesIcons.Label),
            onClick = {
                scope.launch { drawerState.close() }

            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheetContent(
                selectedItem = mainItems[selectedDrawerItem],
                mainItems = mainItems,
                staticItems = staticItems,
                labelItems = labelItems,
            )
        },
        content = {
            Crossfade(targetState = selectedDrawerItem) {
                when(it) {
                    0 -> {
                        NoteListScreen(
                            onFabAddClick = { navigations.navigateToNoteDetail(-1) },
                            noteListTopAppBarSelection = NoteListTopAppBarSelection(
                                listStyle = listStyle,
                                onCancelSelection = {
                                    noteListViewModel.cancelNoteSelection()
                                },
                                onNavigation = {
                                    scope.launch { drawerState.open() }
                                },
                                onDeleteSelected = {
                                    noteListViewModel.deleteAllSelected()
                                },
                                onSearch = {
                                    navigations.navigateToSearch()
                                },
                                onChangeListStyle = {
                                    homeViewModel.updateListStyle()
                                }
                            ),
                            uiState = noteListUiState,
                            noteSelection = NoteSelection(
                                dateHelper = dateHelper,
                                onClick = { note ->
                                    if (noteListUiState.selectedNotes.isEmpty()) {
                                        navigations.navigateToNoteDetail(note.id ?: -1)
                                    } else {
                                        if (noteListUiState.selectedNotes.contains(note)) {
                                            noteListViewModel.unselectNote(note)
                                        } else {
                                            noteListViewModel.selectNote(note)
                                        }
                                    }
                                },
                                onLongClick = { note ->
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    if (noteListUiState.selectedNotes.contains(note)) {
                                        noteListViewModel.unselectNote(note)
                                    } else {
                                        noteListViewModel.selectNote(note)
                                    }
                                }
                            ),
                            listConfigurationSelection = ListConfigurationSelection(
                                sorting = noteListUiState.sorting,
                                onSortingSelected = { sorting ->
                                    noteListViewModel.updateSorting(sorting)
                                }
                            ),
                        )
                    }
                    1 -> {
                        RemindersScreen(
                            uiState = remindersUiState,
                            noteSelection = NoteSelection(
                                dateHelper = dateHelper,
                                onClick = { note ->
                                    if (remindersUiState.selectedNotes.isEmpty()) {
                                        navigations.navigateToNoteDetail(note.id ?: -1)
                                    } else {
                                        if (remindersUiState.selectedNotes.contains(note)) {
                                            remindersViewModel.unselectNote(note)
                                        } else {
                                            remindersViewModel.selectNote(note)
                                        }
                                    }
                                },
                                onLongClick = { note ->
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    if (remindersUiState.selectedNotes.contains(note)) {
                                        remindersViewModel.unselectNote(note)
                                    } else {
                                        remindersViewModel.selectNote(note)
                                    }
                                }
                            ),
                            reminderTopAppBarSelection = ReminderTopAppBarSelection(
                                listStyle = listStyle,
                                selectedNotes = remindersUiState.selectedNotes,
                                onNavigation = {
                                    scope.launch { drawerState.open() }
                                },
                                onChangeListStyle = {
                                    homeViewModel.updateListStyle()
                                },
                                onDeleteSelected = {
                                    remindersViewModel.deleteAllSelected()
                                },
                                onCancelSelection = {
                                    remindersViewModel.cancelNoteSelection()
                                }
                            ),
                        )
                    }
                    2 -> {

                    }
                    3 -> {
                        TrashScreen(
                            uiState = trashUiState,
                            trashTopAppBarSelection = TrashTopAppBarSelection(
                                onNavigation = {
                                    scope.launch { drawerState.open() }
                                }
                            ),
                            noteSelection = NoteSelection(
                                dateHelper = dateHelper,
                                onClick = { note -> },
                                onLongClick = { note ->
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                }
                            ),
                            trashDropdownMenuSelection = TrashDropdownMenuSelection(
                                onEmptyTrash = {
                                    trashViewModel.emptyTrash()
                                }
                            ),
                            listStyle = listStyle
                        )
                    }
                }
            }
        }
    )
}