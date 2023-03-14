package com.hellguy39.hellnotes.feature.note_detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownItemSelection
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenu
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun NoteDetailDropdownMenu(
    state: CustomDropdownMenuState,
    selection: NoteDetailDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() },
        items = listOf(
//            CustomDropdownItemSelection(
//                leadingIconId = painterResource(id = HellNotesIcons.Label),
//                text = stringResource(id = HellNotesStrings.MenuItem.Labels),
//                onClick = {
//                    state.dismiss()
//                    selection.onLabels()
//                }
//            ),
            CustomDropdownItemSelection(
                leadingIconId = painterResource(id = HellNotesIcons.Share),
                text = stringResource(id = HellNotesStrings.MenuItem.Share),
                onClick = {
                    state.dismiss()
                    selection.onShare()
                }
            ),
            CustomDropdownItemSelection(
                leadingIconId = painterResource(id = HellNotesIcons.Delete),
                text = stringResource(id = HellNotesStrings.MenuItem.Delete),
                onClick = {
                    state.dismiss()
                    selection.onDelete()
                }
            )
        )
    )
}

data class NoteDetailDropdownMenuSelection(
    val onShare: () -> Unit,
    val onDelete: () -> Unit
)