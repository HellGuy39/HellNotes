package com.hellguy39.hellnotes.note_detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.components.CustomDropdownItem
import com.hellguy39.hellnotes.components.CustomDropdownMenu
import com.hellguy39.hellnotes.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@Composable
fun NoteDetailDropdownMenu(
    state: CustomDropdownMenuState,
    selection: NoteDetailDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() }
    ) {
//        CustomDropdownItem(
//            leadingIconId = painterResource(id = HellNotesIcons.Palette),
//            text = stringResource(id = HellNotesStrings.MenuItem.Color),
//            onClick = {
//                state.dismiss()
//                selection.onColor()
//            }
//        )
        CustomDropdownItem(
            leadingIconId = painterResource(id = HellNotesIcons.Label),
            text = stringResource(id = HellNotesStrings.MenuItem.Labels),
            onClick = {
                state.dismiss()
                selection.onLabels()
            }
        )
        CustomDropdownItem(
            leadingIconId = painterResource(id = HellNotesIcons.Share),
            text = stringResource(id = HellNotesStrings.MenuItem.Share),
            onClick = {
                state.dismiss()
                selection.onShare()
            }
        )
        CustomDropdownItem(
            leadingIconId = painterResource(id = HellNotesIcons.Delete),
            text = stringResource(id = HellNotesStrings.MenuItem.Delete),
            onClick = {
                state.dismiss()
                selection.onDelete()
            }
        )
    }
}

data class NoteDetailDropdownMenuSelection(
    val onColor: () -> Unit,
    val onLabels: () -> Unit,
    val onShare: () -> Unit,
    val onDelete: () -> Unit
)