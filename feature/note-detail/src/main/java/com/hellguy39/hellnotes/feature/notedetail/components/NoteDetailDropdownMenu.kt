package com.hellguy39.hellnotes.feature.notedetail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownItemSelection
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenu
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@Composable
fun NoteDetailDropdownMenu(
    state: CustomDropdownMenuState,
    selection: NoteDetailDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() },
        items =
            listOf(
                CustomDropdownItemSelection(
                    leadingIconId = painterResource(id = AppIcons.Share),
                    text = stringResource(id = AppStrings.MenuItem.Share),
                    onClick = {
                        state.dismiss()
                        selection.onShare()
                    },
                ),
                CustomDropdownItemSelection(
                    leadingIconId = painterResource(id = AppIcons.Delete),
                    text = stringResource(id = AppStrings.MenuItem.Delete),
                    onClick = {
                        state.dismiss()
                        selection.onDelete()
                    },
                ),
            ),
    )
}

data class NoteDetailDropdownMenuSelection(
    val onShare: () -> Unit,
    val onDelete: () -> Unit,
)
