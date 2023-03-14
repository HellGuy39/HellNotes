package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import java.io.Serializable

@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<CustomDropdownItemSelection>
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        repeat(items.size) { index ->
            CustomDropdownItem(items[index])
        }
    }
}

data class CustomDropdownItemSelection(
    val leadingIconId: Painter? = null,
    val text: String = "",
    val onClick: () -> Unit = {}
)

@Composable
fun CustomDropdownItem(
    selection: CustomDropdownItemSelection
) {
    DropdownMenuItem(
        text = {
            Text(
                text = selection.text,
                style = MaterialTheme.typography.labelLarge
            )
        },
        onClick = selection.onClick,
        leadingIcon = {
            if (selection.leadingIconId != null) {
                Icon(
                    painter = selection.leadingIconId,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun rememberDropdownMenuState(): CustomDropdownMenuState {
    return rememberSaveable(
        saver = CustomDropdownMenuState.saver(),
        init = {
            CustomDropdownMenuState(
                visible = false
            )
        }
    )
}

class CustomDropdownMenuState(
    visible: Boolean,
) {
    var visible by mutableStateOf(visible)

    fun show() {
        visible = true
    }

    fun dismiss() {
        visible = false
    }

    data class CustomDropdownMenuStateData(
        val visible: Boolean
    ): Serializable

    companion object {

        fun saver(): Saver<CustomDropdownMenuState, *> = Saver(
            save = { state ->
                CustomDropdownMenuStateData(
                    visible = state.visible
                )
            },
            restore = { data ->
                CustomDropdownMenuState(
                    visible = data.visible
                )
            }
        )
    }
}