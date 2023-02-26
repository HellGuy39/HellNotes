package com.hellguy39.hellnotes.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.navigations.*
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
import com.hellguy39.hellnotes.feature.home.util.HomeScreen
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    navController: NavController,
    startScreen: HomeScreen = HomeScreen.NoteList,
    homeViewModel: HomeViewModel = hiltViewModel(),
    labelViewModel: LabelViewModel = hiltViewModel(),
) {
    val listStyle by homeViewModel.listStyle.collectAsStateWithLifecycle()
    val noteStyle by homeViewModel.noteStyle.collectAsStateWithLifecycle()
    val labels by homeViewModel.labels.collectAsStateWithLifecycle()
    val appSettings by homeViewModel.appSettings.collectAsStateWithLifecycle()
    val selectedDrawerItem by homeViewModel.drawerItem.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val onDrawerItemClick: (DrawerItem) -> Unit = { drawerItem ->
        scope.launch { drawerState.close() }
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
            homeViewModel.setDrawerItem(drawerItems[startScreen.index])
        }
    }

    val onChangeListStyle = {
        homeViewModel.updateListStyle()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheetContent(
                selectedItem = selectedDrawerItem,
                drawerItems = drawerItems,
                labelItems = labelItems,
                labelSelection = LabelSelection(
                    onEditLabel = { navController.navigateToLabels() },
                    onCreateNewLabel = { navController.navigateToLabels() }
                )
            )
        },
        content = {
            Crossfade(targetState = selectedDrawerItem) {
                when(it.itemType) {
                    DrawerItemType.Primary -> {
                        if (it.title == stringResource(id = HellNotesStrings.Title.Notes)) {
                            NoteListScreen(
                                navController = navController,
                                drawerState = drawerState,
                                listStyle = listStyle,
                                onChangeListStyle = onChangeListStyle,
                                noteStyle = noteStyle
                            )
                        } else {
                            RemindersScreen(
                                navController = navController,
                                listStyle = listStyle,
                                drawerState = drawerState,
                                onChangeListStyle = onChangeListStyle,
                                noteStyle = noteStyle
                            )
                        }
                    }
                    DrawerItemType.Secondary -> {
                        if (it.title == stringResource(id = HellNotesStrings.Title.Archive)) {
                            ArchiveScreen(
                                navController = navController,
                                drawerState = drawerState,
                                listStyle = listStyle,
                                noteStyle = noteStyle
                            )
                        } else {
                            TrashScreen(
                                drawerState = drawerState,
                                listStyle = listStyle,
                                noteStyle = noteStyle,
                                isTipVisible = !appSettings.isTrashTipChecked,
                                onCloseTip = { homeViewModel.setTrashTipChecked(true) }
                            )
                        }
                    }
                    DrawerItemType.Label -> {
                        LabelScreen(
                            navController = navController,
                            drawerState = drawerState,
                            listStyle = listStyle,
                            labelViewModel = labelViewModel,
                            noteStyle = noteStyle
                        )
                    }
                    else -> Unit
                }
            }
        }
    )
}