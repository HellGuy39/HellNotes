package com.hellguy39.hellnotes.feature.home.label.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.component.menu.CustomDropdownItemSelection
import com.hellguy39.hellnotes.core.ui.component.menu.CustomDropdownMenu
import com.hellguy39.hellnotes.core.ui.component.menu.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings

@Composable
fun LabelDropdownMenu(
    state: CustomDropdownMenuState,
    selection: LabelDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() },
        items = listOf(
            CustomDropdownItemSelection(
                leadingIconId = painterResource(id = HellNotesIcons.Edit),
                text = stringResource(id = HellNotesStrings.MenuItem.Rename),
                onClick = {
                    state.dismiss()
                    selection.onRename()
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

data class LabelDropdownMenuSelection(
    val onRename: () -> Unit,
    val onDelete: () -> Unit
)