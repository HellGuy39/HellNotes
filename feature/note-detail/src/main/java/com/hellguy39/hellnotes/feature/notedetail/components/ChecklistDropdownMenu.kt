package com.hellguy39.hellnotes.feature.notedetail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownItemSelection
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenu
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun ChecklistDropdownMenu(
    state: CustomDropdownMenuState,
    selection: ChecklistDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() },
        items =
            listOf(
                CustomDropdownItemSelection(
                    leadingIconId = painterResource(id = HellNotesIcons.DoneAll),
                    text = stringResource(id = HellNotesStrings.MenuItem.CheckAllItems),
                    onClick = {
                        state.dismiss()
                        selection.onDoneAllItems()
                    },
                ),
                CustomDropdownItemSelection(
                    leadingIconId = painterResource(id = HellNotesIcons.RemoveDone),
                    text = stringResource(id = HellNotesStrings.MenuItem.UncheckAllItems),
                    onClick = {
                        state.dismiss()
                        selection.onRemoveDoneItems()
                    },
                ),
                CustomDropdownItemSelection(
                    leadingIconId = painterResource(id = HellNotesIcons.Delete),
                    text = stringResource(id = HellNotesStrings.MenuItem.Delete),
                    onClick = {
                        state.dismiss()
                        selection.onDeleteChecklist()
                    },
                ),
            ),
    )
}

data class ChecklistDropdownMenuSelection(
    val onDoneAllItems: () -> Unit,
    val onRemoveDoneItems: () -> Unit,
    val onDeleteChecklist: () -> Unit,
)
