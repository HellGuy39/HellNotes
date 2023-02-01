package com.hellguy39.hellnotes.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.components.NoteSelection
import com.hellguy39.hellnotes.core.ui.navigations.INavigations
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.archive.ArchiveScreen
import com.hellguy39.hellnotes.feature.home.archive.ArchiveViewModel
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.label.LabelScreen
import com.hellguy39.hellnotes.feature.home.label.LabelViewModel
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.note_list.NoteListScreen
import com.hellguy39.hellnotes.feature.home.note_list.NoteListViewModel
import com.hellguy39.hellnotes.feature.home.note_list.components.DrawerSheetContent
import com.hellguy39.hellnotes.feature.home.note_list.components.LabelSelection
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
import com.hellguy39.hellnotes.feature.home.util.DrawerItemType
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    navigations: INavigations,
    homeViewModel: HomeViewModel = hiltViewModel(),
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
    noteListViewModel: NoteListViewModel = hiltViewModel(),
    trashViewModel: TrashViewModel = hiltViewModel(),
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    labelViewModel: LabelViewModel = hiltViewModel(),
    dateHelper: DateHelper = homeViewModel.dateHelper
) {
    val noteListUiState by noteListViewModel.uiState.collectAsStateWithLifecycle()
    val remindersUiState by remindersViewModel.uiState.collectAsStateWithLifecycle()
    val archiveUiState by archiveViewModel.uiState.collectAsStateWithLifecycle()
    val trashUiState by trashViewModel.uiState.collectAsStateWithLifecycle()
    val labelUiState by labelViewModel.uiState.collectAsStateWithLifecycle()

    val listStyle by homeViewModel.listStyle.collectAsStateWithLifecycle()
    val labels by homeViewModel.labels.collectAsStateWithLifecycle()
    val selectedDrawerItem by homeViewModel.drawerItem.collectAsStateWithLifecycle()

    val haptic = LocalHapticFeedback.current

    val snackbarHostState = remember { SnackbarHostState() }
    
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Notes),
            icon = painterResource(id = HellNotesIcons.StickyNote),
            itemType = DrawerItemType.Primary,
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                homeViewModel.setDrawerItem(drawerItem)
            }
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Reminders),
            icon = painterResource(id = HellNotesIcons.Notifications),
            itemType = DrawerItemType.Primary,
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                homeViewModel.setDrawerItem(drawerItem)
            }
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Archive),
            icon = painterResource(id = HellNotesIcons.Archive),
            itemType = DrawerItemType.Secondary,
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                homeViewModel.setDrawerItem(drawerItem)
            }
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Trash),
            icon = painterResource(id = HellNotesIcons.Delete),
            itemType = DrawerItemType.Secondary,
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                homeViewModel.setDrawerItem(drawerItem)
            }
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Settings),
            icon = painterResource(id = HellNotesIcons.Settings),
            itemType = DrawerItemType.Static,
            onClick = {
                scope.launch { drawerState.close() }
                navigations.navigateToSettings()
            }
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.AboutApp),
            icon = painterResource(id = HellNotesIcons.Info),
            itemType = DrawerItemType.Static,
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
            itemType = DrawerItemType.Label,
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                labelViewModel.selectLabel(label)
                homeViewModel.setDrawerItem(drawerItem)
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        if (selectedDrawerItem.itemType == DrawerItemType.None) {
            homeViewModel.setDrawerItem(drawerItems[0])
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheetContent(
                selectedItem = selectedDrawerItem,
                drawerItems = drawerItems,
                labelItems = labelItems,
                labelSelection = LabelSelection(
                    onEditLabel = { navigations.navigateToLabels() },
                    onCreateNewLabel = { navigations.navigateToLabels() }
                )
            )
        },
        content = {
            Crossfade(targetState = selectedDrawerItem) {
                when(it.itemType) {
                    DrawerItemType.Primary -> {
                        if (it.title == stringResource(id = HellNotesStrings.Title.Notes)) {
                            val actionLabel = stringResource(id = HellNotesStrings.Button.Undo)
                            val noteMovedToTrash = stringResource(id = HellNotesStrings.Text.NoteMovedToTrash)
                            val notesMovedToTrash = stringResource(id = HellNotesStrings.Text.NotesMovedToTrash)

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
                                        val isSingleNote = noteListUiState.selectedNotes.size == 1

                                        noteListViewModel.deleteAllSelected()
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                if(isSingleNote)
                                                    noteMovedToTrash
                                                else
                                                    notesMovedToTrash,
                                                actionLabel = actionLabel,
                                                duration = SnackbarDuration.Long,
                                            ).let { result ->
                                                when(result) {
                                                    SnackbarResult.ActionPerformed -> {
                                                        noteListViewModel.undoDelete()
                                                    }
                                                    else -> Unit
                                                }
                                            }
                                        }
                                    },
                                    onSearch = {
                                        navigations.navigateToSearch()
                                    },
                                    onChangeListStyle = {
                                        homeViewModel.updateListStyle()
                                    },
                                    onArchive = {
                                        noteListViewModel.archiveAllSelected()
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
                                snackbarHostState = snackbarHostState
                            )
                        } else {
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

                    }
                    DrawerItemType.Secondary -> {
                        if (it.title == stringResource(id = HellNotesStrings.Title.Archive)) {
                            ArchiveScreen(
                                uiState = archiveUiState,
                                listStyle = listStyle,
                                noteSelection = NoteSelection(
                                    dateHelper = dateHelper,
                                    onClick = { note ->
                                        if (archiveUiState.selectedNotes.isEmpty()) {
                                            navigations.navigateToNoteDetail(note.id ?: -1)
                                        } else {
                                            if (archiveUiState.selectedNotes.contains(note)) {
                                                archiveViewModel.unselectNote(note)
                                            } else {
                                                archiveViewModel.selectNote(note)
                                            }
                                        }
                                    },
                                    onLongClick = { note ->
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        if (archiveUiState.selectedNotes.contains(note)) {
                                            archiveViewModel.unselectNote(note)
                                        } else {
                                            archiveViewModel.selectNote(note)
                                        }
                                    }
                                ),
                                archiveTopAppBarSelection = ArchiveTopAppBarSelection(
                                    selectedNotes = archiveUiState.selectedNotes,
                                    onCancelSelection = {
                                        archiveViewModel.cancelNoteSelection()
                                    },
                                    onDeleteSelected = {
                                        archiveViewModel.deleteAllSelected()
                                    },
                                    onNavigation = {
                                        scope.launch { drawerState.open() }
                                    },
                                    onArchiveSelected = {
                                        archiveViewModel.archiveAllSelected()
                                    }
                                )
                            )
                        } else {
                            TrashScreen(
                                uiState = trashUiState,
                                trashTopAppBarSelection = TrashTopAppBarSelection(
                                    onNavigation = {
                                        scope.launch { drawerState.open() }
                                    },
                                    selectedNotes = trashUiState.selectedNotes,
                                    onCancelSelection = {
                                        trashViewModel.cancelNoteSelection()
                                    },
                                    onRestoreSelected = {
                                        trashViewModel.restoreSelectedNotes()
                                    },
                                    onDeleteSelected = {
                                        trashViewModel.deleteSelectedNotes()
                                    }
                                ),
                                noteSelection = NoteSelection(
                                    dateHelper = dateHelper,
                                    onClick = { note ->
                                        if (trashUiState.selectedNotes.isEmpty()) {

                                        } else {
                                            if (trashUiState.selectedNotes.contains(note)) {
                                                trashViewModel.unselectNote(note)
                                            } else {
                                                trashViewModel.selectNote(note)
                                            }
                                        }
                                    },
                                    onLongClick = { note ->
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                        if (trashUiState.selectedNotes.contains(note)) {
                                            trashViewModel.unselectNote(note)
                                        } else {
                                            trashViewModel.selectNote(note)
                                        }
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
                    DrawerItemType.Label -> {
                        LabelScreen(
                            uiState = labelUiState,
                            listStyle = listStyle,
                            noteSelection = NoteSelection(
                                dateHelper = dateHelper,
                                onClick = { note -> },
                                onLongClick = { note -> }
                            ),
                            labelTopAppBarSelection = LabelTopAppBarSelection(
                                selectedNotes = listOf(),
                                onDeleteSelected = {},
                                onCancelSelection = {},
                                onArchiveSelected = {},
                                onNavigation = {}
                            )
                        )
                    }
                    else -> Unit
                }
            }
        }
    )
}