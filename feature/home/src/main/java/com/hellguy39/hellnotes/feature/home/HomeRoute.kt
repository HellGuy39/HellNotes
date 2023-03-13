package com.hellguy39.hellnotes.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteSwipesState
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.snack.*
import com.hellguy39.hellnotes.core.ui.navigations.navigateToAboutApp
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLabelEdit
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSettings
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.archive.ArchiveScreen
import com.hellguy39.hellnotes.feature.home.label.LabelScreen
import com.hellguy39.hellnotes.feature.home.label.LabelViewModel
import com.hellguy39.hellnotes.feature.home.note_list.NoteListScreen
import com.hellguy39.hellnotes.feature.home.note_list.components.DrawerSheetContent
import com.hellguy39.hellnotes.feature.home.note_list.components.LabelSelection
import com.hellguy39.hellnotes.feature.home.reminders.RemindersScreen
import com.hellguy39.hellnotes.feature.home.trash.TrashScreen
import com.hellguy39.hellnotes.feature.home.util.DrawerItem
import com.hellguy39.hellnotes.feature.home.util.DrawerItemType
import com.hellguy39.hellnotes.feature.home.util.HomeScreen
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    navController: NavController,
    startScreen: HomeScreen = HomeScreen.NoteList,
    homeViewModel: HomeViewModel = hiltViewModel(),
    labelViewModel: LabelViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val drawerUiState by homeViewModel.drawerUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    val visualsSelection = HomeScreenVisualsSelection(
        listStyle = uiState.listStyle,
        noteStyle = uiState.noteStyle,
        onUpdateListStyle = { homeViewModel.updateListStyle() },
        noteSwipesState = uiState.noteSwipesState,
        drawerState = drawerState,
        snackbarHost = {
            CustomSnackbarHost(
                state = snackbarHostState
            )
        }
    )

    fun showOnActionSnack(message: String, onActionPerformed: () -> Unit) {
        snackbarHostState.showDismissableSnackbar(
            scope = scope,
            message = message,
            actionLabel = context.getString(HellNotesStrings.Button.Undo),
            duration = SnackbarDuration.Long,
            onActionPerformed = onActionPerformed
        )
    }

    val multiActionSelection = HomeScreenMultiActionSelection(
        selectedNotes = uiState.selectedNotes,
        onCancelSelection = { homeViewModel.cancelNoteSelection() },
        onArchiveSelectedNotes = { isArchived ->
            val snackAction = if (isArchived) SnackAction.Archive else SnackAction.Unarchive
            showOnActionSnack(
                message = snackAction.getSnackMessage(context, uiState.selectedNotes.size == 1),
                onActionPerformed = { homeViewModel.undoArchiveSelected(isArchived = isArchived) }
            )
            homeViewModel.archiveSelectedNotes(isArchived)
        },
        onDeleteSelectedNotes = {
            showOnActionSnack(
                message = SnackAction.Delete.getSnackMessage(context, uiState.selectedNotes.size == 1),
                onActionPerformed = { homeViewModel.undoDeleteSelected() }
            )
            homeViewModel.deleteSelectedNotes()
        },
        onSelectNote = { note -> homeViewModel.selectNote(note) },
        onUnselectNote = { note -> homeViewModel.unselectNote(note) },
        onArchiveNote = { note, isArchived ->
            val snackAction = if (isArchived) SnackAction.Archive else SnackAction.Unarchive
            showOnActionSnack(
                message = snackAction.getSnackMessage(context, true),
                onActionPerformed = { homeViewModel.undoArchiveSelected(isArchived = isArchived) }
            )
            homeViewModel.archiveNote(clearBuffer = true, note = note, isArchived = isArchived)
        },
        onDeleteNote = { note ->
            showOnActionSnack(
                message = SnackAction.Delete.getSnackMessage(context, true),
                onActionPerformed = { homeViewModel.undoDeleteSelected() }
            )
            homeViewModel.deleteNote(clearBuffer = true, note = note)
        },
        onDeleteSelectedNotesFromTrash = { homeViewModel.deleteSelectedNotesFromTrash() },
        onRestoreSelectedNotesFromTrash = { homeViewModel.restoreSelectedNotesFromTrash() },
        onRestoreNote = { note ->
            homeViewModel.restoreNoteFromTrash(note)
        }
    )

    val onDrawerItemClick: (DrawerItem) -> Unit = { drawerItem ->
        scope.launch { drawerState.close() }
        multiActionSelection.onCancelSelection()
        snackbarHostState.currentSnackbarData?.dismiss()
        when(drawerItem.itemType) {
            DrawerItemType.Primary -> {
                homeViewModel.setDrawerItem(drawerItem)
            }
            DrawerItemType.Secondary -> {
                homeViewModel.setDrawerItem(drawerItem)
            }
            DrawerItemType.Static -> {
                if (drawerItem.title == context.getString(HellNotesStrings.Title.Settings)) {
                    navController.navigateToSettings()
                }
                if (drawerItem.title == context.getString(HellNotesStrings.Title.AboutApp)) {
                    navController.navigateToAboutApp()
                }
            }
            else -> Unit
        }
    }

    val drawerItems = listOf(
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Notes),
            icon = painterResource(id = HellNotesIcons.StickyNote),
            itemType = DrawerItemType.Primary,
            onClick = onDrawerItemClick
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Reminders),
            icon = painterResource(id = HellNotesIcons.Notifications),
            itemType = DrawerItemType.Primary,
            onClick = onDrawerItemClick
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Archive),
            icon = painterResource(id = HellNotesIcons.Archive),
            itemType = DrawerItemType.Secondary,
            onClick = onDrawerItemClick
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Trash),
            icon = painterResource(id = HellNotesIcons.Delete),
            itemType = DrawerItemType.Secondary,
            onClick = onDrawerItemClick
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.Settings),
            icon = painterResource(id = HellNotesIcons.Settings),
            itemType = DrawerItemType.Static,
            onClick = onDrawerItemClick
        ),
        DrawerItem(
            title = stringResource(id = HellNotesStrings.Title.AboutApp),
            icon = painterResource(id = HellNotesIcons.Info),
            itemType = DrawerItemType.Static,
            onClick = onDrawerItemClick
        )
    )

    val labelItems = drawerUiState.drawerLabels.map { label ->
        DrawerItem(
            title = label.name,
            icon = painterResource(id = HellNotesIcons.Label),
            itemType = DrawerItemType.Label,
            onClick = { drawerItem ->
                scope.launch { drawerState.close() }
                snackbarHostState.currentSnackbarData?.dismiss()
                multiActionSelection.onCancelSelection()
                labelViewModel.selectLabel(label)
                homeViewModel.setDrawerItem(drawerItem)
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        if (drawerUiState.drawerItem.itemType == DrawerItemType.None) {
            homeViewModel.setDrawerItem(drawerItems[startScreen.index])
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheetContent(
                selectedItem = drawerUiState.drawerItem,
                drawerItems = drawerItems,
                labelItems = labelItems,
                labelSelection = LabelSelection(
                    onEditLabel = { navController.navigateToLabelEdit(action = context.getString(HellNotesStrings.Action.Edit)) },
                    onCreateNewLabel = { navController.navigateToLabelEdit(action = context.getString(HellNotesStrings.Action.Create)) }
                )
            )
        },
        content = {
            Crossfade(targetState = drawerUiState.drawerItem) { drawerItem ->
                when(drawerItem.itemType) {
                    DrawerItemType.Primary -> {
                        if (drawerItem.title == stringResource(id = HellNotesStrings.Title.Notes)) {
                            NoteListScreen(
                                navController = navController,
                                visualsSelection = visualsSelection,
                                multiActionSelection = multiActionSelection
                            )
                        } else {
                            RemindersScreen(
                                navController = navController,
                                visualsSelection = visualsSelection,
                                multiActionSelection = multiActionSelection
                            )
                        }
                    }
                    DrawerItemType.Secondary -> {
                        if (drawerItem.title == stringResource(id = HellNotesStrings.Title.Archive)) {
                            ArchiveScreen(
                                navController = navController,
                                visualsSelection = visualsSelection,
                                multiActionSelection = multiActionSelection
                            )
                        } else {
                            TrashScreen(
                                visualsSelection = visualsSelection,
                                multiActionSelection = multiActionSelection
                            )
                        }
                    }
                    DrawerItemType.Label -> {
                        LabelScreen(
                            navController = navController,
                            visualsSelection = visualsSelection,
                            labelViewModel = labelViewModel,
                            multiActionSelection = multiActionSelection
                        )
                    }
                    else -> Unit
                }
            }
        }
    )
}

data class HomeScreenVisualsSelection(
    val noteStyle: NoteStyle,
    val listStyle: ListStyle,
    val drawerState: DrawerState,
    val noteSwipesState: NoteSwipesState,
    val onUpdateListStyle: () -> Unit,
    val snackbarHost: @Composable () -> Unit
)

data class HomeScreenMultiActionSelection(
    val selectedNotes: List<Note>,
    val onSelectNote: (Note) -> Unit,
    val onUnselectNote: (Note) -> Unit,
    val onDeleteNote: (Note) -> Unit,
    val onArchiveNote: (Note, isArchived: Boolean) -> Unit,
    val onRestoreNote: (Note) -> Unit,
    val onCancelSelection: () -> Unit,
    val onDeleteSelectedNotes: () -> Unit,
    val onDeleteSelectedNotesFromTrash: () -> Unit,
    val onRestoreSelectedNotesFromTrash: () -> Unit,
    val onArchiveSelectedNotes: (isArchive: Boolean) -> Unit,
)