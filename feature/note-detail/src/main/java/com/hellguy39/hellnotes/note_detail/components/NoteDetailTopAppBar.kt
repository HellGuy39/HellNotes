package com.hellguy39.hellnotes.note_detail.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.ColorParam
import com.hellguy39.hellnotes.note_detail.events.MenuEvents
import com.hellguy39.hellnotes.note_detail.events.TopAppBarEvents
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailTopAppBar(
    note: Note,
    scrollBehavior: TopAppBarScrollBehavior,
    topAppBarEvents: TopAppBarEvents,
    onNavigationButtonClick: () -> Unit,
    isShowMenu: Boolean,
    menuEvents: MenuEvents
) {
    TopAppBar(
        colors = if (note.colorHex == ColorParam.DefaultColor)
            TopAppBarDefaults.topAppBarColors()
        else
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color(note.colorHex),
                scrolledContainerColor = Color(note.colorHex)
            ),
        scrollBehavior = scrollBehavior,
        title = { /*Text( "Edit note", maxLines = 1, overflow = TextOverflow.Ellipsis)*/ },
        navigationIcon = {
            IconButton(
                onClick = { onNavigationButtonClick() }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.ArrowBack),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Back)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { topAppBarEvents.onReminder() }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Notifications),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Reminder)
                )
            }
            IconButton(
                onClick = { topAppBarEvents.onPin(!note.isPinned) }
            ) {
                Icon(
                    painter = if (note.isPinned)
                        painterResource(id = HellNotesIcons.PinActivated)
                    else
                        painterResource(id = HellNotesIcons.PinDisabled),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Pin)
                )
            }
            IconButton(
                onClick = {
                    topAppBarEvents.onMoreMenu()
                }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.MoreVert),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.More)
                )
            }

            NoteDetailDropdownMenu(
                expanded = isShowMenu,
                noteDetailsMenuEvents = menuEvents
            )
        }
    )
}