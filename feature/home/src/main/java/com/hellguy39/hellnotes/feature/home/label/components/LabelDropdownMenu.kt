package com.hellguy39.hellnotes.feature.home.label.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownItemSelection
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenu
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@Composable
fun LabelDropdownMenu(
    state: CustomDropdownMenuState,
    selection: LabelDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() },
        items =
            listOf(
                CustomDropdownItemSelection(
                    leadingIconId = painterResource(id = AppIcons.Edit),
                    text = stringResource(id = AppStrings.MenuItem.Rename),
                    onClick = {
                        state.dismiss()
                        selection.onRename()
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

data class LabelDropdownMenuSelection(
    val onRename: () -> Unit,
    val onDelete: () -> Unit,
)
